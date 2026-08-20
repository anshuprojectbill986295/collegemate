package com.anshu.collegemate.ui.View.Others

import android.graphics.Color.rgb
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anshu.collegemate.Data.Model.HomeScreen.BirthdaySeed
import com.anshu.collegemate.Utils.DateTimeUtil

/**
 * A celebratory banner displayed on the HomeScreen when it's the student's birthday.
 */
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BirthdayBanner(email: String) {
    if (email.isEmpty()) return
    
    // Use the logic from Stage 1 to determine if today is the user's birthday
    val isBirthday = BirthdaySeed.isBirthdayToday(email)
    if (!isBirthday) return
    
    val student = BirthdaySeed.getBirthdayForUser(email) ?: return
    
    // Warm celebratory gradient (Pink to Orange)
    val gradient = Brush.linearGradient(
        colors = listOf(
            Color(rgb(236, 72, 153)), // Pink 500
            Color(rgb(249, 115, 22))  // Orange 500
        )
    )

    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 0.dp, bottom = 5.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(gradient)
                .padding(12.dp)
        ) {
            Text(
                text = "🎂 Happy Birthday, ${DateTimeUtil.toTitleCase(student.name)}!",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight(800),
                fontSize = 16.sp,
                color = Color.White
            )
            Text(
                text = "Wishing you a wonderful day! 🎉",
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(),
                fontWeight = FontWeight(600),
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.9f)
            )
        }
    }
}
