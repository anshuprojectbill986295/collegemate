package com.anshu.collegemate.ui.View.Others.Bars

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import com.anshu.collegemate.R
import com.anshu.collegemate.ui.ViewModel.UserViewModel
import coil.compose.AsyncImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String, onPicClicked:()-> Unit){
    val userProfile= UserViewModel.userP.collectAsState().value
    var showResourcesDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    if (showResourcesDialog) {
        AlertDialog(
            onDismissRequest = { showResourcesDialog = false },
            confirmButton = {
                TextButton(onClick = { showResourcesDialog = false }) {
                    Text("Close", color = Color(0xff667eea))
                }
            },
            title = {
                Text(
                    text = "CollegeMate Resources",
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    fontFamily = FontFamily.SansSerif
                )
            },
            text = {
                Column {
                    ResourceItem(
                        title = "Full Week Class Routine",
                        subtitle = "View the complete weekly timetable",
                        icon = R.drawable.schedule_24px,
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, "https://drive.google.com/file/d/1qpLVN598TmjeuWZh_wjb0qUc3NDb0sz2/view?usp=sharing".toUri())
                            context.startActivity(intent)
                            showResourcesDialog = false
                        }
                    )
                    ResourceItem(
                        title = "CSE UG Syllabus",
                        subtitle = "View the complete CSE undergraduate syllabus",
                        icon = R.drawable.docs_24px,
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, "https://drive.google.com/file/d/1-2yhpxTSkpn9Bar_e_XzWHyVwGPsrLxU/view?usp=sharing".toUri())
                            context.startActivity(intent)
                            showResourcesDialog = false
                        }
                    )
                    ResourceItem(
                        title = "Academic Calender",
                        subtitle = "View the complete Academic Calender of July-December 2026",
                        icon = R.drawable.docs_24px,
                        onClick = {
                            val intent = Intent(Intent.ACTION_VIEW, "https://drive.google.com/file/d/1KGXOEWuYTPkVStQp0mJ2mpqws_Y01sBC/view?usp=sharing".toUri())
                            context.startActivity(intent)
                            showResourcesDialog = false
                        }
                    )
                }
            }
        )
    }

    Box(modifier = Modifier.clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
    ){
        TopAppBar(title = {
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center){Text(text = title, fontSize = 24.sp, fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.ExtraBold
                , color = Color.White)}
        }
            , colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
            , modifier = Modifier.background(Brush.linearGradient(colors = listOf(Color(0xff667eea),
                Color(0xff764ba2)))
            ), navigationIcon = {
                IconButton(onClick = { showResourcesDialog = true },Modifier.padding(start = 16.dp).size(43.dp)){
                    Icon(painter = painterResource(R.drawable.outline_menu_24)
                        , tint = Color.White, contentDescription = null,
                        modifier = Modifier.clip(RoundedCornerShape(24.dp)).background(color = Color(0x1affffff)).padding(8.dp)
                    )
                }
            }, actions = {
                Box(Modifier.padding(end=16.dp)){
                    IconButton(onClick = {onPicClicked()}, modifier = Modifier.clip(CircleShape)
                        .background(color = Color.Transparent).size(38.dp)){
                      AsyncImage(model = userProfile?.photoURL,contentDescription = null,
                            modifier = Modifier.clip(CircleShape).size(38.dp) )
                    }}

            })
    }

}


@Composable
fun ResourceItem(
    title: String,
    subtitle: String,
    icon: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp, horizontal = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier.size(28.dp),
            tint = Color(0xFF667eea)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.Black
            )
            Text(
                text = subtitle,
                fontSize = 12.sp,
                fontFamily = FontFamily.SansSerif,
                color = Color.Gray
            )
        }
    }
}


//@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun TopBarPreview(){
    TopBar(title ="Notifications  \uD83D\uDD14", onPicClicked = {})
}