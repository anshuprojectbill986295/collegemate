package com.anshu.collegemate.ui.View.Screens

import android.graphics.Color.rgb
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anshu.collegemate.Data.Model.HomeScreen.RoutineResolver
import com.anshu.collegemate.Utils.DateTimeUtil
import com.anshu.collegemate.Utils.DateTimeUtil.todayDay
import com.anshu.collegemate.ui.View.Others.BirthdayBanner
import com.anshu.collegemate.ui.View.Others.DataCardView.CancelledScheduleCardView
import com.anshu.collegemate.ui.View.Others.DataCardView.ScheduleCardView
import com.anshu.collegemate.ui.View.Others.Permission.NotificationPermission
import com.anshu.collegemate.ui.ViewModel.AnnouncementViewModel
import com.anshu.collegemate.ui.ViewModel.UserViewModel

enum class FUTUREDAYTYPE{
    TODAY,TOMORROW,OTHERS
}


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(viewModel: AnnouncementViewModel = viewModel()){
    val gradient = Brush.linearGradient(colors = listOf(Color(0xfff0f4f8)
        ,Color(0xffe0e7ff)), start = Offset(0f,0f),end= Offset(100f,100f))
    var dateChosenInLong by remember { mutableStateOf(System.currentTimeMillis()) }
    val dateChosenInString = DateTimeUtil.getDateFromLong(dateChosenInLong)
    val dayChosen = DateTimeUtil.getDayFromLong(dateChosenInLong)
    val userEmail = UserViewModel.userP.value?.email ?: ""
    val todayRoutine = RoutineResolver.resolveDayRoutine(dayChosen, userEmail)
    var showDatePicker by remember{(mutableStateOf(false))}
    var dateChosenInCalender by remember { mutableStateOf(0L) }



    val datePickerState = rememberDatePickerState()
    var selectedButton by remember { mutableStateOf(0) }
    val classCancelledList by viewModel.classCancelledOnDate.collectAsState()


    LaunchedEffect(dateChosenInString) {
        viewModel.getClassCancelled(dateChosenInString)
        Log.e("xx",dayChosen+dateChosenInString)

    }
    NotificationPermission()


Log.e("today",dayChosen+dateChosenInString)
    
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
                Text(text = "   Today is ${DateTimeUtil.toTitleCase(todayDay())}, ${DateTimeUtil.getDateMonthFromLong(System.currentTimeMillis())} ✨ ", textAlign = TextAlign.Center, modifier = Modifier
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
            BirthdayBanner(userEmail)
            Box( modifier = Modifier.fillMaxWidth().background(
                Color.Transparent)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                    Button(colors = getButtonColors(selectedButton==0),onClick = {
                        dateChosenInLong = System.currentTimeMillis()
                        selectedButton=0
                    },
                        border = BorderStroke(1.dp, Color(0xFFB0B0B0))) {
                        Text("Today")
                    }
                    Button(colors = getButtonColors(selectedButton==1),onClick = {
                        dateChosenInLong = System.currentTimeMillis().plus(24*60*60*1000)
                        selectedButton=1
                    },
                        border = BorderStroke(1.dp, Color(0xFFB0B0B0))) {
                        Text("Tomorrow")
                    }
                    Button(colors = getButtonColors(selectedButton==2),onClick = {
                        showDatePicker = true
                        },
                        border = BorderStroke(1.dp, Color(0xFFB0B0B0))) {
                        if(selectedButton!=2){
                            Text("\uD83D\uDCC5 Pick Date")
                        }
                        else if(selectedButton==2){
                            Text("\uD83D\uDCC5 ${DateTimeUtil.getDateMonthFromLong(dateChosenInCalender)}")
                        }
                    }

                }
            }
            // ----------------------------------------------------
            Log.d("CancelledList","${classCancelledList.size}")
            if (todayRoutine.isEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "No classes today 🎉  $dayChosen",
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
            else if(classCancelledList.isNotEmpty()){

                //it is working well
                if (classCancelledList.size == todayRoutine.size){
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "All classes cancelled today, Mass Bunk..",
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
                else{

                    LazyColumn(Modifier.fillMaxSize()) {
                        items(todayRoutine){period->
                          val isThisPeriodCancelled = classCancelledList.any{it.subjectCode == period.subjectCode && it.classStartTime== period.startTime}

                            if (isThisPeriodCancelled){
                                CancelledScheduleCardView(period)
                            }
                            else{
                                ScheduleCardView(period)
                            }
                        }
                    }
                }

            }
            else{
                LazyColumn(Modifier.fillMaxSize()) {
                    items(todayRoutine){period->
                        ScheduleCardView(period)
                    }
                }
            }



        }
    }
    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                Button(onClick = {
                    dateChosenInCalender = datePickerState.selectedDateMillis ?: System.currentTimeMillis()
                    dateChosenInLong = dateChosenInCalender
                    showDatePicker = false
                    selectedButton=2
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
@Composable
fun getButtonColors(isActive: Boolean): ButtonColors{
    if (isActive){
        val x = ButtonDefaults.buttonColors(containerColor = Color(0xFF5B4B8A), contentColor = Color.White)
        return x
    }
    else{
        val y = ButtonDefaults.buttonColors(containerColor = Color(0xFFE8E8EC), contentColor = Color(0xFF444444))
        return y
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
@Preview(showBackground = true)
fun HomeScreenPreview(){
    HomeScreen()
}