package com.anshu.collegemate.Utils

import com.anshu.collegemate.Data.Model.Announcement.ANNOUNCEMENTTYPE

object Miscealleneous {
    val colors = listOf("YELLOWTHEME", "TEALTHEME", "GREENTHEME", "PURPLETHEME","ORANGETHEME", "BLUETHEME")
    val size= colors.size
    var x:Int=0
    fun getColorForAnnouncement(type: ANNOUNCEMENTTYPE): String {
        return when (type) {
            ANNOUNCEMENTTYPE.CANCELLATION -> "REDTHEME"
            else -> {
                val color = colors[x % colors.size]
                x++
                color
            }
        }
    }


}