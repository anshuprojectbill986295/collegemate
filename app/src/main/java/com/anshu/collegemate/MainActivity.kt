package com.anshu.collegemate

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import com.anshu.collegemate.Data.Repository.AuthRepository
import com.anshu.collegemate.ui.View.Screens.LoginScreen
import com.anshu.collegemate.ui.ViewModel.AuthViewModel
import com.google.firebase.auth.FirebaseAuth
import com.anshu.collegemate.Data.Model.Login.AuthResult
class MainActivity : ComponentActivity() {
    @SuppressLint("StateFlowValueCalledInComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        val auth = FirebaseAuth.getInstance()
        val repository = AuthRepository(auth)
        val viewModel = AuthViewModel(repository)

        //RoutineSeed.uploadRoutineOnce()

        setContent {

            val isLoggedIn by viewModel.isLoggedIn.collectAsState()
            if (isLoggedIn){
                Text("Login Successfully")
            }
            else{
                LoginScreen(viewModel)
            }


        }
    }
}