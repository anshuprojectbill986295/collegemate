package com.anshu.collegemate.ui.View.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.anshu.collegemate.ui.View.Others.DataCardView.AnnouncementCardView
import com.anshu.collegemate.ui.ViewModel.AnnouncementViewModel

@Composable
fun NotificationHistoryScreen(viewModel: AnnouncementViewModel = viewModel()) {

    val list by viewModel.announcements.collectAsState()
    val gradient = Brush.linearGradient(colors = listOf(Color(0xfff0f4f8)
        ,Color(0xffe0e7ff)), start = Offset(0f,0f),end= Offset(100f,100f))



    Box(modifier = Modifier
        .background(gradient)
        .fillMaxSize()
    ){

        LazyColumn() {
            items(list) {
                AnnouncementCardView(it)

            }
        }

    }

}