package com.anshu.collegemate.Data.Repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.anshu.collegemate.Data.Injections.FireStoreInjection
import com.anshu.collegemate.Data.Model.Announcement.AnnouncementCard
import com.anshu.collegemate.Utils.DateTimeUtil
import com.anshu.collegemate.ui.View.Others.MBS.AnnouncementMBS
import kotlinx.coroutines.tasks.await

class AnnouncementRepository {
    val result = mutableListOf<AnnouncementCard>(AnnouncementCard(message = "Test1"), AnnouncementCard(message = "Test2"))
    val firestore = FireStoreInjection.getFirestore()
    fun saveAnnouncementInFireStore(a: AnnouncementCard){
        result.add(a)
//        firestore.collection("Announcement").add(a)
//            .addOnSuccessListener { doc->
//                firestore.collection("Announcement")
//                    .document(doc.id).update("id",doc.id)
//            }
//            .addOnFailureListener { e->
//                Log.e("Error",e.toString())
//            }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun fetchTodayCancelAnnouncement():List<AnnouncementCard>{
        val todayDate = DateTimeUtil.getDateFromLong(System.currentTimeMillis())
        return try{
            val snapshots = firestore.collection("Announcement")
                .whereEqualTo("type","cancel").whereEqualTo("cancelDate",
                    "todayDate").get().await()
            snapshots.documents.mapNotNull { doc->
                doc.toObject(AnnouncementCard::class.java)
            }
        }
        catch(e: Exception){
            return emptyList()
        }
    }
    suspend fun getAnnouncement():List<AnnouncementCard>{
        return result
//        return try {
//            val snapshots = firestore.collection("Announcement").orderBy("createdAt").get().await()
//            snapshots.documents.mapNotNull { doc->
//                doc.toObject(AnnouncementCard::class.java)
//            }
//        }
//        catch (e: Exception){
//            return emptyList()
//        }
    }

}