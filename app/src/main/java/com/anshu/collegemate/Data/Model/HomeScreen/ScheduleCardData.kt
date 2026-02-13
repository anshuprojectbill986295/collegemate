package com.anshu.collegemate.Data.Model.HomeScreen

data class ScheduleCardData(
    val subjectCode: String = "",   // e.g. MA101 (used as document ID)
    val name: String = "",          // Maths
    val instructor: String = "",    // Dr. Sharma
    val roomNo: String = "",        // A-101
    val startTime: String = "",     // 09:00
    val endTime: String = "",       // 10:00
    val colorKey: String = ""  ,
    val syllabusLink:String=""// BLUE, GREEN, etc.
)