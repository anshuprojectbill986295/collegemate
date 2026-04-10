package com.anshu.collegemate

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import com.anshu.collegemate.Data.Repository.AuthRepository
import com.anshu.collegemate.ui.View.Screens.LoginScreen
import com.anshu.collegemate.ui.ViewModel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.anshu.collegemate.Data.Model.Login.AuthResult
import com.anshu.collegemate.ui.ViewModel.UserViewModel
import com.google.firebase.messaging.FirebaseMessaging

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        val auth = FirebaseAuth.getInstance()
        val repository = AuthRepository(auth)
        val viewModel = AuthViewModel(repository)

        //val authRepository = AuthRepository()

        // 🛡️ THE SAFETY NET: Only subscribe if they actually have an active session.
        if (repository.isUserLoggedIn()) {
            FirebaseMessaging.getInstance().subscribeToTopic("all_announcements")
        } else {
            // Just in case a weird glitch happened, forcefully unsubscribe them if they aren't logged in
            FirebaseMessaging.getInstance().unsubscribeFromTopic("all_announcements")
        }

        setContent {

            val isLoggedIn by viewModel.isLoggedIn.collectAsState()
            if (isLoggedIn){
                UserViewModel.loadUserProfile()
                MainView {viewModel.logout()  }
            }
            else{
                LoginScreen(viewModel)
            }


        }
    }
}