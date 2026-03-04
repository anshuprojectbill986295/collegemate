package com.anshu.collegemate.ui.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anshu.collegemate.Data.Model.AssignmentTest.AssignmentCard
import com.anshu.collegemate.Data.Model.AssignmentTest.TestCard
import com.anshu.collegemate.Data.Repository.AssignmentTestRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AssignmentTestVM(
    private val assignmentTestRepository: AssignmentTestRepository = AssignmentTestRepository()): ViewModel() {

    private val _testList = MutableStateFlow<List<TestCard>>(emptyList())
    val testList: StateFlow<List<TestCard>> = _testList

    private val _assList = MutableStateFlow<List<AssignmentCard>>(emptyList())
    val assList: StateFlow<List<AssignmentCard>> = _assList

    fun addTest(test: TestCard){
        viewModelScope.launch {
            assignmentTestRepository.addTest(test)
            fetchAllTest()
        }
    }
    fun fetchAllTest(){
        viewModelScope.launch {
            _testList.value=assignmentTestRepository.fetchAllTest()
        }
    }

    //For Assignment
    fun addAssignment(ass: AssignmentCard){
        viewModelScope.launch {
            assignmentTestRepository.addAssignment(ass)
            fetchAssignment()
        }
    }
    fun fetchAssignment(){
        viewModelScope.launch {
            _assList.value  = assignmentTestRepository.fetchAssignments()
        }
    }

}