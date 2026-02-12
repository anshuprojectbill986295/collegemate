package com.anshu.collegemate.ui.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anshu.collegemate.Data.Model.Login.AuthResult
import com.anshu.collegemate.Data.Repository.AuthRepository
import com.anshu.collegemate.Utils.FcmTokenUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(val repository: AuthRepository= AuthRepository()): ViewModel() {
    private val _authState = MutableStateFlow<AuthResult?>(null)
    val authState:StateFlow<AuthResult?> = _authState

    private val _isLoggedIn  = MutableStateFlow<Boolean>(repository.isUserLoggedIn())
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn

    fun signInWithGoogle(idToken:String){
        viewModelScope.launch {
            _authState.value= AuthResult.Loading
            val result= repository.signInWithGoogle(idToken)
            _authState.value= result
            if (result is AuthResult.Success){
                _isLoggedIn.value=true
                FcmTokenUtil.saveToken()
            }
            else{
                _isLoggedIn.value=false
            }
        }
    }

    fun logout(){
        repository.logout()
        _isLoggedIn.value=false
    }


}