package com.anshu.collegemate.ui.View.Screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.anshu.collegemate.Data.Model.AssignmentTest.AssignmentCard
import com.anshu.collegemate.ui.View.Others.DataCardView.AssignmentCardView
import com.anshu.collegemate.ui.View.Others.DataCardView.TestCardView
import com.anshu.collegemate.ui.ViewModel.AssignmentTestVM

@Composable
fun AssignmentTestScreen(
    //assTestVm: AssignmentTestVM = AssignmentTestVM()
){

//    LaunchedEffect(Unit) {
//        assTestVm.fetchAllTest()
//        assTestVm.fetchAssignment()
//    }
//
//    val testList by assTestVm.testList.collectAsState()
//    val assList by assTestVm.assList.collectAsState()
//    Column(Modifier.fillMaxSize()) {
//        Text("I am vis  length of asslist   ${testList.size}")
//        LazyColumn() {
//            items(assList){
//                Text("I am visible   length of asslist   ${testList.size}")
//                Text(text = it.subjectName)
//                Text(it.questionText)
//                Text(it.subjectName)
//            }
//        }
//    }
    Column(modifier = Modifier.fillMaxSize().padding(start = 20.dp,end=20.dp)) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth())
    {
        Button(onClick = {}, colors = ButtonDefaults.buttonColors(), border = BorderStroke(width = 2.dp, color = Color.LightGray))
        {
            Text("All", color = Color.White)
        }
        Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor =
            Color.Transparent), border = BorderStroke(width = 2.dp, color = Color.LightGray))
        {
            Text("Assignments", color = Color.Blue)
        }
        Button(onClick = {}, colors = ButtonDefaults.buttonColors(containerColor =
            Color.Transparent), border = BorderStroke(width = 2.dp, color = Color.LightGray))
        {
            Text("Tests", color = Color.Blue)
        }
    }
    //TODO My Pattern,,   Today stick and Lazy column for todays entities,
    // but when my today finish Tomorrow stick and then lazy column for Tomorrow and so on.....
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Text("Today", fontWeight = FontWeight.Bold, color = Color(0xff6C3CE0))
        Spacer(Modifier.width(8.dp))
        HorizontalDivider()
    }
        Spacer(Modifier.height(12.dp))
        AssignmentCardView()
        Spacer(Modifier.height(12.dp))
        TestCardView()

}
}
@Preview(showBackground = true)
@Composable
fun ATPreview(){
    AssignmentTestScreen()
}