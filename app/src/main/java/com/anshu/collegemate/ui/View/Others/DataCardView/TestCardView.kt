package com.anshu.collegemate.ui.View.Others.DataCardView



import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anshu.collegemate.R
import com.anshu.collegemate.ui.theme.CardColorsScheme

@Composable
fun TestCardView(){
    val cardCS = CardColorsScheme.BLUETHEME
    Card(Modifier.fillMaxWidth(), shape = RoundedCornerShape(25.dp),
        colors = CardDefaults.cardColors(containerColor = Color(cardCS.cardBackgroundColor))) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.clip(RoundedCornerShape(26.dp))
                    .background(color = Color(cardCS.assignmentWordContainerColor))){
                    Text(text = "TEST ", fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 12.dp,end=12.dp,top=5.dp, bottom = 5.dp)
                        , color = Color(cardCS.assignmentWordContentColor))}
                Icon(painter = painterResource(R.drawable.more_vert_24px)
                    ,contentDescription = null)

            }
            Spacer(Modifier.height(10.dp))
            Text(text = "Operating Systems", fontWeight = FontWeight.ExtraBold, fontSize = 20.sp)
            Text(text = "CS402", color = Color(cardCS.instructorColor))
            Spacer(Modifier.height(9.dp))
            Row(verticalAlignment =
                Alignment.CenterVertically, modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.clip(RoundedCornerShape(26.dp))
                    .background(color = Color(cardCS.assignmentWordContainerColor))
                ){
                    Row(Modifier
                        .padding(top=5.dp,bottom=5.dp,start=8.dp,end=5.dp ), verticalAlignment =
                        Alignment.CenterVertically){
                        Icon(painter = painterResource(R.drawable.docs_24px),
                        contentDescription = null, modifier = Modifier.height(15.dp))
                        Spacer(Modifier.width(5.dp))
                    Text(text = "Class Test ",
                        modifier = Modifier
                        , color = Color(cardCS.assignmentWordContentColor))}}
                Spacer(Modifier.width(12.dp))
                Box(modifier = Modifier.clip(RoundedCornerShape(26.dp))
                    .background(color = Color(cardCS.assignmentWordContainerColor)),
                    contentAlignment = Alignment.Center){

                    Text(text = "Marks: 10 ",
                        modifier = Modifier.padding(start = 12.dp,end=12.dp,top=5.dp, bottom = 5.dp)
                        , color = Color(cardCS.assignmentWordContentColor))}
            }
            Spacer(Modifier.height(12.dp))
            //TODO Logic if not attachment then say no and if yes then show
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(R.drawable.calendar_today_24px),
                    contentDescription = null, tint = Color(cardCS.instructorColor))
                Spacer(Modifier.width(6.dp))
                Text(text = "28 March", color = Color(cardCS.instructorColor))
            }
            Spacer(Modifier.height(10.dp))
            //TODO Logic waht to show depend on logic
            Text(text = "Process scheduling, deadlocks, memory management " +
                    "including paging and segmentation.", maxLines = 2, lineHeight = 25.sp
                ,color = Color(cardCS.lessFocusElementColor)
                ,modifier = Modifier.alpha(0.8f)
            )
            Spacer(Modifier.height(15.dp))
            //TODO Logic below is dynacmic but for now static
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "By Admin . 18 March", color = Color(cardCS.lessFocusElementColor)
                    ,modifier = Modifier.alpha(0.8f))
                Button(onClick = {}, colors = ButtonDefaults.buttonColors(
                    containerColor = Color(cardCS.viewDetailsButton)
                )) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "View Details")
                        Spacer(Modifier.width(5.dp))
                        Icon(painter = painterResource(R.drawable.
                        arrow_forward_24px),contentDescription = null)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TestPreview(){
    TestCardView()
}