package com.anshu.collegemate.ui.View.Others.MBS

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anshu.collegemate.Data.Model.Announcement.ANNOUNCEMENTTYPE
import com.anshu.collegemate.Data.Model.Announcement.AnnouncementCard
import com.anshu.collegemate.Data.Model.HomeScreen.RoutineSeed
import com.anshu.collegemate.Data.Model.HomeScreen.ScheduleCardData
import com.anshu.collegemate.Utils.DateTimeUtil
import com.anshu.collegemate.Utils.DateTimeUtil.getDateFromLong
import com.anshu.collegemate.ui.ViewModel.AnnouncementViewModel
import com.anshu.collegemate.ui.ViewModel.UserViewModel
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnnouncementMBS(viewModel: AnnouncementViewModel, onDismiss:()->Unit){

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val generalClicked = remember { mutableStateOf(false) }
    val cancellationClicked = remember { mutableStateOf(false) }
    var textGeneral by remember { mutableStateOf("") }
    var cancellationDate by remember { mutableLongStateOf(0L) }
    var cancellationDay by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()
    var canceledClass by remember { mutableStateOf(ScheduleCardData()) }
    var typeChoosed by remember { mutableStateOf(false) }
    var type by remember { mutableStateOf<TypesForAnnouncement>(TypesForAnnouncement.NONE) }
    var currentStepForGeneral by remember { mutableStateOf(StepsForGeneral.DETAILS) }
    var currentStepForCancellation by remember { mutableStateOf(StepsForCancellation.CHOOSE_DATE) }



    ModalBottomSheet(
        onDismissRequest = { onDismiss()
            cancellationClicked.value=false
            generalClicked.value=false
        },
        containerColor = Color.White,

        sheetState = sheetState
    ) {
        if(!typeChoosed){
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(onClick = {
                    type = TypesForAnnouncement.GENERAL
                    typeChoosed = true
                }) {
                    Text(text = "General")
                }
                Button(onClick = {
                    type = TypesForAnnouncement.CANCELLATION
                    typeChoosed = true

                })
                { Text(text = "Cancellation") }
            }
        }



        if (type == TypesForAnnouncement.GENERAL) {
            when(currentStepForGeneral){
                StepsForGeneral.DETAILS ->{
                    GeneralDetails(textGeneral,type, onTextFieldValueChange = {textGeneral = it}
                        , onNextClicked = {currentStepForGeneral = StepsForGeneral.REVIEW})
                }
                StepsForGeneral.REVIEW->{
                    GeneralReview(type,textGeneral, onConfirmClicked = {
                        val a = AnnouncementCard(
                            id = "",
                            type = ANNOUNCEMENTTYPE.GENERAL,
                            message = textGeneral,
                            announcerName = UserViewModel.userP.value!!.name,
                            announcerProfileUrl = UserViewModel.userP.value?.photoURL ?: "", // ADDED HERE
                            createdAt = System.currentTimeMillis()
                        )
                        scope.launch {
                            viewModel.saveAnnouncement(a)
                            Toast.makeText(
                                context,
                                "Announcement Made Successfully",
                                Toast.LENGTH_LONG
                            ).show()
                            onDismiss()

                        }
                    })
                }
            }
        }
        else if(type == TypesForAnnouncement.CANCELLATION){
            when(currentStepForCancellation){
                StepsForCancellation.CHOOSE_DATE -> {
                    DateSelection(cancellationDate, onNextClicked = {currentStepForCancellation=
                        StepsForCancellation.CHOOSE_SUBJECT}, onCalendarClicked = {
                        showDatePicker = true
                    })
                }
                StepsForCancellation.CHOOSE_SUBJECT -> {
                    ChooseSubject(cancellationDay, onSubjectClicked = {
                        canceledClass=it
                        currentStepForCancellation = StepsForCancellation.REVIEW
                    },cancellationDate=cancellationDate)
                }
                StepsForCancellation.REVIEW->{
                    ReviewCancellation(type,canceledClass, cancellationDate, onConfirmClicked = {
                        val message = DateTimeUtil.classCancelledMessage(
                            getDateFromLong(cancellationDate),
                            cancellationDay,
                            canceledClass.name
                        )
                        val announcementCard = AnnouncementCard(
                            type = ANNOUNCEMENTTYPE.CANCELLATION,
                            message = message,
                            announcerName = UserViewModel.userP.value!!.name,
                            announcerProfileUrl = UserViewModel.userP.value?.photoURL ?: "", // ADDED HERE
                            createdAt = System.currentTimeMillis(),

                            //cancel related...
                            cancelDate = getDateFromLong(cancellationDate),
                            day = cancellationDay,
                            subjectCode = canceledClass.subjectCode,
                            classStartTime = canceledClass.startTime)
                        scope.launch {
                            viewModel.saveAnnouncement(announcementCard)
                            Toast.makeText(
                                context,
                                "Class Canceled Successfully",
                                Toast.LENGTH_LONG
                            ).show()
                            onDismiss()
                        }
                    })
                }
            }
        }

    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                Button(onClick = {
                    cancellationDate = datePickerState.selectedDateMillis ?: System.currentTimeMillis()
                    cancellationDay = DateTimeUtil.getDayFromLong(cancellationDate)
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
// For GENERAL
@Composable
fun GeneralDetails(
    textGeneral:String,
    type: TypesForAnnouncement,
    onTextFieldValueChange:(newValue:String)->Unit,
    onNextClicked:()-> Unit
){
    Column(Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(type.toString()+".")
        Text("📢 Drop the updates") // Slightly more casual
        OutlinedTextField(
            value = textGeneral,
            onValueChange = { onTextFieldValueChange(it)},
            label = { Text("✍️ Spill the tea... (What's happening?)") } // Gen Z hint
        )
        Button(
            onClick = {onNextClicked()},
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Next")
        }
    }
}
@Composable
fun GeneralReview(type: TypesForAnnouncement, textGeneral: String,onConfirmClicked:()->Unit){
    Column(Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text("Please Review Your Announcement Before Posting.")
        Text(type.toString())
        Text("Update: ${textGeneral}")
        Button(onClick = {onConfirmClicked()}) {
            Text("Confirm")
        }
    }
}

//FOR CANCELLATION
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateSelection(
    cancellationDate:Long,
    onNextClicked: () -> Unit,
    onCalendarClicked:()->Unit
){
    Column(Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text("When is class canceled?")
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically) {
            Text("Date: ")
            Button(onClick = {
                onCalendarClicked()
            }) {
                if (cancellationDate==0L){
                    Text("📅 Select Date")
                }
                else{
                    Text("📅 ${DateTimeUtil.getDateMonthFromLong(cancellationDate)}")
                }
            }
        }
        Button(onClick = {onNextClicked()}) {
            Text("Next->")
        }
    }

}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChooseSubject(
    cancellationDay: String,
    onSubjectClicked:(subject:ScheduleCardData)-> Unit,
    announcementViewModel: AnnouncementViewModel = viewModel(),
    cancellationDate: Long
){
    LaunchedEffect(cancellationDay) {
        announcementViewModel.getClassCancelled(DateTimeUtil.getDateFromLong(cancellationDate))
    }
    val classCancelled  = announcementViewModel.classCancelledOnDate.collectAsState()
    Column(Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text("Which Class on $cancellationDay?")
        val classes = RoutineSeed.weeklyRoutine[cancellationDay]?:emptyList()
        Log.e("classToCancel","${classes.size}   ${classes.toString()}   ${classCancelled.value.size}   ${classCancelled.value.toString()}  ")
        LazyColumn() {
            items(classes) { period ->

                val isThisClassCancelled = classCancelled.value.any { it.classStartTime == period.startTime }
                if (isThisClassCancelled){
                    Row(
                        Modifier.fillMaxWidth()
                            .padding(top = 16.dp)
                    ) {
                        Text(
                            text = period.name+"~Already cancelled.",
                            color= Color.Gray
                        )
                    }
                }
                else{
                    Row(
                        Modifier.fillMaxWidth()
                            .clickable(onClick = { onSubjectClicked(period) })
                            .padding(top = 16.dp)
                    ) {
                        Text(period.name)
                    }
                }
            }
        }
    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReviewCancellation(
    type: TypesForAnnouncement,
    subject: ScheduleCardData,
    cancellationDate: Long,
    onConfirmClicked: () -> Unit
){
    Column(Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text("Are you sure want to cancel the below class")
        Text(subject.name)
        Text(subject.subjectCode)
        Text(subject.instructor)
        Text(DateTimeUtil.getDateMonthFromLong(cancellationDate))
        Button(onClick = {onConfirmClicked()}) {
            Text("Confirm")
        }
    }
}