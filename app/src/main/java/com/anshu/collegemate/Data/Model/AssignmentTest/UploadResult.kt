package com.anshu.collegemate.Data.Model.AssignmentTest

import com.anshu.collegemate.ui.View.Others.CustomizedButtons.activeSource


sealed class UploadResult() {
    // ADD THIS: Represents the default state before any upload starts,
    // or when the state is reset after a successful upload.
    object Idle : UploadResult()
    data class Uploading(var progress: Float): UploadResult()
    data class Success(val downloadLink :String,var type: activeSource): UploadResult()
    data class Error(val result: String): UploadResult()
}