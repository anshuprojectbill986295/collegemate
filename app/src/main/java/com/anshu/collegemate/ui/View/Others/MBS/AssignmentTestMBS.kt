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
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
    var type by remember { mutableStateOf<TypesForAssignmentTest>(TypesForAssignmentTest.NONE) }

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
                    TypesForAssignmentTest.TEST -> {
                        TestDetailsStep(
                            type,subjectName,contentText,testDate,
                            onSyllabusChange = {contentText=it}, onCalenderClicked = {
                                showDatePicker = true}, onNextClicked = {currentStep = steps.REVIEW})
                    }
                    TypesForAssignmentTest.ASSIGNMENT -> {AssignmentDetailsStep(
                        type,subjectName,contentText,lastDateOfAssignment,
                        onQuestionChange = {contentText = it}, onCalenderClicked = {
                            showDatePicker = true }, onNextClicked = {
                            currentStep = steps.REVIEW
                        })}
                    TypesForAssignmentTest.NONE -> {}
                }
            }
            steps.REVIEW->{
                when(type){
                    TypesForAssignmentTest.TEST->{
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
                    TypesForAssignmentTest.ASSIGNMENT->{
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

                    TypesForAssignmentTest.NONE->{}
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
    onTypeSelected: (TypesForAssignmentTest) -> Unit
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

        Text("What do you want to add?")

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {

            Button(onClick = {
                onTypeSelected(TypesForAssignmentTest.TEST)
            }) {
                Text("Test")
            }

            Button(onClick = {
                onTypeSelected(TypesForAssignmentTest.ASSIGNMENT)
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
    Column(Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
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
    type: TypesForAssignmentTest,
    subjectName: String,
    question:String,
    lastDate:Long,
    onQuestionChange:(newValue:String)->Unit,
    onCalenderClicked:()->Unit,
    onNextClicked:()-> Unit
){
    Column(Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(type.toString()+"."+subjectName)

        OutlinedTextField(value = question, onValueChange = {onQuestionChange(it)}
            , label ={Text("Question")})
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically) {
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
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically) {
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
    type: TypesForAssignmentTest,
    subjectName: String,
    syllabus:String,
    testDate:Long,
    onSyllabusChange:(newValue: String)->Unit,
    onCalenderClicked:()-> Unit,
    onNextClicked:()->Unit
){
    Column(Modifier.padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text(type.toString()+"."+subjectName)

        OutlinedTextField(value = syllabus, onValueChange = {onSyllabusChange(it)}
            , label ={Text("Question")})
        Row(modifier = Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
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
        Row(modifier = Modifier.fillMaxWidth().padding(10.dp), horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically) {
            Text("Last Date")
            Spacer(Modifier.width(10.dp))
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
            Column(Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center) {
                Text("TEST . ${subjectName}  ${subjectCode}")
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
    Column(Modifier.padding(8.dp), horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        Text("ASSIGNMENT . "+subjectName+"  "+subjectCode)
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