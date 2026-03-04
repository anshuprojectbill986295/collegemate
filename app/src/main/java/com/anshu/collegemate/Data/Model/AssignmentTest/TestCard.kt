package com.anshu.collegemate.Data.Model.AssignmentTest

data class TestCard(
    var testId: String="",

    val subjectName: String="",
    val subjectCode: String="",

    val createdBy: String="",            // teacher / admin
    val createdAt: Long=0L,

    val testDate: Long=0L,               // actual test date
    val expiryAt:Long=0L,    // auto-hide after test

    val syllabus: String="",

    val maxMarks: Int=5,
    val testType: String="ClassTest"              // Mid Sem / Class Test / End Sem
)