package com.anshu.collegemate.Utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate.now
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

object DateTimeUtil {

    @RequiresApi(Build.VERSION_CODES.O)
    private val dateFormater = DateTimeFormatter.ofPattern("dd MM yyyy")
    @RequiresApi(Build.VERSION_CODES.O)
    private val dayFormater = DateTimeFormatter.ofPattern("EEEE")

    @RequiresApi(Build.VERSION_CODES.O)
    fun todayDate():String{
        return now().format(dateFormater)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun tomorrowDate():String{
        return now().plusDays(1).format(dateFormater)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun todayDay():String{
        return toTitleCase(now().format(dayFormater)).lowercase()
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun tomorrowDay():String{
        return now().plusDays(1).format(dayFormater).lowercase()
    }
    fun convert(time24:String):String{
        val inputFormater = SimpleDateFormat("HH:mm",Locale.getDefault())
        val outputFormatter = SimpleDateFormat("hh:mm a", Locale.getDefault())
        val date=inputFormater.parse(time24)
        return outputFormatter.format(date!!).uppercase()
    }
    fun toTitleCase(text: String): String {
        return text
            .lowercase()
            .split(" ")
            .joinToString(" ") { word ->
                word.replaceFirstChar { it.uppercase() }
            }
    }
    fun verifyCollege(email:String,onDefaulter:()-> Unit){
        var ch=0
        while (email[ch]!='@'){
            ch++
        }
        if (email[++ch]!='n'){
            onDefaulter()
        }
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getDayFromLong(time:Long):String{
        val day= Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).dayOfWeek.
        getDisplayName(TextStyle.FULL, Locale.ENGLISH).lowercase()
        return day
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getDateFromLong(time:Long):String{
        return  Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("dd MM yyyy"))
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun getDateMonthFromLong(time: Long):String{
        return  Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MMMM  dd"))

    }
    fun classCancelledMessage(
        date: String,
        day: String,
        subject: String
    ): String {
        return """
🚫 Class Cancelled
📚 $subject
🗓️ $day • $date
""".trimIndent()

    }

    ////////////
    fun getTimeAgo(createdAt:Long):String{
        val diff = System.currentTimeMillis()-createdAt

        val seconds = diff/1000
        val minutes = seconds/60
        val hours = minutes/60
        val days =hours/24

        return when{
            seconds<60-> "just now"
            minutes<60-> "$minutes minutes ago"
            hours<24-> "$hours hours  ago"
            days<7->"$days days ago"
            else-> {
                val weeks = days / 7
                "$weeks weeks ago"
            }
        }
    }


}