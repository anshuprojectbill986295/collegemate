package com.anshu.collegemate.Data.Repository

import android.util.Log
import com.anshu.collegemate.Data.Injections.FireStoreInjection
import com.anshu.collegemate.Data.Model.AssignmentTest.AssignmentCard
import com.anshu.collegemate.Data.Model.AssignmentTest.TestCard
import kotlinx.coroutines.tasks.await

class AssignmentTestRepository {
    private val fs = FireStoreInjection.getFirestore()

    suspend fun addTest(test: TestCard){

        val docRef = fs.collection("AssignmentTest").document("Test").collection("Tests").document()
        val testWithId=test.copy(testId = docRef.id)
        docRef.set(testWithId).await()
    }
    suspend fun fetchAllTest():List<TestCard>{
        return try {
            val snapshots=fs.collection("AssignmentTest").document("Test").collection("Tests").get().await()
            snapshots.toObjects(TestCard::class.java)
        }
        catch (e:Exception){
            Log.e("fetchAllTest","Error in Assignment Test Repository")
            emptyList()
        }


    }
    //For Assignment

    suspend fun addAssignment(ass: AssignmentCard){
        val docRef = fs.collection("AssignmentTest").document("Assignment").collection("Assignments")
            .document()

        val assWithId=ass.copy(assignmentId = docRef.id)
        docRef.set(assWithId).await()
    }
    suspend fun fetchAssignments():List<AssignmentCard>{
        return try {
            val snapshots = fs.collection("AssignmentTest").document("Assignment")
                .collection("Assignments").get().await()
            snapshots.toObjects(AssignmentCard::class.java)
        }
        catch (e: Exception){
            Log.e("Assignment","Error fucked up fetch")
            emptyList()
        }

    }
}