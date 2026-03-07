package com.anshu.collegemate.ui.View.Others.DataCardView

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anshu.collegemate.Data.Model.AssignmentTest.AssignmentCard
import com.anshu.collegemate.Data.Model.AssignmentTest.TimelineItem
import com.anshu.collegemate.R
import com.anshu.collegemate.Utils.DateTimeUtil
import com.anshu.collegemate.ui.ViewModel.UserViewModel
import com.anshu.collegemate.ui.theme.CardColorsScheme

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AssignmentCardView(ac: TimelineItem.AssignmentItem){
    val cardCS = CardColorsScheme.ORANGETHEME
    Card(Modifier.fillMaxWidth().padding(bottom = 12.dp,top=5.dp), shape = RoundedCornerShape(25.dp),
        colors = CardDefaults.cardColors(containerColor = Color(cardCS.cardBackgroundColor))) {
        Column(modifier = Modifier.padding(12.dp)) {
            Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                Box(modifier = Modifier.clip(RoundedCornerShape(26.dp))
                    .background(color = Color(cardCS.assignmentWordContainerColor))){
                    Text(text = "Assignment", fontWeight = FontWeight(700),
                        modifier = Modifier.padding(start = 12.dp,end=12.dp,top=5.dp, bottom = 5.dp)
                    , color = Color(cardCS.assignmentWordContentColor))}
                    Icon(painter = painterResource(R.drawable.more_vert_24px)
                        ,contentDescription = null)

            }
            Spacer(Modifier.height(10.dp))
            Text(text = ac.assignment.subjectName, fontWeight = FontWeight.ExtraBold,
                fontSize = 20.sp)
            Text(text = ac.assignment.subjectCode, color = Color(cardCS.instructorColor))
            Spacer(Modifier.height(9.dp))
            Text(text = ac.assignment.questionText, maxLines = 2, lineHeight = 25.sp
                ,color = Color(cardCS.lessFocusElementColor)
                        ,modifier = Modifier.alpha(0.8f)
            )
            Spacer(Modifier.height(10.dp))
            //TODO Logic if not attachment then say no and if yes then show
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(R.drawable.attach_file_24px),
                    contentDescription = null, tint = Color(cardCS.instructorColor))
                Spacer(Modifier.width(6.dp))
                if (ac.assignment.questionImageUrl.isEmpty()){
                    Text(text = "Attachment Not included", color = Color(cardCS.instructorColor))
                }
                else{
                    Text(text = "Attachment included", color = Color(cardCS.instructorColor))
                }
            }
            Spacer(Modifier.height(10.dp))
            //TODO Logic waht to show depend on logic
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(painter = painterResource(R.drawable.schedule_24px),
                    contentDescription = null, tint = Color.Red)
                Spacer(Modifier.width(6.dp))
                Text(text = "Due "+DateTimeUtil.getHeaderLabel(ac.assignment.lastDateToSubmit), fontWeight = FontWeight.Bold, color = Color.Red)
            }
            Spacer(Modifier.height(15.dp))
            //TODO Logic below is dynacmic but for now static
            Row(Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = "~By ${ac.assignment.createdBy.substringBefore(" ")} . ${DateTimeUtil.getHeaderLabel(ac.assignment.createdAt)}", color = Color(cardCS.lessFocusElementColor)
                    ,modifier = Modifier.alpha(0.8f))
                Button(onClick = {}, colors = ButtonDefaults.buttonColors(
                    containerColor = Color(cardCS.viewAssignmentButton)
                )) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(text = "View Assignment")
                        Icon(painter = painterResource(R.drawable.
                        arrow_forward_24px),contentDescription = null)
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun ACVPreview(){
    val ac = AssignmentCard(assignmentId="AU043CZJq6hNErpw7r5L",createdAt=1772636290872,createdBy="Anshu Kumar Gupta"
        ,expiryAt=1772841600000,lastDateToSubmit=1772967600000,questionImageUrl="",questionPdfUrl=""
                ,questionText="Waterfall method and SDLC CYCLE " ,subjectCode="CS-2201"
        ,subjectName="Software Engineering")
    val acTimeline = TimelineItem.AssignmentItem(ac)
    AssignmentCardView(acTimeline)
}
