package com.anshu.collegemate.Data.Model.AssignmentTest

import androidx.credentials.exceptions.domerrors.DataCloneError
import com.google.android.gms.common.util.DataUtils
import com.google.type.DateTime

data class AssignmentCard(
    var assignmentId: String ="",        // document id reference

    val subjectName: String ="",
    val subjectCode: String ="",

    val createdBy: String ="",
    val createdAt: Long =0L,

    val lastDateToSubmit:Long= System.currentTimeMillis().plus(24*60*60*1000),

    val questionText: String ="",

    val questionImageUrl: String ="",
    val questionFileUrl: String ="",
    // Change expiryAt from Long to Date
    val expiryDate: java.util.Date? = null // Add this for TTL
)
