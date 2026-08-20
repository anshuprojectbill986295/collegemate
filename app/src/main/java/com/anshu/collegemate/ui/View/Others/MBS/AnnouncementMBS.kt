package com.anshu.collegemate.ui.View.Others.MBS

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anshu.collegemate.Data.Model.Announcement.ANNOUNCEMENTTYPE
import com.anshu.collegemate.Data.Model.Announcement.AnnouncementCard
import com.anshu.collegemate.Data.Model.HomeScreen.RoutineResolver
import com.anshu.collegemate.Data.Model.HomeScreen.ScheduleCardData
import com.anshu.collegemate.Utils.DateTimeUtil
import com.anshu.collegemate.Utils.DateTimeUtil.getDateFromLong
import com.anshu.collegemate.ui.ViewModel.AnnouncementViewModel
import com.anshu.collegemate.ui.ViewModel.UserViewModel
import kotlinx.coroutines.launch
import androidx.activity.compose.BackHandler


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AnnouncementMBS(viewModel: AnnouncementViewModel, onDismiss:()->Unit){

    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
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
        onDismissRequest = { onDismiss() },
        containerColor = Color.White,
        sheetState = sheetState,
        dragHandle = null,
        properties = ModalBottomSheetProperties(
            shouldDismissOnBackPress = false
        )
    ) {
        BackHandler(enabled = true) {
            scope.launch {
                sheetState.hide()
                onDismiss()
            }
        }

        Column {
            // Header with dynamic title and step indicator
            val totalSteps = if (type == TypesForAnnouncement.CANCELLATION) 3 else if (type == TypesForAnnouncement.GENERAL) 2 else 1
            val currentStep = when {
                !typeChoosed -> 1
                type == TypesForAnnouncement.GENERAL -> if (currentStepForGeneral == StepsForGeneral.DETAILS) 1 else 2
                type == TypesForAnnouncement.CANCELLATION -> when(currentStepForCancellation) {
                    StepsForCancellation.CHOOSE_DATE -> 1
                    StepsForCancellation.CHOOSE_SUBJECT -> 2
                    StepsForCancellation.REVIEW -> 3
                }
                else -> 1
            }
            
            val title = when {
                !typeChoosed -> "Announcement Type"
                type == TypesForAnnouncement.GENERAL -> "General Update"
                type == TypesForAnnouncement.CANCELLATION -> "Class Cancellation"
                else -> "New Announcement"
            }

            MBSHeader(
                title = title,
                subtitle = "Share important updates with your mates",
                currentStep = currentStep,
                totalSteps = totalSteps,
                onDismiss = onDismiss
            )

            Box(modifier = Modifier.padding(16.dp)) {
                if(!typeChoosed){
                    TypeSelectionStep(onTypeSelected = { 
                        type = it
                        typeChoosed = true
                    })
                }

                if (type == TypesForAnnouncement.GENERAL) {
                    when(currentStepForGeneral){
                        StepsForGeneral.DETAILS ->{
                            GeneralDetails(textGeneral, onTextFieldValueChange = {textGeneral = it}
                                , onNextClicked = {currentStepForGeneral = StepsForGeneral.REVIEW})
                        }
                        StepsForGeneral.REVIEW->{
                            GeneralReview(textGeneral, onConfirmClicked = {
                                val a = AnnouncementCard(
                                    id = "",
                                    type = ANNOUNCEMENTTYPE.GENERAL,
                                    message = textGeneral,
                                    announcerName = UserViewModel.userP.value!!.name,
                                    announcerProfileUrl = UserViewModel.userP.value?.photoURL ?: "",
                                    createdAt = System.currentTimeMillis()
                                )
                                scope.launch {
                                    viewModel.saveAnnouncement(a)
                                    Toast.makeText(context, "Announcement Made Successfully", Toast.LENGTH_LONG).show()
                                    onDismiss()
                                }
                            }, onBackClicked = { currentStepForGeneral = StepsForGeneral.DETAILS })
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
                            },cancellationDate=cancellationDate, onBackClicked = {
                                currentStepForCancellation = StepsForCancellation.CHOOSE_DATE
                            })
                        }
                        StepsForCancellation.REVIEW->{
                            ReviewCancellation(canceledClass, cancellationDate, onConfirmClicked = {
                                val message = DateTimeUtil.classCancelledMessage(
                                    getDateFromLong(cancellationDate),
                                    cancellationDay,
                                    canceledClass.name
                                )
                                val announcementCard = AnnouncementCard(
                                    type = ANNOUNCEMENTTYPE.CANCELLATION,
                                    message = message,
                                    announcerName = UserViewModel.userP.value!!.name,
                                    announcerProfileUrl = UserViewModel.userP.value?.photoURL ?: "",
                                    createdAt = System.currentTimeMillis(),
                                    cancelDate = getDateFromLong(cancellationDate),
                                    day = cancellationDay,
                                    subjectCode = canceledClass.subjectCode,
                                    subjectName = canceledClass.name,
                                    classStartTime = canceledClass.startTime,
                                    classEndTime = canceledClass.endTime)
                                scope.launch {
                                    viewModel.saveAnnouncement(announcementCard)
                                    Toast.makeText(context, "Class Canceled Successfully", Toast.LENGTH_LONG).show()
                                    onDismiss()
                                }
                            }, onBackClicked = {
                                currentStepForCancellation = StepsForCancellation.CHOOSE_SUBJECT
                            })
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    cancellationDate = datePickerState.selectedDateMillis ?: System.currentTimeMillis()
                    cancellationDay = DateTimeUtil.getDayFromLong(cancellationDate)
                    showDatePicker = false
                }) { Text("Confirm", fontWeight = FontWeight.Bold) }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text("Cancel") }
            }) {
            DatePicker(state = datePickerState)
        }
    }
}

@Composable
fun TypeSelectionStep(onTypeSelected: (TypesForAnnouncement) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        MBSSelectionCard(
            title = "General Update",
            subtitle = "Share news, notes, or general information",
            icon = Icons.Default.Notifications,
            onClick = { onTypeSelected(TypesForAnnouncement.GENERAL) }
        )
        MBSSelectionCard(
            title = "Class Cancellation",
            subtitle = "Notify everyone about a cancelled lecture",
            icon = Icons.Default.Warning,
            onClick = { onTypeSelected(TypesForAnnouncement.CANCELLATION) }
        )
    }
}

@Composable
fun GeneralDetails(
    textGeneral:String,
    onTextFieldValueChange:(newValue:String)->Unit,
    onNextClicked:()-> Unit
){
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = "Enter details below",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF475569)
        )
        OutlinedTextField(
            value = textGeneral,
            onValueChange = { onTextFieldValueChange(it)},
            placeholder = { Text("What would you like to announce?") },
            modifier = Modifier.fillMaxWidth().height(150.dp),
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6366F1),
                unfocusedBorderColor = Color(0xFFE2E8F0)
            )
        )
        MBSPrimaryButton(
            text = "Next",
            onClick = onNextClicked,
            enabled = textGeneral.isNotBlank()
        )
    }
}

@Composable
fun GeneralReview(textGeneral: String, onConfirmClicked:()->Unit, onBackClicked: () -> Unit){
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = "Review your announcement",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF475569)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("MESSAGE", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B))
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = textGeneral, fontSize = 16.sp, color = Color(0xFF1E293B))
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            MBSSecondaryButton(text = "Back", onClick = onBackClicked, modifier = Modifier.weight(1f))
            MBSPrimaryButton(text = "Post Now", onClick = onConfirmClicked, modifier = Modifier.weight(1f))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateSelection(
    cancellationDate:Long,
    onNextClicked: () -> Unit,
    onCalendarClicked:()->Unit
){
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = "When is the class cancelled?",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF475569)
        )
        
        MBSSelectionCard(
            title = if (cancellationDate == 0L) "Select Date" else DateTimeUtil.getDateMonthFromLong(cancellationDate),
            subtitle = if (cancellationDate == 0L) "Tap to open calendar" else "Selected cancellation date",
            icon = Icons.Default.DateRange,
            onClick = onCalendarClicked
        )

        MBSPrimaryButton(
            text = "Next",
            onClick = onNextClicked,
            enabled = cancellationDate != 0L
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChooseSubject(
    cancellationDay: String,
    onSubjectClicked:(subject:ScheduleCardData)-> Unit,
    announcementViewModel: AnnouncementViewModel = viewModel(),
    cancellationDate: Long,
    onBackClicked: () -> Unit
){
    LaunchedEffect(cancellationDay) {
        announcementViewModel.getClassCancelled(DateTimeUtil.getDateFromLong(cancellationDate))
    }
    val classCancelled  = announcementViewModel.classCancelledOnDate.collectAsState()
    val classes = RoutineResolver.resolveDayRoutine(
        cancellationDay,
        UserViewModel.userP.value?.email ?: ""
    )

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Which class on $cancellationDay?",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF475569)
            )
        }

        LazyColumn(
            modifier = Modifier.heightIn(max = 400.dp),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            items(classes) { period ->
                val isThisClassCancelled = classCancelled.value.any { 
                    it.subjectCode == period.subjectCode && it.classStartTime == period.startTime 
                }
                MBSSubjectCard(
                    subject = period,
                    isCancelled = isThisClassCancelled,
                    onClick = { onSubjectClicked(period) }
                )
            }
        }
        
        MBSSecondaryButton(text = "Change Date", onClick = onBackClicked)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReviewCancellation(
    subject: ScheduleCardData,
    cancellationDate: Long,
    onConfirmClicked: () -> Unit,
    onBackClicked: () -> Unit
){
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = "Confirm cancellation details",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF475569)
        )
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(20.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8FAFC)),
            border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE2E8F0))
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Column {
                    Text("SUBJECT", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B))
                    Text(text = subject.name, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                    Text(text = subject.subjectCode, fontSize = 13.sp, color = Color(0xFF475569))
                }
                Column {
                    Text("DATE & TIME", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B))
                    Text(
                        text = "${DateTimeUtil.getDateMonthFromLong(cancellationDate)} | ${DateTimeUtil.convert(subject.startTime)} - ${DateTimeUtil.convert(subject.endTime)}",
                        fontSize = 14.sp,
                        color = Color(0xFF1E293B)
                    )
                }
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            MBSSecondaryButton(text = "Back", onClick = onBackClicked, modifier = Modifier.weight(1f))
            MBSPrimaryButton(text = "Confirm", onClick = onConfirmClicked, modifier = Modifier.weight(1f))
        }
    }
}
