package com.anshu.collegemate.Utils

object Miscealleneous {
    val colors = listOf("YELLOWTHEME", "TEALTHEME", "REDTHEME", "PURPLETHEME","ORANGETHEME", "BLUETHEME")
    val size= colors.size
    var x:Int=0
    fun getColorForAnnouncement(
        type: String
    ): String {
        return if (type == "general") {
            x=(x+1)%size
            colors[x]
        } else {
            "GREENTHEME"
        }
    }

}