package com.anshu.collegemate.Utils

import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.messaging.FirebaseMessaging


object FcmTokenUtil {

    fun saveToken() {
        val user = FirebaseAuth.getInstance().currentUser ?: return

        FirebaseMessaging.getInstance().token
            .addOnSuccessListener { token ->

                val tokenData = hashMapOf(
                    "userId" to user.uid,
                    "platform" to "android",
                    "updatedAt" to com.google.firebase.firestore.FieldValue.serverTimestamp()
                )

                Firebase.firestore
                    .collection("fcm_tokens")
                    .document(token)   // 👈 token as document ID
                    .set(tokenData)
            }
    }
}
