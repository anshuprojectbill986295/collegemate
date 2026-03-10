package com.anshu.collegemate.ui.View.Others.MBS

import android.net.wifi.p2p.WifiP2pGroup
import android.os.Build
import android.sax.RootElement
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.traceEventStart
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.core.content.contentValuesOf
import com.anshu.collegemate.Data.Model.AssignmentTest.AssignmentCard
import com.anshu.collegemate.Data.Model.AssignmentTest.TestCard
import com.anshu.collegemate.Data.Model.HomeScreen.RoutineSeed
import com.anshu.collegemate.Data.Model.HomeScreen.ScheduleCardData
import com.anshu.collegemate.R
import com.anshu.collegemate.Utils.DateTimeUtil
import com.anshu.collegemate.ui.ViewModel.AssignmentTestVM
import com.anshu.collegemate.ui.ViewModel.UserViewModel
import java.time.Instant
import java.time.ZoneId
import java.util.concurrent.TimeUnit

enum class steps{
    TYPE,SUBJECT,DETAILS,REVIEW
}
enum class types{
    TEST,ASSIGNMENT
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignmentTestMBS(assTesVM: AssignmentTestVM, onDismiss:()->Unit) {
    var subjectName by remember { mutableStateOf("") }
    var subjectCode by remember { mutableStateOf("") }
    //contentText works both for test Assignment in TextField
    var contentText by remember { mutableStateOf("") }
    var showDatePicker by remember { mutableStateOf(false) }
    var lastDateOfAssignment by remember{mutableStateOf(0L)}
    val datePickerState = rememberDatePickerState()
    var createdAt by remember { mutableStateOf(0L) }
    var createdBy by remember { mutableStateOf("") }
    var testDate by remember {mutableStateOf(0L)}
    //var testSyllabus by remember { mutableStateOf("Enter Topics or Syllabus for the this Test(Optional)") }
    val context =   LocalContext.current
    var currentStep by remember { mutableStateOf(steps.TYPE) }
    var type by remember { mutableStateOf<types?>(null) }

    ModalBottomSheet(onDismissRequest = { onDismiss() }) {

        when(currentStep){
            steps.TYPE->{
                TypeStep {
                    type = it
                    currentStep = steps.SUBJECT
                }
            }
            steps.SUBJECT->{
                SubjectStep(RoutineSeed.setOfDistinctClasses) {
                    sc->
                    subjectName = sc.name
                    subjectCode = sc.subjectCode
                    currentStep = steps.DETAILS
                }
            }
            steps.DETAILS->{
                when(type){
                    types.TEST -> {
                        TestDetailsStep(type!!,subjectName,contentText,testDate,
                            onSyllabusChange = {contentText=it}, onCalenderClicked = {
                                showDatePicker = true}, onNextClicked = {currentStep = steps.REVIEW})
                    }
                    types.ASSIGNMENT -> {AssignmentDetailsStep(
                        type!!,subjectName,contentText,lastDateOfAssignment,
                        onQuestionChange = {contentText = it}, onCalenderClicked = {
                            showDatePicker = true }, onNextClicked = {
                            currentStep = steps.REVIEW
                        })}
                    else -> {}
                }
            }
            steps.REVIEW->{
                when(type){
                    types.TEST->{
                        ReviewTestDetail(subjectName,subjectCode,contentText,testDate,
                            onConfirm = {
                                createdBy = UserViewModel.userP.value?.name?:""
                            createdAt = System.currentTimeMillis()
                            val tc = TestCard(
                                subjectName = subjectName,
                                subjectCode = subjectCode,
                                createdAt = createdAt,
                                createdBy = createdBy,
                                testDate = testDate,
                                expiryAt = testDate + TimeUnit.DAYS.toMillis(1),
                                syllabus = contentText
                            )
                            assTesVM.addTest(tc)
                            Toast.makeText(context,"Test successfully added",Toast.LENGTH_LONG).show()
                            onDismiss()
                            },
                            onCancel = {
                                onDismiss()
                            })
                    }
                    types.ASSIGNMENT->{
                        ReviewAssignmentDetail(subjectName,subjectCode,contentText,lastDateOfAssignment,
                            onConfirm =
                                {
                            createdBy = UserViewModel.userP.value?.name?:""
                            createdAt = System.currentTimeMillis()
                            val ass = AssignmentCard(
                                subjectName = subjectName,
                                subjectCode = subjectCode,
                                questionText = contentText,
                                createdBy = createdBy,
                                createdAt = createdAt,
                                expiryAt = lastDateOfAssignment + TimeUnit.DAYS.toMillis(1),
                                lastDateToSubmit = lastDateOfAssignment
                            )
                            assTesVM.addAssignment(ass)
                            Toast.makeText(context,"Assignment Successfully Added!",Toast.LENGTH_LONG).show()
                            onDismiss()

                            },
                            onCancel = {
                                onDismiss()
                            })
                    }
                    else -> {}
                }

            }
        }



   }
    if (showDatePicker){
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                Button(onClick = {
                    val selectedMillis = datePickerState.selectedDateMillis ?: 0L

                    val endOfDayMillis = Instant.ofEpochMilli(selectedMillis)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
                        .atTime(23, 59, 59)
                        .atZone(ZoneId.systemDefault())
                        .toInstant()
                        .toEpochMilli()
                    if (type.toString()=="ASSIGNMENT")lastDateOfAssignment = endOfDayMillis
                    else {
                        testDate=endOfDayMillis
                    }
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
@Composable
fun TypeStep(
    onTypeSelected: (types) -> Unit
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

        Text("What do you want to add?")

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {

            Button(onClick = {
                onTypeSelected(types.TEST)
            }) {
                Text("Test")
            }

            Button(onClick = {
                onTypeSelected(types.ASSIGNMENT)
            }) {
                Text("Assignment")
            }

        }
    }
}
@Composable
fun SubjectStep(
    subjects: List<ScheduleCardData>,
    onSubjectSelect:(sc: ScheduleCardData)->Unit
) {
    Column() {
        Text("Choose Subject")
    LazyColumn() {

        items(subjects) { subject ->
            Row(
                modifier = Modifier.fillMaxWidth()
                .padding(25.dp)
                .clickable(onClick = { onSubjectSelect(subject) })
            )
            {
                Text(subject.name)
            }
        }
    }
}
}
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AssignmentDetailsStep(
    type: types,
    subjectName: String,
    question:String,
    lastDate:Long,
    onQuestionChange:(newValue:String)->Unit,
    onCalenderClicked:()->Unit,
    onNextClicked:()-> Unit
){
    Column() {
        Text(type.toString()+"."+subjectName)

        OutlinedTextField(value = question, onValueChange = {onQuestionChange(it)}
            , label ={Text("Question")})
        Row(modifier = Modifier.fillMaxWidth()) {
                        IconButton(onClick = {}) {
                            Icon(
                                painter = painterResource(R.drawable.image_24px),
                                contentDescription = null
                            )
                        }
                        IconButton(onClick = {}) {
                            Icon(
                                painter = painterResource(R.drawable.picture_as_pdf_24px),
                                contentDescription = null
                            )
                        }

                    }
        Row() {
            Text("Last Date")
            Button(onClick = {
                onCalenderClicked()}) {
                if(lastDate==0L){
                    Text("📅 Select Date")
                }
                else{
                    Text("📅 ${DateTimeUtil.getDateMonthFromLong(lastDate)}")
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
fun TestDetailsStep(
    type: types,
    subjectName: String,
    syllabus:String,
    testDate:Long,
    onSyllabusChange:(newValue: String)->Unit,
    onCalenderClicked:()-> Unit,
    onNextClicked:()->Unit
){
    Column() {
        Text(type.toString()+"."+subjectName)

        OutlinedTextField(value = syllabus, onValueChange = {onSyllabusChange(it)}
            , label ={Text("Question")})
        Row(modifier = Modifier.fillMaxWidth()) {
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.image_24px),
                    contentDescription = null
                )
            }
            IconButton(onClick = {}) {
                Icon(
                    painter = painterResource(R.drawable.picture_as_pdf_24px),
                    contentDescription = null
                )
            }

        }
        Row() {
            Text("Last Date")
            Button(onClick = {
                onCalenderClicked()}) {
                if(testDate ==0L){
                    Text("📅 Select Date")
                }
                else{
                    Text("📅 ${DateTimeUtil.getDateMonthFromLong(testDate)}")
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
fun ReviewTestDetail(
    subjectName: String,
    subjectCode: String,
    syllabus: String,
    testDate:Long,
    onConfirm:()->Unit,
    onCancel:()-> Unit
){
            Column(Modifier.padding(10.dp)) {
                Text("TEST . $subjectName$subjectCode")
                Text(syllabus)
                Text("Attachment Included")
                Text(DateTimeUtil.getDateMonthFromLong(testDate))
                Button(onClick = onConfirm) {
                    Text("Confirm")
                }
                Button(onClick = onCancel) {
                    Text("Cancel")
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
    onConfirm:()->Unit,
    onCancel:()-> Unit
){
    Column(Modifier.padding(10.dp)) {
        Text("ASSIGNMENT . "+subjectName+subjectCode)
        Text(question)
        Text("Attachment Included")
        Text(DateTimeUtil.getDateMonthFromLong(lastDate))
        Button(onClick = onConfirm) {
            Text("Confirm")
        }
        Button(onClick = onCancel) {
            Text("Cancel")
        }
    }

}