package com.anshu.collegemate.Data.Model.AssignmentTest

sealed class TimelineItem(val eventDate: Long) {
    data class AssignmentItem(
        val assignment: AssignmentCard
    ): TimelineItem(assignment.lastDateToSubmit)
    data class TestItem(
        val test: TestCard
    ): TimelineItem(test.testDate)
}