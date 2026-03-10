package com.anshu.collegemate.Navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.anshu.collegemate.ui.View.Screens.AssignmentTestDetailedScreen
import com.anshu.collegemate.ui.View.Screens.AssignmentTestScreen
import com.anshu.collegemate.ui.View.Screens.HomeScreen
import com.anshu.collegemate.ui.View.Screens.NotificationHistoryScreen
import com.anshu.collegemate.ui.ViewModel.AnnouncementViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NavigationGraph(navController: NavHostController, modifier: Modifier= Modifier,announcementViewModel: AnnouncementViewModel){


    NavHost(navController=navController, startDestination = Screens.HomeScreen.route) {
        composable(route= Screens.HomeScreen.route){
            HomeScreen()
        }
        composable(route= Screens.AssignmentTestScreen.route){
            AssignmentTestScreen(onViewClicked = { id,type->
                navController.navigate(Screens.AssignmentTestDetailedScreen
                    .route+"/${id}/${type}")
            })
            //TODO I thought to send instance of asstestvm to AssignmentTestScreen , but finding it difficult
        }
        composable(route= Screens.NotificationHistoryScreen.route){
           NotificationHistoryScreen(announcementViewModel)
        }
        composable(route= Screens.AssignmentTestDetailedScreen.route+"/{id}/{type}"){
            val id = navController.currentBackStackEntry?.arguments?.getString("id")?:""
            val type = navController.currentBackStackEntry?.arguments?.getString("type")?:""
            AssignmentTestDetailedScreen(id,type)
        }


    }
}