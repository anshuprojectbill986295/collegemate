package com.anshu.collegemate.Utils

import android.content.Context
import com.anshu.collegemate.R
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient

//TODO update WebClient ID
class GoogleSignInHelper(context: Context) {
    val oneTapClient: SignInClient = Identity.getSignInClient(context)

    val signInRequest: BeginSignInRequest = BeginSignInRequest.builder()
        .setGoogleIdTokenRequestOptions(
            BeginSignInRequest.GoogleIdTokenRequestOptions.builder().setSupported(true)
                .setServerClientId(context.getString(R.string.web_client_id)
                )
                .setFilterByAuthorizedAccounts(false)
                .build()
        ).build()
}