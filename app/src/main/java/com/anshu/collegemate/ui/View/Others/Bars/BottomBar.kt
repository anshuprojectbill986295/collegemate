package com.anshu.collegemate.ui.View.Others.Bars

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.anshu.collegemate.Navigation.Screens
import com.anshu.collegemate.R

@Composable
fun BottomBar(screen: MutableState<Screens>, navController: NavHostController) {
    // Removed the shape parameter so it defaults to a flat rectangle
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding() // Moved inside! Pushes the icons up, but leaves white background behind system buttons
                .height(52.dp),          // Reduced from 60.dp for a slimmer look
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavigationBarItem(
                selected = screen.value.route == Screens.HomeScreen.route,
                onClick = {
                    screen.value = Screens.HomeScreen
                    navController.navigate(screen.value.route)
                },
                icon = {
                    Icon(
                        painter = painterResource(
                            if (screen.value.route == Screens.HomeScreen.route) R.drawable.filled_home
                            else R.drawable.outline_home_24
                        ),
                        contentDescription = "Home",
                        tint = Color.Black
                    )
                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )

            NavigationBarItem(
                selected = screen.value.route == Screens.AssignmentTestScreen.route,
                onClick = {
                    screen.value = Screens.AssignmentTestScreen
                    navController.navigate(screen.value.route)
                },
                icon = {
                    Icon(
                        painter = painterResource(
                            if (screen.value.route == Screens.AssignmentTestScreen.route) R.drawable.filled_assignment_24px
                            else R.drawable.outline_assignment_24
                        ),
                        contentDescription = "Assignments",
                        tint = Color.Black
                    )
                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )

            NavigationBarItem(
                selected = screen.value.route == Screens.NotificationHistoryScreen.route,
                onClick = {
                    screen.value = Screens.NotificationHistoryScreen
                    navController.navigate(screen.value.route)
                },
                icon = {
                    Icon(
                        painter = painterResource(
                            if (screen.value.route == Screens.NotificationHistoryScreen.route) R.drawable.filled_notifications_24px
                            else R.drawable.outline_notifications_24
                        ),
                        contentDescription = "Notifications",
                        tint = Color.Black
                    )
                },
                colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
            )
        }
    }
}