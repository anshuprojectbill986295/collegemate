package com.anshu.collegemate.Data.Model.AssignmentTest

enum class TESTTYPE{
   CLASS_TEST,SURPRISE_TEST
}

data class TestCard(
    var testId: String="",

    val subjectName: String="",
    val subjectCode: String="",

    val createdBy: String="",            // teacher / admin
    val createdAt: Long=0L,

    val testDate: Long=System.currentTimeMillis().plus(24*60*60*1000),               // actual test date
    val syllabus: String="",

    val syllabusImageUrl: String ="",    // null if no image
    val syllabusFileUrl: String ="",// Mid Sem / Class Test / End Sem

    val maxMarks: Int=5,
    val testType:TESTTYPE = TESTTYPE.CLASS_TEST,
    // Change expiryAt from Long to Date
    val expiryDate: java.util.Date? = null // Add this for TTL
)