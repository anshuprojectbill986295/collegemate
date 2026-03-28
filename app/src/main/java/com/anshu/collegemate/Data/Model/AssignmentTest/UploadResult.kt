package com.anshu.collegemate.Data.Model.AssignmentTest

import com.anshu.collegemate.ui.View.Others.CustomizedButtons.activeSource


sealed class UploadResult() {
    data class Uploading(var progress: Float): UploadResult()
    data class Success(val downloadLink :String,var type: activeSource): UploadResult()
    data class Error(val result: String): UploadResult()
}