package com.anshu.collegemate.Navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.anshu.collegemate.ui.View.Screens.HomeScreen

@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier= Modifier){


    NavHost(navController=navController, startDestination = Screens.HomeScreen.route) {
        composable(route= Screens.HomeScreen.route){
            HomeScreen()
        }
        composable(route= Screens.AssignmentTestScreen.route){
            //AssignmentTestScreen()
        }
        composable(route= Screens.NotificationHistoryScreen.route){
           /// NotificationHistoryScreen(announcementViewModel)
        }


    }
}