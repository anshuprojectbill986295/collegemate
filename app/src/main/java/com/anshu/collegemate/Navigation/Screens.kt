package com.anshu.collegemate.Navigation

sealed class Screens(val title:String,val route:String) {
    object HomeScreen: Screens("Today's Schedule \uD83C\uDF92","homescreen")
    object NotificationHistoryScreen: Screens("Announcements  \uD83D\uDD14","notification")
    object AssignmentTestScreen: Screens("Assignments/Tests \uD83D\uDCDA","assignment")

}