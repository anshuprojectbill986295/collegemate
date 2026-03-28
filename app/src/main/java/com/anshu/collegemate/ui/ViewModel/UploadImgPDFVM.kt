package com.anshu.collegemate.ui.ViewModel

import android.R
import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.util.Logger
import com.anshu.collegemate.Data.Injections.FireStorageInjection
import com.anshu.collegemate.Data.Model.AssignmentTest.UploadResult
import com.anshu.collegemate.ui.View.Others.CustomizedButtons.activeSource
import com.anshu.collegemate.ui.View.Screens.LoginScreen
import com.google.android.gms.common.data.BitmapTeleporter
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.serialization.Contextual
import java.io.File
import java.io.FileOutputStream

class UploadImgPDFVM(): ViewModel() {
    private val _result = MutableStateFlow<UploadResult?>(null)
    val result: StateFlow<UploadResult?> = _result
    var progress= 0.0
    private val _uploadButtonClicked = MutableStateFlow<Boolean>(false)
    //val uploadButtonClicked: StateFlow<Boolean> = _uploadButtonClicked
    fun upload(uri: Uri,type: activeSource){
      //  _uploadButtonClicked.value = true
        val fileRef = FireStorageInjection.getStorageRef().child("files/${System.currentTimeMillis()}")

        _result.value= UploadResult.Uploading(0.0f)
        Log.d("Set to Uploading","${_result.value.toString()}")

            val uploadTask = fileRef.putFile(uri)

        uploadTask.addOnProgressListener { snapshot ->
            Log.d("Progress","")

            progress = (100.0 * snapshot.bytesTransferred/snapshot.totalByteCount)
                _result.value = UploadResult.Uploading(progress.toFloat())
            }
        uploadTask.addOnFailureListener {
            error->
            Log.d("ErrorUpload","${error.toString()}")
            _result.value = UploadResult.Error(error.toString())
            _uploadButtonClicked.value = false
        }
        uploadTask.addOnSuccessListener {
            uploadTask->
            Log.d("SuccessUpload","")
            fileRef.downloadUrl.addOnSuccessListener {
                downloadURL->
                val downloadLink = downloadURL.toString()
                _result.value = UploadResult.Success(downloadLink,type)
            }

            //_uploadButtonClicked.value = false
        }



    }
    fun bitmapToFile(context:Context,bitmap: Bitmap): File{
        val file = File(context.cacheDir,"temp_file_${System.currentTimeMillis()}")
        val outputStream = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG,90,outputStream)
        outputStream.close()
        return file
    }
}

