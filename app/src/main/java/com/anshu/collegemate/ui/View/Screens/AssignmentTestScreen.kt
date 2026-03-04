package com.anshu.collegemate.ui.View.Screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.anshu.collegemate.ui.ViewModel.AssignmentTestVM

@Composable
fun AssignmentTestScreen(assTestVm: AssignmentTestVM = AssignmentTestVM()){

    LaunchedEffect(Unit) {
        assTestVm.fetchAllTest()
        assTestVm.fetchAssignment()
    }

    val testList by assTestVm.testList.collectAsState()
    val assList by assTestVm.assList.collectAsState()
    Column() {
        Text("I am visible   length of asslist   ${testList.size}")
        LazyColumn() {
            items(assList){
                // Text("I am visible   length of asslist   ${testList.size}")
                Text(text = it.subjectName)
                Text(it.questionText)
                Text(it.subjectName)
            }
        }
    }
}