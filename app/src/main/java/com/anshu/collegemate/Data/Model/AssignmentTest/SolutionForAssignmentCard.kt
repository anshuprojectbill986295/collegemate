package com.anshu.collegemate.Data.Model.AssignmentTest

data class SolutionForAssignmentCard(
    val solutionId: String,           // document id

    val assignmentId: String,         // which assignment
    val uploadedBy: String,           // student uid
    val uploadedAt: Long,

    val solutionImageUrl: String?,    // image solution
    val solutionPdfUrl: String?       // pdf solution
)