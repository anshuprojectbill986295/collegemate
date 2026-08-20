package com.anshu.collegemate

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.anshu.collegemate.Data.Repository.AuthRepository
import com.anshu.collegemate.Utils.BirthdayScheduler
import com.anshu.collegemate.ui.View.Screens.LoginScreen
import com.anshu.collegemate.ui.ViewModel.AuthViewModel
import com.anshu.collegemate.ui.ViewModel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessaging
import android.util.Log
import android.view.KeyEvent

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
            BirthdayScheduler.scheduleNextCheck(this)
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
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Log.d(
                "BACK_TRACE",
                "Activity.onKeyDown | action=${event.action}"
            )
        }

        return super.onKeyDown(keyCode, event)
    }
}