package com.anshu.collegemate.ui.ViewModel

import com.anshu.collegemate.Data.Model.Login.UserProfile
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object UserViewModel {
    private val _userP = MutableStateFlow<UserProfile?>(null)
    val userP: StateFlow<UserProfile?> =_userP
    fun loadUserProfile(){
        val currentUser = FirebaseAuth.getInstance().currentUser
        val userName=currentUser?.displayName?:""

        val userEmail=currentUser?.email?:""
        val userPhoto= currentUser?.photoUrl.toString()
        val userProfile=UserProfile(userName,userEmail,userPhoto)
        _userP.value=userProfile
    }
}