package com.anshu.collegemate.ui.View.Others.MBS

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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




    ModalBottomSheet(
        onDismissRequest = { onDismiss()
            cancellationClicked.value=false
            generalClicked.value=false
        },
        containerColor = Color.White,

        sheetState = sheetState
    ) {

        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(onClick = {
                generalClicked.value = true
                cancellationClicked.value = false
            }) {
                Text(text = "General")
            }
            Button(onClick = {
                cancellationClicked.value = true
                generalClicked.value = false
            })
            { Text(text = "Cancellation") }
        }
        if (generalClicked.value) {

            Box() {
                Column(
                    horizontalAlignment = Alignment
                        .CenterHorizontally, verticalArrangement = Arrangement.Center
                ) {
                    Text("📢 Post a quick update")
                    OutlinedTextField(
                        value = textGeneral,
                        onValueChange = { textGeneral = it },
                        label = { Text("✍️ What's the update?") })

                    Button(onClick = {
                        val a = AnnouncementCard(
                            "", "general", textGeneral, UserViewModel.userP.value!!.name,
                            System.currentTimeMillis()
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
                    }) { Text("Send") }
                    Button(onClick = { onDismiss() }) {
                        Text("Back")
                    }
                }

            }

        }
        if (cancellationClicked.value) {

            Box() {
                Column() {
                    Text("Cancel")
                    Text("When?")
                    Row() {
                        Button(onClick = {
                            cancellationDate = System.currentTimeMillis()
                            cancellationDay = DateTimeUtil.todayDay()
                        }) {
                            Text("Today")
                        }
                        Button(onClick = {
                            cancellationDate = System.currentTimeMillis()+ java.util.concurrent.TimeUnit.DAYS.toMillis(1)
                            cancellationDay = DateTimeUtil.tomorrowDay()
                        }) {
                            Text("Tomorrow")
                        }
                        Button(onClick = { showDatePicker = true }) {
                            Text("Pick Date 📅")
                        }
                    }
                    Text("Which Class on $cancellationDay?")
                    val classes =
                        RoutineSeed.weeklyRoutine.get(cancellationDay) ?: emptyList()
                    LazyColumn() {
                        items(classes) {
                            Button(onClick = { canceledClass = it }) { Text(it.name) }
                        }
                    }
                    Button(onClick = {
                        val message = DateTimeUtil.classCancelledMessage(
                            getDateFromLong(cancellationDate),
                            cancellationDay,
                            canceledClass.name
                        )
                        val announcementCard = AnnouncementCard(
                            type = "cancel", message = message,

                            announcerName = UserViewModel.userP.value!!.name,
                            createdAt = System.currentTimeMillis(),

                            //cancel related...
                            cancelDate = getDateFromLong(cancellationDate),
                            day = cancellationDay,
                            subjectCode = canceledClass.subjectCode
                        )
                        scope.launch {
                            viewModel.saveAnnouncement(announcementCard)
                            Toast.makeText(
                                context,
                                "Class Canceled Successfully",
                                Toast.LENGTH_LONG
                            ).show()
                            onDismiss()
                        }

                    }) {
                        Text("Confirm Cancellation")
                    }
                    Button(onClick = { onDismiss() }) {
                        Text("Back")
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
                    cancellationDate = datePickerState.selectedDateMillis ?: 0L
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