package com.anshu.collegemate.ui.View.Others.CustomizedButtons

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.anshu.collegemate.Data.Model.AssignmentTest.UploadResult
import com.anshu.collegemate.R

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
    Button(onClick = {onClick()
    }, enabled = !isUploading) {
        if (isUploadingByClickingThisButton){
            CircularProgressIndicator(color = Color.Red)
        }
        else if (isSuccessByClickingThisButton) {
            Text("✅")
        }
        else{
            Log.d("newIsUploading","${isUploading}   ${isUploadingByClickingThisButton}")
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    painter = painterResource(id),
                    contentDescription = null
                )
                Text(buttonName)
            }
        }


    }
}