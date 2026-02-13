package com.anshu.collegemate.ui.View.Others.Bars

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.anshu.collegemate.R
import com.anshu.collegemate.ui.ViewModel.UserViewModel
import coil.compose.AsyncImage


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(title: String, onPicClicked:()-> Unit){
    val userProfile= UserViewModel.userP.collectAsState().value
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
                IconButton(onClick = {},Modifier.padding(start = 16.dp).size(43.dp)){
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


//@Preview(showBackground = true)
//@Composable
//fun TopBarPreview(){
//    TopBar(title ="Notifications  \uD83D\uDD14")
//}