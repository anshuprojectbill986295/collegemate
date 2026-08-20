package com.anshu.collegemate.ui.View.Others.MBS

import androidx.activity.compose.BackHandler
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Star
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
import androidx.core.content.FileProvider
import com.anshu.collegemate.Data.Model.AssignmentTest.AssignmentCard
import com.anshu.collegemate.Data.Model.AssignmentTest.TestCard
import com.anshu.collegemate.Data.Model.AssignmentTest.UploadResult
import com.anshu.collegemate.Data.Model.HomeScreen.RoutineResolver
import com.anshu.collegemate.Data.Model.HomeScreen.ScheduleCardData
import com.anshu.collegemate.R
import com.anshu.collegemate.Utils.DateTimeUtil
import com.anshu.collegemate.ui.View.Others.CustomizedButtons.UploadButton
import com.anshu.collegemate.ui.View.Others.CustomizedButtons.activeSource
import com.anshu.collegemate.ui.ViewModel.AssignmentTestVM
import com.anshu.collegemate.ui.ViewModel.UploadImgPDFVM
import com.anshu.collegemate.ui.ViewModel.UserViewModel
import kotlinx.coroutines.launch
import java.io.File
import java.time.Instant
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun AssignmentTestMBS(assTestVM: AssignmentTestVM, onDismiss:()->Unit, uploadImgPDFVM: UploadImgPDFVM) {
    var subjectName by remember { mutableStateOf("") }
    var subjectCode by remember { mutableStateOf("") }
    var contentText by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var lastDateOfAssignment by remember{mutableStateOf(0L)}
    val datePickerState = rememberDatePickerState()
    var createdAt by remember { mutableStateOf(0L) }
    var createdBy by remember { mutableStateOf("") }
    val context =   LocalContext.current
    val scope = rememberCoroutineScope()
    var currentStep by remember { mutableStateOf(steps.TYPE) }
    var type by remember { mutableStateOf<TypesForAssignmentTest>(TypesForAssignmentTest.NONE) }
    val result by uploadImgPDFVM.result.collectAsState()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)


    ModalBottomSheet(
        onDismissRequest = {
            uploadImgPDFVM.resetUploadState()
            onDismiss()
        },
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

        Column{
            val totalSteps = 4
            val stepIndex = when(currentStep) {
                steps.TYPE -> 1
                steps.SUBJECT -> 2
                steps.DETAILS -> 3
                steps.REVIEW -> 4
            }

            val title = when(type) {
                TypesForAssignmentTest.TEST -> "Add Test"
                TypesForAssignmentTest.ASSIGNMENT -> "Add Assignment"
                else -> "New Task"
            }

            MBSHeader(
                title = title,
                subtitle = "Keep your schedule organized",
                currentStep = stepIndex,
                totalSteps = totalSteps,
                onDismiss = onDismiss
            )

            Box(modifier = Modifier.padding(16.dp)) {
                when(currentStep){
                    steps.TYPE->{
                        TypeStep {
                            type = it
                            currentStep = steps.SUBJECT
                        }
                    }
                    steps.SUBJECT->{
                        SubjectStep(RoutineResolver.resolveDistinctClasses(UserViewModel.userP.value?.email ?: ""), onBack = {
                            currentStep = steps.TYPE
                        }) { sc->
                            subjectName = sc.name
                            subjectCode = sc.subjectCode
                            currentStep = steps.DETAILS
                        }
                    }
                    steps.DETAILS->{
                        DetailStep(
                            type,subjectName,contentText,lastDateOfAssignment,
                            onQuestionChange = {contentText = it }, 
                            onCalenderClicked = { showDatePicker = true }, 
                            onNextClicked = { currentStep = steps.REVIEW },
                            onBackClicked = { currentStep = steps.SUBJECT },
                            uploadImgPDFVM=uploadImgPDFVM)
                    }

                    steps.REVIEW -> {
                        val hasAttachment = result is UploadResult.Success
                        when (type) {
                            TypesForAssignmentTest.TEST -> {
                                ReviewTestDetail(
                                    subjectName, subjectCode, contentText, lastDateOfAssignment,
                                    hasAttachment = hasAttachment,
                                    onConfirm = {
                                        createdBy = UserViewModel.userP.value?.name?:""
                                        createdAt = System.currentTimeMillis()
                                        val tc = TestCard(
                                            subjectName = subjectName,
                                            subjectCode = subjectCode,
                                            createdAt = createdAt,
                                            createdBy = createdBy,
                                            testDate = if(lastDateOfAssignment==0L)
                                            {System.currentTimeMillis().plus(24*60*60*1000)}
                                            else{lastDateOfAssignment},
                                            syllabus = contentText,
                                            syllabusImageUrl = if (result is UploadResult.Success &&
                                                (result as UploadResult.Success).type!= activeSource.FILES){(result as UploadResult.Success).downloadLink}
                                            else {""},
                                            syllabusFileUrl = if (result is UploadResult.Success &&
                                                (result as UploadResult.Success).type == activeSource.FILES){(result as UploadResult.Success).downloadLink}
                                            else {""}
                                        )
                                        assTestVM.addTest(tc)
                                        uploadImgPDFVM.resetUploadState()
                                        Toast.makeText(context,"Test successfully added",Toast.LENGTH_LONG).show()
                                        onDismiss()
                                    },
                                    onCancel = { currentStep = steps.DETAILS }
                                )
                            }
                            TypesForAssignmentTest.ASSIGNMENT -> {
                                ReviewAssignmentDetail(
                                    subjectName, subjectCode, contentText, lastDateOfAssignment,
                                    hasAttachment = hasAttachment,
                                    onConfirm = {
                                        createdBy = UserViewModel.userP.value?.name?:""
                                        createdAt = System.currentTimeMillis()
                                        val ass = AssignmentCard(
                                            subjectName = subjectName,
                                            subjectCode = subjectCode,
                                            questionText = contentText,
                                            createdBy = createdBy,
                                            createdAt = createdAt,
                                            lastDateToSubmit = if(lastDateOfAssignment==0L)
                                            {System.currentTimeMillis().plus(24*60*60*1000)}
                                            else{lastDateOfAssignment}
                                            ,
                                            questionImageUrl = if (result is UploadResult.Success &&
                                                (result as UploadResult.Success).type!= activeSource.FILES){(result as UploadResult.Success).downloadLink}
                                            else {""},
                                            questionFileUrl = if (result is UploadResult.Success &&
                                                (result as UploadResult.Success).type == activeSource.FILES){(result as UploadResult.Success).downloadLink}
                                            else {""}
                                        )
                                        assTestVM.addAssignment(ass)
                                        uploadImgPDFVM.resetUploadState()
                                        Toast.makeText(context,"Assignment Successfully Added!",Toast.LENGTH_LONG).show()
                                        onDismiss()
                                    },
                                    onCancel = { currentStep = steps.DETAILS }
                                )
                            }
                            TypesForAssignmentTest.NONE -> {}
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }

    if (showDatePicker){
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val selectedMillis = datePickerState.selectedDateMillis ?: 0L
                    val endOfDayMillis = Instant.ofEpochMilli(selectedMillis)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                        .atTime(23, 59, 59)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli()

                    lastDateOfAssignment = endOfDayMillis
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
fun TypeStep(onTypeSelected: (TypesForAssignmentTest) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "What would you like to add?",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF475569)
        )
        MBSSelectionCard(
            title = "Assignment",
            subtitle = "Add a new assignment with deadline",
            icon = Icons.Default.Edit,
            onClick = { onTypeSelected(TypesForAssignmentTest.ASSIGNMENT) }
        )
        MBSSelectionCard(
            title = "Test",
            subtitle = "Schedule an upcoming test or quiz",
            icon = Icons.Default.Star,
            onClick = { onTypeSelected(TypesForAssignmentTest.TEST) }
        )
    }
}

@Composable
fun SubjectStep(
    subjects: List<ScheduleCardData>,
    onBack: () -> Unit,
    onSubjectSelect:(sc: ScheduleCardData)->Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = "Select subject",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF475569)
        )
        LazyColumn(
            modifier = Modifier.heightIn(max = 400.dp),
            contentPadding = PaddingValues(vertical = 4.dp)
        ) {
            items(subjects) { subject ->
                MBSSubjectCard(subject = subject, onClick = { onSubjectSelect(subject) })
            }
        }
        MBSSecondaryButton(text = "Back", onClick = onBack)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DetailStep(
    type: TypesForAssignmentTest,
    subjectName: String,
    question:String,
    lastDate:Long,
    onQuestionChange:(newValue:String)->Unit,
    onCalenderClicked:()->Unit,
    onNextClicked: ()-> Unit,
    onBackClicked: () -> Unit,
    uploadImgPDFVM: UploadImgPDFVM
){
    val context = LocalContext.current
    val result by uploadImgPDFVM.result.collectAsState()
    val isSuccess = result is UploadResult.Success
    val isUploading = result is UploadResult.Uploading
    val progress = if (result is UploadResult.Uploading) {
        (result as UploadResult.Uploading).progress ?: 0f
    } else {
        0f
    }
    var source by remember { mutableStateOf(activeSource.NONE) }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            uploadImgPDFVM.upload(uri,source)
        }
        else source = activeSource.NONE
    }
    
    val fileForCamera = File(context.cacheDir, "captured_${System.currentTimeMillis()}.jpg")
    val fileForCameraURI = FileProvider.getUriForFile(context, "${context.packageName}.provider", fileForCamera)
    val cameraLauncher = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { isSuccessInCamera ->
        if (isSuccessInCamera) {
            uploadImgPDFVM.upload(fileForCameraURI, source)
        }
    }

    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Column {
            Text(
                text = if (type == TypesForAssignmentTest.TEST) "Test details" else "Assignment details",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF475569)
            )
            Text(text = subjectName, fontSize = 13.sp, color = Color(0xFF6366F1), fontWeight = FontWeight.Bold)
        }

        OutlinedTextField(
            value = question, 
            onValueChange = {onQuestionChange(it)},
            placeholder = { Text(if (type == TypesForAssignmentTest.TEST) "Enter syllabus or topics..." else "Enter question details...") },
            modifier = Modifier.fillMaxWidth().height(120.dp),
            shape = RoundedCornerShape(20.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = Color(0xFF6366F1),
                unfocusedBorderColor = Color(0xFFE2E8F0)
            )
        )

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("DATE", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B))
            MBSSelectionCard(
                title = if (lastDate == 0L) "Select Date" else DateTimeUtil.getDateMonthFromLong(lastDate),
                subtitle = if (type == TypesForAssignmentTest.TEST) "Scheduled test date" else "Submission deadline",
                icon = Icons.Default.DateRange,
                onClick = onCalenderClicked
            )
        }

        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Text("ATTACHMENTS", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                UploadButton("Camera",isUploading,isUploading && source== activeSource.CAMERA,
                    progress,isSuccess && source== activeSource.CAMERA,
                    onClick = {
                        cameraLauncher.launch(fileForCameraURI)
                        source = activeSource.CAMERA
                    },R.drawable.photo_camera_24px)

                UploadButton("Photos",isUploading,isUploading && source == activeSource.PHOTOS,
                    progress,isSuccess && source == activeSource.PHOTOS,
                    onClick = {
                        filePickerLauncher.launch("image/*")
                        source = activeSource.PHOTOS

                    },R.drawable.imagesmode_24px)
                UploadButton("Files",isUploading,isUploading && source== activeSource.FILES,
                    progress,isSuccess && source== activeSource.FILES,
                    onClick = {
                        filePickerLauncher.launch("application/*")
                        source = activeSource.FILES

                    },R.drawable.attach_file_24px)
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            MBSSecondaryButton(text = "Back", onClick = onBackClicked, modifier = Modifier.weight(1f))
            MBSPrimaryButton(
                text = "Next",
                onClick = {
                    val hasText = question.trim().isNotEmpty()
                    if (!hasText && !isSuccess){
                        Toast.makeText(context,"Please provide details or an attachment", Toast.LENGTH_LONG).show()
                    } else if(isUploading){
                        Toast.makeText(context,"Please wait for upload to complete", Toast.LENGTH_LONG).show()
                    } else {
                        onNextClicked()
                    }
                },
                modifier = Modifier.weight(1f),
                enabled = !isUploading
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReviewTestDetail(
    subjectName: String,
    subjectCode: String,
    syllabus: String,
    testDate: Long,
    hasAttachment: Boolean,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = "Review test details",
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
                    Text(text = subjectName, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                    Text(text = subjectCode, fontSize = 13.sp, color = Color(0xFF475569))
                }
                if (syllabus.isNotBlank()) {
                    Column {
                        Text("SYLLABUS", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B))
                        Text(text = syllabus, fontSize = 14.sp, color = Color(0xFF1E293B))
                    }
                }
                Column {
                    Text("TEST DATE", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B))
                    Text(text = DateTimeUtil.getDateMonthFromLong(testDate), fontSize = 14.sp, color = Color(0xFF1E293B))
                }
                if (hasAttachment) {
                    Surface(shape = RoundedCornerShape(8.dp), color = Color(0xFFDCFCE7)) {
                        Text(
                            "✓ Attachment included",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF16A34A)
                        )
                    }
                }
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            MBSSecondaryButton(text = "Edit", onClick = onCancel, modifier = Modifier.weight(1f))
            MBSPrimaryButton(text = "Confirm", onClick = onConfirm, modifier = Modifier.weight(1f))
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ReviewAssignmentDetail(
    subjectName: String,
    subjectCode: String,
    question: String,
    lastDate: Long,
    hasAttachment: Boolean,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        Text(
            text = "Review assignment details",
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
                    Text(text = subjectName, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color(0xFF1E293B))
                    Text(text = subjectCode, fontSize = 13.sp, color = Color(0xFF475569))
                }
                if (question.isNotBlank()) {
                    Column {
                        Text("QUESTION", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B))
                        Text(text = question, fontSize = 14.sp, color = Color(0xFF1E293B))
                    }
                }
                Column {
                    Text("DUE DATE", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = Color(0xFF64748B))
                    Text(text = DateTimeUtil.getDateMonthFromLong(lastDate), fontSize = 14.sp, color = Color(0xFF1E293B))
                }
                if (hasAttachment) {
                    Surface(shape = RoundedCornerShape(8.dp), color = Color(0xFFDCFCE7)) {
                        Text(
                            "✓ Attachment included",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF16A34A)
                        )
                    }
                }
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            MBSSecondaryButton(text = "Edit", onClick = onCancel, modifier = Modifier.weight(1f))
            MBSPrimaryButton(text = "Confirm", onClick = onConfirm, modifier = Modifier.weight(1f))
        }
    }
}
