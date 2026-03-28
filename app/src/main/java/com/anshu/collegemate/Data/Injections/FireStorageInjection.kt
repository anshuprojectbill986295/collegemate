package com.anshu.collegemate.Data.Injections

import com.google.firebase.storage.FirebaseStorage

object FireStorageInjection{
    private val storage = FirebaseStorage.getInstance()
    private val storageRef = storage.reference
    fun getStorageRef() = storageRef
    fun getStorage() = storage
}
