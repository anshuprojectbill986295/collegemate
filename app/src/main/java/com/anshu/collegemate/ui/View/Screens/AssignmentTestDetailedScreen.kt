package com.anshu.collegemate.ui.View.Screens

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.anshu.collegemate.ui.ViewModel.AssignmentTestVM

@Composable

fun AssignmentTestDetailedScreen(id:String,type:String,
  assTestVm: AssignmentTestVM = AssignmentTestVM()){
    val context = LocalContext.current
    when(type){
        "TEST"->{
            val testItem by assTestVm.testByID.collectAsState()
            LaunchedEffect(Unit) {
                assTestVm.getTestByID(id)
            }

            Column(Modifier.fillMaxSize().padding(25.dp), horizontalAlignment = Alignment.CenterHorizontally
                , verticalArrangement = Arrangement.Center) {
                Text("Test")
                Text("Syllabus-->"+testItem.test.syllabus)
                if (testItem.test.syllabusImageUrl.isNotEmpty()){
                    Button(onClick = {
                        val intent= Intent(
                            Intent.ACTION_VIEW,
                            testItem.test.syllabusPdfUrl.toUri()
                        )
                        context.startActivity(intent)

                    }) {
                        Text(text = "Question Image")
                    }
                }
                if (testItem.test.syllabusPdfUrl.isNotEmpty()){
                    Button(onClick = {
                        val intent= Intent(
                            Intent.ACTION_VIEW,
                            testItem.test.syllabusPdfUrl.toUri()
                        )
                        context.startActivity(intent)

                    }) {
                        Text(text = "Question Pdf")
                    }
                }

            }

        }
        "ASSIGNMENT"->{
            val assItem by assTestVm.assByID.collectAsState()
            LaunchedEffect(Unit) {
                assTestVm.getAssByID(id)
            }

            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally
                , verticalArrangement = Arrangement.Center) {
                Text("Assignment")
                Text("Syllabus-->"+assItem.assignment.questionText)
                if (assItem.assignment.questionImageUrl.isNotEmpty()){
                    Button(onClick = {
                        val intent= Intent(
                            Intent.ACTION_VIEW,
                            assItem.assignment.questionImageUrl.toUri()
                        )
                        context.startActivity(intent)

                    }) {
                        Text(text = "Question Image")
                    }
                }
                if (assItem.assignment.questionFileUrl.isNotEmpty()){
                    Button(onClick = {
                        val intent= Intent(
                            Intent.ACTION_VIEW,
                            assItem.assignment.questionFileUrl.toUri()
                        )
                        context.startActivity(intent)

                    }) {
                        Text(text = "Question Pdf")
                    }
                }

            }
        }
    }
}