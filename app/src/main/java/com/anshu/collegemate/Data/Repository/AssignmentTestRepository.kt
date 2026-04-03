package com.anshu.collegemate.Data.Repository

import android.util.Log
import com.anshu.collegemate.Data.Injections.FireStoreInjection
import com.anshu.collegemate.Data.Model.AssignmentTest.AssignmentCard
import com.anshu.collegemate.Data.Model.AssignmentTest.TestCard
import kotlinx.coroutines.tasks.await
import kotlin.collections.emptyList

class AssignmentTestRepository {
    private val fs = FireStoreInjection.getFirestore()

    suspend fun addTest(test: TestCard){

        val docRef = fs.collection("AssignmentTest").document("Test").collection("Tests").document()
        val testWithId=test.copy(testId = docRef.id)
        docRef.set(testWithId).await()
    }
    suspend fun fetchAllTest():List<TestCard>{
        return try {
           // Log.e("fetchAllTest","No error in Assignment Test Repository")

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
    suspend fun getAssByID(id:String): AssignmentCard{
        return try{
            val snapshot = fs.collection("AssignmentTest").document("Assignment")
                .collection("Assignments").document(id).get().await()
            snapshot.toObject(AssignmentCard::class.java)?: AssignmentCard()
        } catch (e: Exception){
            Log.e("Assignment","Error fucked up fetch")
            AssignmentCard()
        }
    }
    suspend fun getTestByID(id:String): TestCard{
        return try{
            val snapshot = fs.collection("AssignmentTest").document("Test").collection("Tests")
                .document(id).get().await()
            snapshot.toObject(TestCard::class.java)?: TestCard()
        } catch (e: Exception){
            Log.e("Assignment","Error fucked up fetch")
            TestCard()
        }
    }
}