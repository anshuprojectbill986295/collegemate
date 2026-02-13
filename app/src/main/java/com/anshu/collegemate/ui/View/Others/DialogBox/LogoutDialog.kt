package com.anshu.collegemate.ui.View.Others.DialogBox

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LogoutDialog(showDialog: MutableState<Boolean>,onLogout:()-> Unit) {
    if(showDialog.value){
        AlertDialog(onDismissRequest = {showDialog.value=false}, confirmButton = { TextButton(onClick = {
            showDialog.value=false
            onLogout()}
        ) { Text(text = "Log Out") }
        }
            , dismissButton = {TextButton(onClick = {
                showDialog.value=false
            }, Modifier.padding(end = 80.dp)
            ) { Text(text = "Cancel", modifier = Modifier.padding(start = 0.dp)) }}
            ,   title = {Text(text = "Leaving so soon? 👋")}
            , text = {Text(text = "Do you really want to log out?")}
        )

    }
}
@Preview(showBackground = true)
@Composable
fun nnnn(){
    val x= mutableStateOf(true)
    LogoutDialog(x) { }
}
