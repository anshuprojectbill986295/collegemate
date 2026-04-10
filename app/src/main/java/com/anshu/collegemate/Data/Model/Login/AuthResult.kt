package com.anshu.collegemate.Data.Model.Login

sealed class AuthResult{
    object Idle: AuthResult()
    object Loading:AuthResult()
    data class Success(val userId:String):AuthResult()
    data class Error(val message:String):AuthResult()
}