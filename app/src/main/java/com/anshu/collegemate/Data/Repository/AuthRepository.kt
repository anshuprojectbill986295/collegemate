package com.anshu.collegemate.Data.Repository

import com.anshu.collegemate.Data.Model.Login.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class AuthRepository(
    val auth: FirebaseAuth = FirebaseAuth.getInstance()
) {

    suspend fun signInWithGoogle(idToken:String): AuthResult {
        return try
        {       val credentials = GoogleAuthProvider.getCredential(idToken,null)
            val result = auth.signInWithCredential(credentials).await()
            val email =result.user?.email?:""
            if(!email.endsWith("@nitap.ac.in")){
                auth.currentUser?.delete()
                auth.signOut()
                return AuthResult.Error("Please sign in with your institute email.")
            }
            val userId = result.user?.uid ?:""
            AuthResult.Success(userId)

        }
        catch (e: Exception){
            AuthResult.Error("Login Failed")
        }
    }

    fun isUserLoggedIn(): Boolean{
        return auth.currentUser!=null
    }
    fun logout()
    {
        auth.signOut()
    }



}