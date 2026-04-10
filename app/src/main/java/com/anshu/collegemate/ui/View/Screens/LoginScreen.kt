package com.anshu.collegemate.ui.View.Screens

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anshu.collegemate.Data.Model.Login.AuthResult
import com.anshu.collegemate.R
import com.anshu.collegemate.Utils.GoogleSignInHelper
import com.anshu.collegemate.ui.ViewModel.AuthViewModel

@Composable
fun LoginScreen(
    viewModel: AuthViewModel
) {
    val context = LocalContext.current
    val authState by viewModel.authState.collectAsState()
    val googleHelper = remember { GoogleSignInHelper(context) }

    // Local loading state for button press until One Tap appears
    var isSigningIn by remember { mutableStateOf(false) }

    // Reset local loading based on authState changes
    LaunchedEffect(authState) {
        when (authState) {
            is AuthResult.Loading -> {
                isSigningIn = false
            }
            is AuthResult.Success, is AuthResult.Error -> {
                isSigningIn = false
            }
            else -> { /* do nothing */ }
        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val credential =
                googleHelper.oneTapClient.getSignInCredentialFromIntent(result.data)
            val idToken = credential.googleIdToken

            if (idToken != null) {
                viewModel.signInWithGoogle(idToken)
            } else {
                isSigningIn = false
                Toast.makeText(context, "Google ID token is null", Toast.LENGTH_SHORT).show()
            }
        } else {
            isSigningIn = false
        }
    }

    Box(
        modifier = Modifier
            .background(
                Brush.linearGradient(
                    colors = listOf(Color(0xfff0f4f8), Color(0xffe0e7ff)),
                    start = Offset(0f, 0f),
                    end = Offset(100f, 100f)
                )
            )
            .systemBarsPadding() // Pushes content inside the safe area
            .padding(bottom = 20.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
            // No verticalArrangement here, weights handle the spacing!
        ) {
            // Row for logo
            Row(
                modifier = Modifier.size(302.4.dp, 100.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.logo_foreground),
                    contentDescription = "Logo",
                    contentScale = ContentScale.Crop
                )
            }
            Row(
                modifier = Modifier.size(302.4.dp, 40.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "College Mate",
                    fontSize = 36.sp,
                    fontFamily = FontFamily.SansSerif,
                    color = Color(0xFF4F46E5),
                    fontWeight = FontWeight.ExtraBold
                )
            }

            Spacer(Modifier.weight(0.5f))

            Row {
                Column(
                    Modifier.size(302.4.dp, 56.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Text("Your essential, hassle-free campus", color = Color(0xFF6b7280), fontSize = 18.sp)
                    }
                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                        Text("companion.", color = Color(0xFF6b7280), fontSize = 18.sp)
                    }
                }
            }

            Spacer(Modifier.weight(1.5f))

            // --- Feature 1 ---
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Image(
                    painter = painterResource(R.drawable.outline_circle_notifications_24),
                    contentDescription = "Notification Bell Icon",
                    modifier = Modifier.clip(RoundedCornerShape(25.dp)).background(color = Color(0x1af59e0b))
                )
                Column {
                    Text("Instant, Clear Schedule Alerts", fontWeight = FontWeight.Bold, color = Color(0xFF1f2937), fontSize = 16.sp)
                    Text("No more lost updates! Get **direct \nnotifications** for canceled classes or\nschedule changes—100% clear of\ngroup chat clutter.", fontSize = 14.sp, color = Color(0xFF4b5563))
                }
            }

            Spacer(Modifier.weight(1f))

            // --- Feature 2 ---
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Image(
                    painter = painterResource(R.drawable.outline_assignment_24),
                    contentDescription = null,
                    modifier = Modifier.clip(RoundedCornerShape(25.dp)).background(color = Color(0x1a4f46e5))
                )
                Column {
                    Text("Organized Academic Tracker", fontWeight = FontWeight.Bold, color = Color(0xFF1f2937), fontSize = 16.sp)
                    Text("Automatically track all upcoming \n**assignment submission dates, test\nschedules, and the required\nsyllabus** in one unified view.", fontSize = 14.sp, color = Color(0xFF4b5563))
                }
            }

            Spacer(Modifier.weight(1f))

            // --- Feature 3 ---
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                Image(
                    painter = painterResource(R.drawable.outline_schedule_24),
                    contentDescription = null,
                    modifier = Modifier.clip(RoundedCornerShape(25.dp)).background(color = Color(0x1af59e0b))
                )
                Column {
                    Text("Real-time Schedule Dashboard", fontWeight = FontWeight.Bold, color = Color(0xFF1f2937), fontSize = 16.sp)
                    Text("Forget searching PDFs! Get a **direct,\nreal-time view** of today's schedule\nimmediately upon launching the app.", fontSize = 14.sp, color = Color(0xFF4b5563))
                }
            }

            Spacer(Modifier.weight(1.5f))

            Row { Text("Tap to connect and simplify your college life.", fontSize = 14.sp, color = Color(0xFF4b5563)) }

            Spacer(Modifier.weight(0.2f))

            Row { Text("NOTE: Only your institute email is supported", color = Color.Red, fontWeight = FontWeight.SemiBold) }

            Spacer(Modifier.weight(0.5f))

            // Google Sign-In Button
            Button(
                onClick = {
                    if (!isSigningIn) {
                        isSigningIn = true
                        googleHelper.oneTapClient.beginSignIn(googleHelper.signInRequest)
                            .addOnSuccessListener {
                                launcher.launch(
                                    IntentSenderRequest.Builder(it.pendingIntent.intentSender).build()
                                )
                            }
                            .addOnFailureListener { e ->
                                Log.e("GH-BEGIN-ERROR", e.message ?: "unknown error")
                                isSigningIn = false
                                Toast.makeText(context, "Sign-in start failed: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                },
                enabled = !isSigningIn && authState !is AuthResult.Loading,
                modifier = Modifier.clip(RoundedCornerShape(24.dp)),
                colors = ButtonColors(
                    containerColor = Color(66, 135, 245),
                    contentColor = Color(66, 135, 245),
                    disabledContentColor = Color(66, 135, 245),
                    disabledContainerColor = Color(66, 135, 245)
                )
            ) {
                Box {
                    if (isSigningIn) {
                        CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White, strokeWidth = 3.dp)
                    } else {
                        Row(
                            Modifier.width(180.dp),
                            horizontalArrangement = Arrangement.SpaceAround,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Image(
                                painter = painterResource(R.drawable.google__g__logo),
                                contentDescription = null,
                                modifier = Modifier.clip(RoundedCornerShape(50.dp)).background(Color.White)
                            )
                            Text(text = "Continue with Google", color = Color.White)
                        }
                    }
                }
            }

            Spacer(Modifier.weight(0.2f))

            Row {
                Text(
                    text = buildAnnotatedString {
                        append("By signing in, you agree to the College Mate\n      ")
                        withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) { append("Terms of Service") }
                        append(" and")
                        withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) { append(" Privacy Policy") }
                        append(".")
                    }
                )
            }
        }

        // Full‑screen loader when auth is actually processing
        when (authState) {
            is AuthResult.Loading -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(48.dp),
                    color = Color.Green,
                    strokeWidth = 6.dp,
                    trackColor = Color.LightGray,
                    strokeCap = StrokeCap.Round
                )
            }
            is AuthResult.Error -> {
                val errorMessage = (authState as AuthResult.Error).message
                LaunchedEffect(errorMessage) {
                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
            else -> {}
        }
    }
}