package com.anshu.collegemate.Data.Model.AssignmentTest

data class AssignmentCard(
    var assignmentId: String ="",        // document id reference

    val subjectName: String ="",
    val subjectCode: String ="",

    val questionText: String ="",        // null if only image/pdf
    val questionImageUrl: String ="",    // null if no image
    val questionPdfUrl: String ="",      // null if no pdf

    val createdBy: String ="",            // CR / admin uid
    val createdAt: Long =0L,              // timestamp

    val lastDateToSubmit:Long=0L,
    val expiryAt: Long  =0L               // expiry timestamp
)
