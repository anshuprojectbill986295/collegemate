package com.anshu.collegemate.Utils

object Miscealleneous {
    val colors = listOf("YELLOWTHEME", "TEALTHEME", "GREENTHEME", "PURPLETHEME","ORANGETHEME", "BLUETHEME")
    val size= colors.size
    var x:Int=0
    fun getColorForAnnouncement(type: String): String {
        return when (type) {
            "cancel" -> "REDTHEME"
            else -> {
                val color = colors[x % colors.size]
                x++
                color
            }
        }
    }


}