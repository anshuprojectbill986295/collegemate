package com.anshu.collegemate.ui.ViewModel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anshu.collegemate.Data.Model.AssignmentTest.AssignmentCard
import com.anshu.collegemate.Data.Model.AssignmentTest.TestCard
import com.anshu.collegemate.Data.Model.AssignmentTest.TimelineItem
import com.anshu.collegemate.Data.Repository.AssignmentTestRepository
import com.anshu.collegemate.Utils.DateTimeUtil
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AssignmentTestVM(
    private val assignmentTestRepository: AssignmentTestRepository = AssignmentTestRepository()): ViewModel() {

    private var _fileURL = MutableStateFlow<String>("")
    val fileURL: StateFlow<String> = _fileURL
    private val _testList = MutableStateFlow<List<TestCard>>(emptyList())
    val testList: StateFlow<List<TestCard>> = _testList

    private val _assList = MutableStateFlow<List<AssignmentCard>>(emptyList())
    val assList: StateFlow<List<AssignmentCard>> = _assList

    @RequiresApi(Build.VERSION_CODES.O)
    val map: StateFlow<Map<String,List<TimelineItem>>> = combine(_testList,_assList){
        tests,ass->

        val testItems = tests.filter { it.testDate>= System.currentTimeMillis() }.map {
            TimelineItem.TestItem(it)
        }
        val assItems = ass.filter { it.lastDateToSubmit>= System.currentTimeMillis() }.map {
            TimelineItem.AssignmentItem(it)
        }
        val mergedSortedItems = (testItems+assItems).sortedBy { it.eventDate }
        mergedSortedItems.groupBy { DateTimeUtil.getHeaderLabel(it.eventDate) }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyMap()
    )
    @RequiresApi(Build.VERSION_CODES.O)
    val assMap: StateFlow<Map<String,List<TimelineItem.AssignmentItem>>> = _assList.map {
        ass->
        val assItems = ass.filter { it.lastDateToSubmit>= System.currentTimeMillis() }.map {
            TimelineItem.AssignmentItem(it)
        }
       val sortedAssItems = assItems.sortedBy { it.eventDate}
        sortedAssItems.groupBy { DateTimeUtil.getHeaderLabel(it.eventDate) }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyMap()
    )
    @RequiresApi(Build.VERSION_CODES.O)
    val testMap: StateFlow<Map<String,List<TimelineItem.TestItem>>> = _testList.map {
            tests->
        val testItems = tests.filter { it.testDate>= System.currentTimeMillis() }.map {
            TimelineItem.TestItem(it)
        }
        val sortedTestItems= testItems.sortedBy { it.eventDate }
        sortedTestItems.groupBy { DateTimeUtil.getHeaderLabel(it.eventDate) }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyMap()
    )
    private val _assByID = MutableStateFlow<TimelineItem.AssignmentItem>(TimelineItem.AssignmentItem(
        AssignmentCard()))
    val assByID: StateFlow<TimelineItem.AssignmentItem> = _assByID
    private val _testByID = MutableStateFlow<TimelineItem.TestItem>(TimelineItem.TestItem(
        TestCard()))
    val testByID: StateFlow<TimelineItem.TestItem> = _testByID
    @RequiresApi(Build.VERSION_CODES.O)
    fun addTest(test: TestCard){
        viewModelScope.launch {
            assignmentTestRepository.addTest(test)
            fetchAllTest()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchAllTest(){
        viewModelScope.launch {
            _testList.value=assignmentTestRepository.fetchAllTest()
        }
        Log.e("TestMap","${map.value.size}")
    }

    //For Assignment
    @RequiresApi(Build.VERSION_CODES.O)
    fun addAssignment(ass: AssignmentCard){
        viewModelScope.launch {
            assignmentTestRepository.addAssignment(ass)
            fetchAssignment()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchAssignment(){
        viewModelScope.launch {
            _assList.value  = assignmentTestRepository.fetchAssignments()
        }
        Log.e("AssMap","${map.value.size}")
    }
    fun getAssByID(id:String){
        viewModelScope.launch {
            _assByID.value = TimelineItem.AssignmentItem(assignmentTestRepository.getAssByID(id))
        }
    }
    fun getTestByID(id:String){
        viewModelScope.launch {
            _testByID.value = TimelineItem.TestItem(assignmentTestRepository.getTestByID(id))
        }
    }
    fun setFileURL(x:String){
        _fileURL.value=x
    }

}