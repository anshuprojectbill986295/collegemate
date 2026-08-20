package com.anshu.collegemate.Navigation

sealed class Screens(val title:String,val route:String) {
    object HomeScreen: Screens("Today's Schedule \uD83C\uDF92","homescreen")
    object NotificationHistoryScreen: Screens("Announcements  \uD83D\uDD14","notification")
    object AssignmentTestScreen: Screens("Assignments/Tests \uD83D\uDCDA","assignment")
    object AssignmentTestDetailedScreen: Screens("Assignments/Tests Detailed View \uD83D\uDCDA","assignmentTestDetailed")

    companion object {
        fun fromRoute(route: String?): Screens {
            return when {
                route == HomeScreen.route -> HomeScreen
                route == NotificationHistoryScreen.route -> NotificationHistoryScreen
                route == AssignmentTestScreen.route -> AssignmentTestScreen
                route?.startsWith(AssignmentTestDetailedScreen.route) == true -> AssignmentTestDetailedScreen
                else -> HomeScreen
            }
        }
    }
}