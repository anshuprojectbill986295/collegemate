package com.anshu.collegemate.ui.View.Screens

import android.graphics.Color.rgb
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anshu.collegemate.Data.Model.HomeScreen.RoutineSeed
import com.anshu.collegemate.Utils.DateTimeUtil
import com.anshu.collegemate.Utils.DateTimeUtil.todayDay
import com.anshu.collegemate.ui.View.Others.DataCardView.ScheduleCardView
import com.anshu.collegemate.ui.ViewModel.UserViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(){
    var today by remember { mutableStateOf(todayDay().lowercase()) }
    val todayRoutine = RoutineSeed.weeklyRoutine[today]?:emptyList()
    val gradient = Brush.linearGradient(colors = listOf(Color(0xfff0f4f8)
        ,Color(0xffe0e7ff)), start = Offset(0f,0f),end= Offset(100f,100f))
    var showDatePicker by remember{(mutableStateOf(false))}
    var dateChoosed by remember { mutableStateOf(0L) }
    var dayChoosed by remember { mutableStateOf("") }
    var datePickerState = rememberDatePickerState()
    //TODO NotificationPermission()



    Box(modifier = Modifier
        .background(gradient)
        .fillMaxSize()){
        Column(){
            Card (shape = RoundedCornerShape(15.dp), modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, end = 10.dp,top=0.dp, bottom  =5.dp)
            ){
                Text(text = "Hi 👋, "+ DateTimeUtil.toTitleCase(UserViewModel.userP.value?.name?:""), textAlign = TextAlign.Center, modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            listOf<Color>(
                                Color(rgb(96, 165, 250)),
                                Color(rgb(52, 211, 153))
                            )
                        )
                    ), fontWeight = FontWeight(800), fontSize = 15.sp
                )
                Text(text = "   It’s  ${DateTimeUtil.toTitleCase(todayDay())}, ${DateTimeUtil.getDateMonthFromLong(System.currentTimeMillis())} ✨ ", textAlign = TextAlign.Center, modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Brush.linearGradient(
                            listOf<Color>(
                                Color(rgb(96, 165, 250)),
                                Color(rgb(52, 211, 153))
                            )
                        )
                    ), fontWeight = FontWeight(800), fontSize = 15.sp
                )
            }
            Card(shape = RoundedCornerShape(20.dp), modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    Button(onClick = {
                        today= DateTimeUtil.todayDay().lowercase()
                    }) {
                        Text("Today")
                    }
                    Button(onClick = {
                        today = DateTimeUtil.tomorrowDay().lowercase()
                    }) {
                        Text("Tomorrow")
                    }
                    Button(onClick = {
                        showDatePicker = true }) {
                        Text("Pick Date 📅")
                    }
                }
            }
            if (todayRoutine.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No classes today 🎉  $today",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "            Enjoy the break 😌\nTouch grass or touch code \uD83C\uDF3F\uD83D\uDCBB\n",
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }

            LazyColumn(Modifier.fillMaxSize()) {
                items(todayRoutine){period->
                    ScheduleCardView(period)
                }
            }

        }
    }
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                Button(onClick = {
                    dateChoosed = datePickerState.selectedDateMillis ?: 0L
                    dayChoosed = DateTimeUtil.getDayFromLong(dateChoosed)
                    today=dayChoosed.lowercase()
                    showDatePicker = false
                }) { Text("Ok") }
            },
            dismissButton = {
                Button(onClick = {
                    showDatePicker = false
                }) { Text("Cancel") }
            }) {
            DatePicker(state = datePickerState)
        }
    }

}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun HomeScreenPreview(){
    HomeScreen()
}