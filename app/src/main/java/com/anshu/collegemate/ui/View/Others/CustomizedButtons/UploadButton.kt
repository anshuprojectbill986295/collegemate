package com.anshu.collegemate.ui.View.Others.CustomizedButtons

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class activeSource{
    NONE,CAMERA,PHOTOS,FILES
}

@Composable
fun UploadButton(
    buttonName:String,
    isUploading: Boolean,
    isUploadingByClickingThisButton:Boolean,
    progress: Float,
    isSuccessByClickingThisButton: Boolean,
    onClick:()-> Unit,
    @DrawableRes id: Int
){
    Surface(
        modifier = Modifier
            .width(100.dp)
            .height(80.dp),
        shape = RoundedCornerShape(16.dp),
        color = if (isSuccessByClickingThisButton) Color(0xFFDCFCE7) else Color(0xFFF1F5F9),
        onClick = { if (!isUploading) onClick() },
        enabled = !isUploading
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(8.dp)
        ) {
            if (isUploadingByClickingThisButton) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = Color(0xFF6366F1),
                    strokeWidth = 2.dp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "${progress.toInt()}%",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF6366F1)
                )
            } else if (isSuccessByClickingThisButton) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = null,
                    tint = Color(0xFF16A34A),
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "Added",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF16A34A)
                )
            } else {
                Icon(
                    painter = painterResource(id),
                    contentDescription = null,
                    tint = if (isUploading) Color.LightGray else Color(0xFF475569),
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = buttonName,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (isUploading) Color.LightGray else Color(0xFF475569)
                )
            }
        }
    }
}
