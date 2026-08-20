package com.anshu.collegemate.ui.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anshu.collegemate.Data.Model.Announcement.AnnouncementCard
import com.anshu.collegemate.Data.Repository.AnnouncementRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AnnouncementViewModel(
    private val repository: AnnouncementRepository = AnnouncementRepository()): ViewModel() {



    private val _selectedDate = MutableStateFlow("")

    private val generalAnnouncementsFlow = repository.getGeneralAnnouncementsFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val cancellationAnnouncementsFlow = repository.getCancellationAnnouncementsFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val announcements: StateFlow<List<AnnouncementCard>> = combine(
        generalAnnouncementsFlow,
        cancellationAnnouncementsFlow
    ) { general, cancellation ->
        (general + cancellation).sortedByDescending { it.createdAt }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    val classCancelledOnDate: StateFlow<List<AnnouncementCard>> = combine(
        cancellationAnnouncementsFlow,
        _selectedDate
    ) { cancellations, date ->
        if (date.isEmpty()) emptyList()
        else cancellations.filter { it.cancelDate == date }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun saveAnnouncement(a: AnnouncementCard){
        repository.saveAnnouncementInFireStore(a)
        Log.e("Abhishshek","Abhsioshe'")
    }
//    @RequiresApi(Build.VERSION_CODES.O)
//    suspend fun fetchTodayCancelAnnouncement(): List<AnnouncementCard>{
//        return repository.fetchTodayCancelAnnouncement()
//    }
    fun getClassCancelled(date:String){
        _selectedDate.value = date
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

