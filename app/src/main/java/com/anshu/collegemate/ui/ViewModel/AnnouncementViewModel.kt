package com.anshu.collegemate.ui.ViewModel

import android.util.Log
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

    private val _classCancelledOnDate = MutableStateFlow<List<AnnouncementCard>>(emptyList())
    val classCancelledOnDate: StateFlow<List<AnnouncementCard>> = _classCancelledOnDate

    private val _isOffline = MutableStateFlow<Boolean>(false)
    val isOffline: StateFlow<Boolean> = _isOffline

    init{
        Log.d("init98","EnterGet")

        getAnnouncement()
        Log.d("init98","EnterGet")

    }

    fun saveAnnouncement(a: AnnouncementCard){
        repository.saveAnnouncementInFireStore(a)
        Log.e("Abhishshek","Abhsioshe'")
        getAnnouncement()
    }
//    @RequiresApi(Build.VERSION_CODES.O)
//    suspend fun fetchTodayCancelAnnouncement(): List<AnnouncementCard>{
//        return repository.fetchTodayCancelAnnouncement()
//    }
    fun getAnnouncement(){
    Log.d("getAnn1","EnterGet")
    viewModelScope.launch {
             val generaList = repository.getGeneralAnnouncement()
             val cancelList = repository.getCancellationAnnouncement()
             val combinedList = generaList.plus(cancelList)
            _announcements.value = combinedList.sortedBy { it.createdAt }.reversed()
            Log.d("getAnn2",_announcements.value.size.toString())
        }
    Log.d("getAnn3",_announcements.value.size.toString())
}
    fun getClassCancelled(date:String){
        viewModelScope.launch {
            try {
                _classCancelledOnDate.value = repository.getCancellationAnnouncement(date)

                _isOffline.value = false
            } catch (e: Exception) {
                Log.e("Network Call has failed.", "${e.toString()}")
                _isOffline.value = true
            }
        }

    }
//    @RequiresApi(Build.VERSION_CODES.O)
//    fun getUpdatedClassSchedule(date:Long): List<ScheduleCardData>{
//        viewModelScope.launch {
//            getClassCancelled(DateTimeUtil.getDateFromLong(date))
//            val result = mutableListOf<ScheduleCardData>()
//            val actual = RoutineSeed.weeklyRoutine[DateTimeUtil.getDayFromLong(date)] ?: emptyList()
//            for (x in actual) {
//                if (_classCancelledOnDate.value.any { it.classStartTime == x.startTime }) {
//                } else {
//                    result.add(x)
//                }
//            }
//        }
//    }
}

