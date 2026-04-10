package com.anshu.collegemate.Data.Repository

import android.util.Log
import com.anshu.collegemate.Data.Injections.FireStoreInjection
import com.anshu.collegemate.Data.Model.Announcement.ANNOUNCEMENTTYPE
import com.anshu.collegemate.Data.Model.Announcement.AnnouncementCard
import com.google.firebase.firestore.Source
import kotlinx.coroutines.tasks.await


class AnnouncementRepository {
    val firestore = FireStoreInjection.getFirestore()
    fun saveAnnouncementInFireStore(a: AnnouncementCard){
        // Calculate 7 days from now
        val calendar = java.util.Calendar.getInstance()
        calendar.add(java.util.Calendar.DAY_OF_YEAR, 7)
        val sevenDaysFromNow = calendar.time

        val announcementWithExpiry = a.copy(expiryDate = sevenDaysFromNow)

            firestore.collection("Announcement").document(a.type.toString()).collection("items")
                .add(announcementWithExpiry)
                .addOnSuccessListener { doc ->
                    firestore.collection("Announcement").document(a.type.toString()).collection("items")
                        .document(doc.id).update("id", doc.id)
                    Log.e("Success84", "Success84")

                }
                .addOnFailureListener { e ->
                    Log.e("Error98", e.toString())
                }

    }


//    @RequiresApi(Build.VERSION_CODES.O)
//    suspend fun fetchTodayCancelAnnouncement():List<AnnouncementCard>{
//        val todayDate = DateTimeUtil.getDateFromLong(System.currentTimeMillis())
//        return try{
//            val snapshots = firestore.collection("Announcement")
//                .whereEqualTo("type","cancel").whereEqualTo("cancelDate",
//                    todayDate).get().await()
//            snapshots.documents.mapNotNull { doc->
//                doc.toObject(AnnouncementCard::class.java)
//            }
//        }
//        catch(e: Exception){
//            return emptyList()
//        }
//    }
    suspend fun getGeneralAnnouncement():List<AnnouncementCard>{
        return try {
            val snapshots = firestore.collection("Announcement").document(
                ANNOUNCEMENTTYPE.GENERAL.toString()).collection("items").get().await()
            snapshots.documents.mapNotNull { doc->
                doc.toObject(AnnouncementCard::class.java)
            }
        }
        catch (e: Exception){
            Log.d("EMPTYLIST12",e.toString())
            return emptyList()
        }
    }
    suspend fun getCancellationAnnouncement():List<AnnouncementCard>{
        return try {
            val snapshots = firestore.collection("Announcement").document(
                ANNOUNCEMENTTYPE.CANCELLATION.toString()).collection("items").get().await()
            snapshots.documents.mapNotNull { doc->
                doc.toObject(AnnouncementCard::class.java)
            }
        }
        catch (e: Exception){
            Log.d("EMPTYLIST13","")
            return emptyList()
        }
    }
    suspend fun getCancellationAnnouncement(date:String):List<AnnouncementCard>{

            val snapshots = firestore.collection("Announcement").document(
                ANNOUNCEMENTTYPE.CANCELLATION.toString()).collection("items")
                .whereEqualTo("cancelDate",date).get(Source.SERVER).await()
            return snapshots.documents.mapNotNull { doc->
                doc.toObject(AnnouncementCard::class.java)
            }


    }

}