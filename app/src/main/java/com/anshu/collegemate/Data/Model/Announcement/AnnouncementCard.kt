package com.anshu.collegemate.Data.Model.Announcement

import com.anshu.collegemate.Utils.Miscealleneous

val colors = listOf("YELLOWTHEME", "TEALTHEME", "REDTHEME", "PURPLETHEME","ORANGETHEME",
    "BLUETHEME")
val size= colors.size

var x:Int=0

val randomColor = colors[x]


data class AnnouncementCard(
    val id: String="",
    val type:String="",

    val message:String="",

    val announcerName:String="",
    val createdAt:Long=0L,
    val colorKey: String = Miscealleneous.getColorForAnnouncement(type),
    //cancel related...
    val cancelDate:String?=null,
    val day: String?=null,
    val subjectCode:String?=null
)

