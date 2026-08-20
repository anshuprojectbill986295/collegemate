package com.anshu.collegemate

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.anshu.collegemate.Navigation.NavigationGraph
import com.anshu.collegemate.Navigation.Screens
import com.anshu.collegemate.ui.View.Others.Bars.BottomBar
import com.anshu.collegemate.ui.View.Others.Bars.TopBar
import com.anshu.collegemate.ui.View.Others.DialogBox.LogoutDialog
import com.anshu.collegemate.ui.View.Others.MBS.AnnouncementMBS
import com.anshu.collegemate.ui.View.Others.MBS.AssignmentTestMBS
import com.anshu.collegemate.ui.ViewModel.AnnouncementViewModel
import com.anshu.collegemate.ui.ViewModel.AssignmentTestVM
import com.anshu.collegemate.ui.ViewModel.NetworkViewModel
import com.anshu.collegemate.ui.ViewModel.UploadImgPDFVM
import kotlinx.coroutines.delay

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainView(onLogout:()->Unit) {

    val announcementViewModel: AnnouncementViewModel = viewModel()
    val assignmentTestVM: AssignmentTestVM = viewModel()
    val uploadImgPDFVM: UploadImgPDFVM = viewModel()
    val networkViewModel: NetworkViewModel = viewModel()

    val isOnline by networkViewModel.isOnline.collectAsState()
    var showConnectionRestored by remember { mutableStateOf(false) }
    var previousOnlineState by remember { mutableStateOf(true) }

    LaunchedEffect(isOnline) {
        if (isOnline && !previousOnlineState) {
            showConnectionRestored = true
            delay(3000)
            showConnectionRestored = false
        }
        previousOnlineState = isOnline
    }

    var showBottomSheet by remember { mutableStateOf(false) }
    val showDialog = remember { mutableStateOf(false) }
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentScreen = Screens.fromRoute(currentRoute)

    val gradient = Brush.linearGradient(
        colors = listOf(
            Color(0xfff0f4f8), Color(0xffe0e7ff)
        ), start = Offset(0f, 0f), end = Offset(100f, 100f)
    )

    Scaffold(
        topBar = { TopBar(currentScreen.title, { showDialog.value = true }) },
        bottomBar = { BottomBar(navController) },
        containerColor = Color.Transparent,
        floatingActionButton = {
            if (currentScreen.route != Screens.HomeScreen.route && currentScreen.route != Screens.AssignmentTestDetailedScreen.route) {
                ExtendedFloatingActionButton(onClick = {
                    showBottomSheet=true
                }) {
                    Icon(
                        painter = painterResource(
                            R.drawable.add_alert_24dp_75fb4c_fill1_wght400_grad0_opsz24
                        ),
                        contentDescription = null
                    )
                }
            }
        }
    )
    {
        Box(
            modifier = Modifier.background(gradient).padding(it)
                .fillMaxSize()
        ) {
            Column {
                // Global Connectivity Banner
                ConnectivityBanner(isOnline, showConnectionRestored)

                Box(modifier = Modifier.fillMaxSize().padding(top = 12.dp)) {
                    NavigationGraph(navController, Modifier, announcementViewModel, assignmentTestVM)
                }
            }
            LogoutDialog(showDialog, { onLogout() })


            //For Announcement Screen
             if (showBottomSheet){
                if (currentScreen.route== Screens.NotificationHistoryScreen.route){
                    AnnouncementMBS(announcementViewModel,onDismiss = {showBottomSheet=false})
                }
                else if(currentScreen.route== Screens.AssignmentTestScreen.route){
                    AssignmentTestMBS(assignmentTestVM,onDismiss = {showBottomSheet=false},uploadImgPDFVM)
                }
            }

        }


    }
}

@Composable
fun ConnectivityBanner(isOnline: Boolean, showRestored: Boolean) {
    AnimatedVisibility(
        visible = !isOnline || showRestored,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically()
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 5.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = if (isOnline) Color(0xFFD1FAE5) else Color(0xFFFFF3CD)
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = if (isOnline) Icons.Default.CheckCircle else Icons.Default.Warning,
                    contentDescription = null,
                    tint = if (isOnline) Color(0xFF065F46) else Color(0xFF856404)
                )
                Text(
                    text = if (isOnline)
                        "Internet connected — all features are working correctly."
                    else
                        "No internet connection. Some features may not work properly. Please connect to the internet.",
                    color = if (isOnline) Color(0xFF065F46) else Color(0xFF856404),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }
        }
    }
}

