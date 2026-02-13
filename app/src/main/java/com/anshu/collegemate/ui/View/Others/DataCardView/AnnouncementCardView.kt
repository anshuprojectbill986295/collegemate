package com.anshu.collegemate.ui.View.Others.DataCardView

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.anshu.collegemate.Data.Model.Announcement.AnnouncementCard
import com.anshu.collegemate.Utils.DateTimeUtil
import com.anshu.collegemate.ui.ViewModel.UserViewModel
import com.anshu.collegemate.ui.theme.CardColorsScheme



@Composable
fun AnnouncementCardView(ac: AnnouncementCard){
    val cardColorScheme= CardColorsScheme.getCCScheme(ac.colorKey)
    if (ac.type.contentEquals("general")){
        Card(shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent)
                .padding(10.dp), colors = CardDefaults.cardColors(containerColor =
                Color(cardColorScheme.cardBackgroundColor)), elevation =
                CardDefaults.cardElevation(defaultElevation = 8.dp)) {
            Column(modifier = Modifier.padding(10.dp).fillMaxWidth()){
                Row( modifier = Modifier.fillMaxWidth().padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(model = UserViewModel.userP.value?.photoURL, contentDescription = null, modifier = Modifier.size(38.dp).clip(CircleShape))
                    Column(Modifier.padding(start = 10.dp)) { Text("~~"+ac.announcerName, fontSize = 14.sp, fontWeight = FontWeight(600),color = Color(cardColorScheme.roomNoColor))
                        Text(DateTimeUtil.toTitleCase(DateTimeUtil.getTimeAgo(ac.createdAt)), fontSize = 12.sp, modifier = Modifier.padding(start =15.dp))}


                }
                HorizontalDivider(thickness = 4.dp,color=Color.Black)
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = ac.message, fontSize = 16.sp, fontWeight = FontWeight(800),color = Color(cardColorScheme.nameColor))

                }
            }
        }
    }
    else{
        Card(shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.Transparent)
                .padding(10.dp), colors = CardDefaults.cardColors(containerColor =
                Color(cardColorScheme.cardBackgroundColor)), elevation =
                CardDefaults.cardElevation(defaultElevation = 8.dp)) {
            Column(modifier = Modifier.padding(10.dp).fillMaxWidth()){
                Row( modifier = Modifier.fillMaxWidth().padding(10.dp), verticalAlignment = Alignment.CenterVertically) {
                    AsyncImage(model = UserViewModel.userP.value?.photoURL, contentDescription = null, modifier = Modifier.size(38.dp).clip(CircleShape))
                    Column(Modifier.padding(start = 10.dp)) { Text("~~"+ac.announcerName, fontSize = 14.sp, fontWeight = FontWeight(600),color = Color(cardColorScheme.roomNoColor))
                        Text(DateTimeUtil.toTitleCase(DateTimeUtil.getTimeAgo(ac.createdAt)), fontSize = 12.sp, modifier = Modifier.padding(start =15.dp))}


                }
                HorizontalDivider(thickness = 4.dp,color=Color.Black)
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = ac.message, fontSize = 16.sp, fontWeight = FontWeight(800),color = Color(cardColorScheme.nameColor))

                }
            }
        }
    }


}

//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true)
//@Composable
//
//fun AnnouncementCardViewPreview(){
//    com.example.collegemate.ui.View.Others.AnnouncementCardView(dummyGeneralAC)
//}