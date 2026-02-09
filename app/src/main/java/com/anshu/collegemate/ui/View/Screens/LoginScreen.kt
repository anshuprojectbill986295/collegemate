package com.anshu.collegemate.ui.View.Screens

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anshu.collegemate.R

@Composable
fun LoginScreen(
    //TODO viewModel: AuthViewModel
){


    val context= LocalContext.current
    //TODO val authState by viewModel.authState.collectAsState()
    //TODO val googleHelper = remember{GoogleSignInHelper(context)}

// //TODO    val launcher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.StartIntentSenderForResult()
//    ) { result ->
//        if (result.resultCode == Activity.RESULT_OK) {
//            val credential =
//                googleHelper.oneTapClient.getSignInCredentialFromIntent(result.data)
//            val idToken = credential.googleIdToken
//
//            if (idToken != null) {
//                viewModel.signInWithGoogle(idToken)
//            }
//        }
//    }



    Box(modifier = Modifier.background(
        Brush.linearGradient(colors = listOf(Color(0xfff0f4f8)
            ,Color(0xffe0e7ff)), start = Offset(0f,0f),end= Offset(100f,100f))
    ).padding(top=25.dp, bottom = 20.dp)
        .fillMaxSize()
    )

    { //Content inside the Rounder corner Box


        Column (modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceAround)
        { //Row wise segregation
            //Row for logo
            Row (modifier = Modifier.size(302.4.dp,100.dp), horizontalArrangement = Arrangement.Center){
                Image(painter = painterResource(R.drawable.logo_foreground), contentDescription = "Logo"
                    , contentScale = ContentScale.Crop) }
            Row(modifier = Modifier.size(302.4.dp,40.dp)
                ,horizontalArrangement = Arrangement.Center) {
                Text(text = "College Mate", fontSize = 36.sp, fontFamily = FontFamily.SansSerif,
                    color = Color(0xFF4F46E5), fontWeight = FontWeight.ExtraBold)
            }
            Spacer(Modifier.height(16.dp))
            Row { Column(Modifier.size(302.4.dp,56.dp), horizontalAlignment = Alignment.CenterHorizontally
                , verticalArrangement = Arrangement.Center) {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){Text(text="Your essential, hassle-free campus", color = Color(0xFF6b7280), fontSize = 18.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)}
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){Text(text = "companion.", color = Color(0xFF6b7280), fontSize = 18.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal)}
            } }
            Spacer(Modifier.height(32.dp))
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly)
            {Image(painter = painterResource(R.drawable.outline_circle_notifications_24), contentDescription = "Notification Bell Icon"
                , modifier = Modifier.clip(RoundedCornerShape(25.dp)).background(color = Color(0x1af59e0b)))
                Column {Text(text = "Instant, Clear Schedule Alerts", fontWeight = FontWeight.Bold, color = Color(0xFF1f2937), fontSize = 16.sp, fontFamily = FontFamily.SansSerif)
                    Text(text = "No more lost updates! Get **direct " +
                            "\nnotifications** for canceled classes or" +
                            "\nschedule changes—100% clear of" +
                            "\ngroup chat clutter. ", fontSize = 14.sp, fontFamily = FontFamily.SansSerif, color = Color(0xFF4b5563))
                }
            }
            Spacer(Modifier.height(20.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) { Image(painter=painterResource(R.drawable.outline_assignment_24), contentDescription = null,
                modifier = Modifier.clip(RoundedCornerShape(25.dp)).background(color = Color(0x1a4f46e5)))
                Column { Text(text = "Organized Academic Tracker", fontWeight = FontWeight.Bold, color = Color(0xFF1f2937), fontSize = 16.sp, fontFamily = FontFamily.SansSerif)
                    Text(text = "Automatically track all upcoming " +
                            "\n**assignment submission dates, test\n" +
                            "schedules, and the required\n" +
                            "syllabus** in one unified view.", fontSize = 14.sp, fontFamily = FontFamily.SansSerif, color = Color(0xFF4b5563)) }

            }
            Spacer(Modifier.height(20.dp))

            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) { Image(painter=painterResource(R.drawable.outline_schedule_24), contentDescription = null,
                modifier = Modifier.clip(RoundedCornerShape(25.dp)).background(color = Color(0x1af59e0b)))
                Column { Text(text = "Real-time Schedule Dashboard", fontWeight = FontWeight.Bold, color = Color(0xFF1f2937), fontSize = 16.sp, fontFamily = FontFamily.SansSerif)
                    Text(text = "Forget searching PDFs! Get a **direct,\n" +
                            "real-time view** of today's schedule\n" +
                            "immediately upon launching the app.", fontSize = 14.sp, fontFamily = FontFamily.SansSerif, color = Color(0xFF4b5563)) }

            }
            Spacer(Modifier.height(32.dp))


            Row { Text(text = "Tap to connect and simplify your college life.", fontSize = 14.sp,
                fontFamily = FontFamily.SansSerif, color = Color(0xFF4b5563)) }

            Spacer(Modifier.height(8.dp))
            Row { Text(text = "NOTE: Only your institute email is supported", color = Color.Red
                , fontWeight = FontWeight.SemiBold)}
            Spacer(Modifier.height(16.dp))
            Button(onClick = {
//           TODO     googleHelper.oneTapClient.beginSignIn(googleHelper.signInRequest)
//                    .addOnSuccessListener {
//                        launcher.launch(
//                            IntentSenderRequest.Builder(it.pendingIntent.intentSender).build()
//                        )
//                    }

            }, modifier = Modifier.clip(RoundedCornerShape(24.dp)),
                colors = ButtonColors(containerColor = Color(66, 135, 245),
                    contentColor = Color(66, 135, 245),
                    disabledContentColor = Color(66, 135, 245),
                    disabledContainerColor = Color(66, 135, 245)))
            {
                Box(){
                    //Google Icon +Text Sign in with Google it is pill shape
                    Row(Modifier.width(180.dp), horizontalArrangement = Arrangement.SpaceAround
                        , verticalAlignment = Alignment.CenterVertically){
                        Image(painter = painterResource(R.drawable.google__g__logo),
                            contentDescription = null, modifier = Modifier.clip(RoundedCornerShape(50.dp))
                                .background(Color.White))
                        Text(text = "Continue with Google", color =Color.White )
                    }
                }
            }
            Spacer(Modifier.height(8.dp))
            Row() { Text(text = buildAnnotatedString {
                append("By signing in, you agree to the College Mate\n      ")
                withStyle(SpanStyle(textDecoration = TextDecoration.Underline)){
                    append("Terms of Service")
                }
                append(" and")
                withStyle(SpanStyle(textDecoration = TextDecoration.Underline)){
                    append(" Privacy Policy")
                }
                append(".")
            })}



        }

    }
//   TODO  when (authState) {
//        is AuthResult.Loading -> Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){ CircularProgressIndicator(
//            modifier = Modifier.size(48.dp),
//            color = Color.Green,
//            strokeWidth = 6.dp,
//            trackColor = Color.LightGray,
//            strokeCap = StrokeCap.Round
//        )
//        }
//        is AuthResult.Success -> {}
//        is AuthResult.Error -> Text("Login Failed")
//        else -> {}
//    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview
@Composable
fun nnnnn(){
    LoginScreen()
}