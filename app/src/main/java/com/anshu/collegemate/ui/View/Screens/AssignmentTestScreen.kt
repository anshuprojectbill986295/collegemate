package com.anshu.collegemate.ui.View.Screens

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.collection.emptyLongSet
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anshu.collegemate.Data.Model.AssignmentTest.AssignmentCard
import com.anshu.collegemate.Data.Model.AssignmentTest.TimelineItem
import com.anshu.collegemate.ui.View.Others.DataCardView.AssignmentCardView
import com.anshu.collegemate.ui.View.Others.DataCardView.TestCardView
import com.anshu.collegemate.ui.ViewModel.AssignmentTestVM
import java.sql.Time

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AssignmentTestScreen(
    assTestVm: AssignmentTestVM = AssignmentTestVM(),
    onViewClicked:(id:String,type:String)->Unit
){
    var selectedButton  by remember { mutableStateOf(0) }


    LaunchedEffect(selectedButton) {
        assTestVm.fetchAllTest()
        assTestVm.fetchAssignment()
    }

    val testMap by assTestVm.testMap.collectAsState()
    val assMap by assTestVm.assMap.collectAsState()
    val map by assTestVm.map.collectAsState()

    Column(modifier = Modifier.fillMaxSize().padding(start = 14.dp,end=14.dp)) {
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth())
    {
        Button(onClick = {
            selectedButton=0;
        }
            , colors =
            if (selectedButton==0) ButtonDefaults.buttonColors()
                else ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            , border = BorderStroke(width = 2.dp, color = Color.LightGray))
        {
            Text("All",
                color = if (selectedButton==0)Color.White
                        else Color.Black)
        }
        Button(onClick = {
            selectedButton=1;
        }, colors =
            if (selectedButton==1) ButtonDefaults.buttonColors()
            else ButtonDefaults.buttonColors(containerColor = Color.Transparent)
                    ,border = BorderStroke(width = 2.dp, color = Color.LightGray))
        {
            Text("Assignments",
                color = if (selectedButton==1)Color.White
                else Color.Black)
        }
        Button(onClick = {
            selectedButton=2;
        }, colors =
            if (selectedButton==2) ButtonDefaults.buttonColors()
            else ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            , border = BorderStroke(width = 2.dp, color = Color.LightGray))
        {
            Text("Tests",
                color = if (selectedButton==2)Color.White
                else Color.Black)
        }
    }

        LazyColumn() {
            when(selectedButton){
                0->{map.forEach { (header,list)->
                    stickyHeader{
                        Row(Modifier.fillMaxWidth().padding(top = 5.dp,bottom=5.dp), verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = header, fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,color = Color(0xff6C3CE0))
                            Spacer(Modifier.width(8.dp))
                            HorizontalDivider()
                        }
                        //Text("lksdkjfdsfnh")
                    }

                    items(list){

                            item->
                        //("lksdkjfdsfnh")
                        when(item){
                            is TimelineItem.TestItem-> TestCardView(item,onViewClicked)
                            is TimelineItem.AssignmentItem-> AssignmentCardView(item,onViewClicked)
                        }
                    }
                }}
                1->{
                    assMap.forEach { (header,assList)->
                        stickyHeader{
                            Row(Modifier.fillMaxWidth().padding(top = 5.dp,bottom=5.dp), verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = header, fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp,color = Color(0xff6C3CE0))
                                Spacer(Modifier.width(8.dp))
                                HorizontalDivider()
                            }
                            //Text("lksdkjfdsfnh")
                        }

                        items(assList){

                                item->
                            //("lksdkjfdsfnh")
                            AssignmentCardView(item,onViewClicked)
                        }
                    }
                }
                2->{testMap.forEach { (header,testList)->
                    stickyHeader{
                        Row(Modifier.fillMaxWidth().padding(top = 5.dp,bottom=5.dp), verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = header, fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,color = Color(0xff6C3CE0))
                            Spacer(Modifier.width(8.dp))
                            HorizontalDivider()
                        }
                        //Text("lksdkjfdsfnh")
                    }

                    items(testList){

                            item->
                        //("lksdkjfdsfnh")
                        TestCardView(item,onViewClicked)
                    }
                }}

            }

        }


}
}
//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true)
//@Composable
//fun ATPreview(){
//    AssignmentTestScreen()
//}