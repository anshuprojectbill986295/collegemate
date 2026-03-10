package com.anshu.collegemate.ui.View.Others.MBS

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.anshu.collegemate.Data.Model.AssignmentTest.AssignmentCard
import com.anshu.collegemate.Data.Model.AssignmentTest.TestCard
import com.anshu.collegemate.Data.Model.HomeScreen.RoutineSeed
import com.anshu.collegemate.R
import com.anshu.collegemate.ui.ViewModel.AssignmentTestVM
import com.anshu.collegemate.ui.ViewModel.UserViewModel
import java.time.Instant
import java.time.ZoneId
import java.util.concurrent.TimeUnit
enum class steps{
    TYPE,SUBJECT,DETAILS,REVIEW
}
enum class type{
    TEST,ASSIGNMENT
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignmentTestMBS(assTesVM: AssignmentTestVM, onDismiss:()->Unit) {
    var selectedButton by remember { mutableStateOf("NONE") }
    var subjectName by remember { mutableStateOf("") }
    var subjectCode by remember { mutableStateOf("") }
    var questionText by remember { mutableStateOf("Either u can add text/pdf/image of the question. Write for question here.") }
    var showDatePicker by remember { mutableStateOf(false) }
    var lastDateOfAssignment by remember{mutableStateOf(0L)}
    val datePickerState = rememberDatePickerState()
    var createdAt by remember { mutableStateOf(0L) }
    var createdBy by remember { mutableStateOf("") }
    var testDate by remember {mutableStateOf(0L)}
    var testSyllabus by remember { mutableStateOf("Enter Topics or Syllabus for the this Test(Optional)") }
    val context =   LocalContext.current

    ModalBottomSheet(onDismissRequest = { onDismiss() }) {


        Column(modifier = Modifier.padding(10.dp)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceAround) {
                Button(onClick = {
                    selectedButton = "TEST"
                }) { Text("Test") }
                Button(onClick = {
                    selectedButton = "ASSIGNMENT"
                }) { Text("ASSIGNMENT") }

            }
            when (selectedButton) {
                "TEST" -> {
                    Text("Please Choose Subject!")
                    LazyColumn() {
                        items(RoutineSeed.setOfDistinctClasses) {
                            Button(onClick = {
                                subjectCode = it.subjectCode
                                subjectName = it.name
                            }) {
                                Text(it.name)
                            }
                        }
                    }
                    Row(){Text("When is the Test?")
                        Button(onClick = {
                            showDatePicker=true}) {
                            Text("📅")
                        }
                    }
                    OutlinedTextField(value = testSyllabus, onValueChange = {testSyllabus=it},
                        label = {Text("Syllabus/Topics")})

                    //TODO expiry At plus day one to testDate use it directly while creating object of Test
                    Row() {
                        Button(onClick = {
                            createdBy = UserViewModel.userP.value?.name?:""
                            createdAt = System.currentTimeMillis()
                            val tc = TestCard(
                                subjectName = subjectName,
                                subjectCode = subjectCode,
                                createdAt = createdAt,
                                createdBy = createdBy,
                                testDate = testDate,
                                expiryAt = testDate + TimeUnit.DAYS.toMillis(1),
                                syllabus = testSyllabus
                            )
                            assTesVM.addTest(tc)
                            Toast.makeText(context,"Test successfully added",Toast.LENGTH_LONG).show()
                            onDismiss()
                        }) {
                            Text("Confirm")
                        }
                        Button(onClick = {onDismiss()}) {
                            Text("Cancel")
                        }

                    }


                }

                "ASSIGNMENT" -> {
                    Text("Please Choose Subject!")
                    LazyColumn(horizontalAlignment = Alignment.End) {
                        items(RoutineSeed.setOfDistinctClasses) {
                            Button(onClick = {
                                subjectCode = it.subjectCode
                                subjectName = it.name
                            }) {
                                Text(it.name)
                            }
                        }
                    }
                    OutlinedTextField(
                        onValueChange = { questionText = it }, value = questionText,
                        label = { Text("Assignment Question") })
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
                    Row(){Text("Choose Last Date of submission")
                        Button(onClick = {showDatePicker=true}) {
                            Text("📅")
                        }
                    }
                    Row() {
                        Button(onClick = {
                            createdBy = UserViewModel.userP.value?.name?:""
                            createdAt = System.currentTimeMillis()
                            val ass = AssignmentCard(
                                subjectName = subjectName,
                                subjectCode = subjectCode,
                                questionText = questionText,
                                createdBy = createdBy,
                                createdAt = createdAt,
                                expiryAt = lastDateOfAssignment + TimeUnit.DAYS.toMillis(1),
                                lastDateToSubmit = lastDateOfAssignment
                            )
                            assTesVM.addAssignment(ass)
                            Toast.makeText(context,"Assignment Successfully Added!",Toast.LENGTH_LONG).show()
                            onDismiss()
                        }) {
                            Text("Confirm")
                        }
                        Button(onClick = {onDismiss()}) {
                            Text("Cancel")
                        }

                    }

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
                    if (selectedButton=="ASSIGNMENT")lastDateOfAssignment = endOfDayMillis
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