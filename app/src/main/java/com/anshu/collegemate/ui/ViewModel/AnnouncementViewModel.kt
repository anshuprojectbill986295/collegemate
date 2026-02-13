package com.anshu.collegemate.ui.ViewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anshu.collegemate.Data.Model.Announcement.AnnouncementCard
import com.anshu.collegemate.Data.Repository.AnnouncementRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AnnouncementViewModel(
    private val repository: AnnouncementRepository = AnnouncementRepository()): ViewModel() {



    private val _announcements = MutableStateFlow<List<AnnouncementCard>>(emptyList())
    val announcements: StateFlow<List<AnnouncementCard>> = _announcements

    init{
        getAnnouncement()
    }
    fun saveAnnouncement(a: AnnouncementCard){
        repository.saveAnnouncementInFireStore(a)
        Log.e("Abhishshek","Abhsioshe'")
        getAnnouncement()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun fetchTodayCancelAnnouncement(): List<AnnouncementCard>{
        return repository.fetchTodayCancelAnnouncement()
    }
    fun getAnnouncement(){
        viewModelScope.launch {
            _announcements.value = repository.getAnnouncement().reversed()
        }
    }

}

