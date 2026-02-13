package com.anshu.collegemate.ui.View.Others.DataCardView

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.anshu.collegemate.Data.Model.HomeScreen.RoutineSeed
import com.anshu.collegemate.Data.Model.HomeScreen.ScheduleCardData
import com.anshu.collegemate.R
import com.anshu.collegemate.Utils.DateTimeUtil
import com.anshu.collegemate.ui.theme.CardColorsScheme

@Composable
fun ScheduleCardView(scd: ScheduleCardData){
    val cardColorScheme= CardColorsScheme.getCCScheme(scd.colorKey)
    Card(shape = RoundedCornerShape(20.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.Transparent)
            .padding(10.dp), colors = CardDefaults.cardColors(containerColor = Color(cardColorScheme.cardBackgroundColor)), elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)) {
        Column(modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth(), horizontalAlignment = Alignment.Start) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 4.dp)){
                Text(text = scd.name, softWrap = true, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight(800), fontSize = 20.sp, color = Color(cardColorScheme.nameColor), modifier = Modifier.width(220.48.dp))
                Box(Modifier
                    .clip(RoundedCornerShape(26.dp))
                    .background(color = Color(cardColorScheme.timingBackgroundColor))
                    .size(width = 120.dp, height = 44.dp), contentAlignment = Alignment.Center){
                    Text(text = DateTimeUtil.convert(scd.startTime)+"-"+DateTimeUtil.convert(scd.endTime), fontFamily = FontFamily.SansSerif, fontWeight = FontWeight(700), fontSize = 12.sp, color = Color(cardColorScheme.timingContentColor), softWrap = false)
                }
            }
            Text(text = scd.instructor, fontFamily = FontFamily.SansSerif, fontSize = 14.sp, fontWeight = FontWeight(500), modifier = Modifier.padding(bottom = 8.dp), color = Color(cardColorScheme.instructorColor))
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.padding(top = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        painterResource(R.drawable.location_on_24px),
                        contentDescription = null,
                        modifier = Modifier.padding(end = 8.dp),
                        tint = Color(cardColorScheme.roomNoColor)
                    )
                    Text(
                        text = scd.roomNo,
                        fontFamily = FontFamily.SansSerif,
                        fontWeight = FontWeight(500),
                        fontSize = 14.sp,
                        color = Color(cardColorScheme.roomNoColor)
                    )
                }
                val context=LocalContext.current

                Button(onClick = {
                    val intent= Intent(Intent.ACTION_VIEW,
                        scd.syllabusLink.toUri())
                    context.startActivity(intent)


                }) {
                    Text(text = "Syllabus")
                }

            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun SCVPreview(){
    ScheduleCardView(RoutineSeed.mondayClasses.get(0))
}