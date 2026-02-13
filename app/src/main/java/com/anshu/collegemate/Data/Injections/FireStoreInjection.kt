package com.anshu.collegemate.Data.Injections

import com.google.firebase.firestore.FirebaseFirestore

object FireStoreInjection {

    private val firestore = FirebaseFirestore.getInstance()
    //private val storageRef = FirebaseStorage.getInstance().reference

    fun getFirestore() = firestore
}