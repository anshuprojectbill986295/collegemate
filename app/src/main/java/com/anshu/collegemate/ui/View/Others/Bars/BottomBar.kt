package com.anshu.collegemate.ui.View.Others.Bars

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.anshu.collegemate.Navigation.Screens
import com.anshu.collegemate.R

@Composable
fun BottomBar(screen: MutableState<Screens>, navController: NavHostController) {


    Surface(shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp), modifier = Modifier.height(60.dp)
        , color = Color.White
    ) {
        BottomAppBar(containerColor = Color.White, modifier = Modifier.fillMaxHeight().padding(top = 9.dp)) {
            NavigationBarItem(selected = false, onClick = {screen.value= Screens.HomeScreen
                navController.navigate(screen.value.route)}, icon = {

                Icon(
                    painter = painterResource(if(screen.value.route== Screens.HomeScreen.route){
                        R.drawable.filled_home
                    }
                    else{
                        R.drawable.outline_home_24
                    }),

                    contentDescription = null, tint = Color.Black
                )
            }, colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent))
            NavigationBarItem(selected = false, onClick = {screen.value= Screens.AssignmentTestScreen
                navController.navigate(screen.value.route)}, icon = {

                Icon(
                    painter = painterResource(if(screen.value.route== Screens.AssignmentTestScreen.route){
                        R.drawable.filled_assignment_24px
                    }
                    else{
                        R.drawable.outline_assignment_24
                    }),

                    contentDescription = null, tint = Color.Black
                )
            }, colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent))
            NavigationBarItem(selected = false, onClick = {screen.value= Screens.NotificationHistoryScreen
                navController.navigate(screen.value.route)}, icon = {

                Icon(
                    painter = painterResource(if(screen.value.route== Screens.NotificationHistoryScreen.route){
                        R.drawable.filled_notifications_24px
                    }
                    else{
                        R.drawable.outline_notifications_24
                    }),

                    contentDescription = null, tint = Color.Black
                )
            }, colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent))
        }
    }
}



//@SuppressLint("UnrememberedMutableState")
//@Preview(showBackground = true)
//@Composable
//fun BottomBarPreview(){
//    val screen = mutableStateOf<Screens>(Screens.HomeScreen)
//    BottomBar(screen)
//}