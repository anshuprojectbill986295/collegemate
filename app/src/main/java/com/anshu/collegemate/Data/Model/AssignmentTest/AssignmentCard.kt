package com.anshu.collegemate.Data.Model.AssignmentTest

import androidx.credentials.exceptions.domerrors.DataCloneError
import com.google.android.gms.common.util.DataUtils
import com.google.type.DateTime

data class AssignmentCard(
    var assignmentId: String ="",        // document id reference

    val subjectName: String ="",
    val subjectCode: String ="",

    val questionText: String ="",        // null if only image/pdf
    val questionImageUrl: String ="",    // null if no image
    val questionFileUrl: String ="",      // null if no pdf

    val createdBy: String ="",            // CR / admin uid
    val createdAt: Long =0L,              // timestamp

    val lastDateToSubmit:Long= System.currentTimeMillis().plus(24*60*60*1000),
    val expiryAt: Long  =0L               // expiry timestamp
)
