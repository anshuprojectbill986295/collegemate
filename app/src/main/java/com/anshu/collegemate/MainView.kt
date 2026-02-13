package com.anshu.collegemate

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.anshu.collegemate.Navigation.NavigationGraph
import com.anshu.collegemate.Navigation.Screens
import com.anshu.collegemate.ui.View.Others.Bars.BottomBar
import com.anshu.collegemate.ui.View.Others.Bars.TopBar
import com.anshu.collegemate.ui.View.Others.DialogBox.LogoutDialog

@Composable
fun MainView(onLogout:()->Unit) {

    //TODO  announcementViewModel: AnnouncementViewModel = viewModel()
    //TODO  assignmentTestVM: AssignmentTestVM = viewModel()
    //TODO var showBottomSheet by remember { mutableStateOf(false) }
    val screen = remember { mutableStateOf<Screens>(Screens.HomeScreen) }
    val showDialog = remember { mutableStateOf(false) }
    val navController = rememberNavController()
    val gradient = Brush.linearGradient(
        colors = listOf(
            Color(0xfff0f4f8), Color(0xffe0e7ff)
        ), start = Offset(0f, 0f), end = Offset(100f, 100f)
    )

    Scaffold(
        topBar = { TopBar(screen.value.title, { showDialog.value = true }) },
        bottomBar = { BottomBar(screen, navController) },
        containerColor = Color.Transparent,
//  TODO      floatingActionButton = {
//            if (screen.value.route != Screens.HomeScreen.route) {
//                ExtendedFloatingActionButton(onClick = {
//                    showBottomSheet=true
//                }) {
//                    Icon(
//                        painter = painterResource(
//                            com.example.collegemate.R.drawable
//                                .add_alert_24dp_75fb4c_fill1_wght400_grad0_opsz24
//                        ),
//                        contentDescription = null
//                    )
//                }
//            }
//        }
    )
    {
        Box(
            modifier = Modifier.background(gradient).padding(it).padding(top = 12.dp)
                .fillMaxSize()
        ) {
            NavigationGraph(navController, Modifier
                //TODO, announcementViewModel
            )
            LogoutDialog(showDialog, { onLogout() })


            //For Announcement Screen
//        TODO     if (showBottomSheet){
//                if (screen.value.route== Screens.NotificationHistoryScreen.route){
//                    AnnouncementMBS(announcementViewModel,onDismiss = {showBottomSheet=false})
//                }
//                else if(screen.value.route== Screens.AssignmentTestScreen.route){
//                    AssignmentTestMBS(assignmentTestVM,onDismiss = {showBottomSheet=false})
//                }
//            }

        }


    }
}
@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun nnnn() {
    MainView { }
}
