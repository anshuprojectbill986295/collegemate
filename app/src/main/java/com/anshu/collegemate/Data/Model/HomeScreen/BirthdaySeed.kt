package com.anshu.collegemate.Data.Model.HomeScreen

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Data model for a student's birthday information.
 * dateOfBirth is stored in "DD-MM-YYYY" format.
 */
data class BirthdayStudent(
    val name: String,
    val dateOfBirth: String
)

object BirthdaySeed {
    /**
     * Mapping of student emails to their birthday information for CSE 2024 batch.
     * Only students with a verified Date of Birth are included (Total: 25).
     */
    private val mapping = mapOf(
        "abhishek.cse.24@nitap.ac.in" to BirthdayStudent("ABHISHEK KUMAR GIRI", "01-12-2003"),
        "aditya.cse.24@nitap.ac.in" to BirthdayStudent("ADITYA RAJ", "12-04-2007"),
        "anshu.cse.24@nitap.ac.in" to BirthdayStudent("Anshu Kumar Gupta", "18-02-2006"),
        "anurag.cse.24@nitap.ac.in" to BirthdayStudent("ANURAG KUMAR OJHA", "05-02-2006"),
        "arjun.cse.24@nitap.ac.in" to BirthdayStudent("ARJUN MAURYA", "31-12-2005"),
        "divyanshu.cse.24@nitap.ac.in" to BirthdayStudent("DIVYANSHU GUPTA", "10-07-2005"),
        "fredy.cse.24@nitap.ac.in" to BirthdayStudent("FREDY KHATTIYA DEORI", "23-03-2006"),
        "gorle.cse.24@nitap.ac.in" to BirthdayStudent("GORLE SATYA SAI NIKHIL KUMAR", "04-08-2006"),
        "jainesh.cse.24@nitap.ac.in" to BirthdayStudent("JAINESH BIRLA", "24-04-2008"),
        "jikke.cse.24@nitap.ac.in" to BirthdayStudent("JIKKE YAMONI", "26-11-2006"),
        "kallepalli.cse.24@nitap.ac.in" to BirthdayStudent("KALLEPALLI HARSHIT", "14-07-2007"),
        "mohammed.cse.24@nitap.ac.in" to BirthdayStudent("MOHAMMED SAINUDHEEN ALI BI", "14-06-2005"),
        "nabam.cse.24@nitap.ac.in" to BirthdayStudent("NABAM RANA", "06-05-2004"),
        "ngilyang.cse.24@nitap.ac.in" to BirthdayStudent("Nancy N.", "13-08-2004"),
        "prateek.cse.24@nitap.ac.in" to BirthdayStudent("PRATEEK SINGH", "06-05-2005"),
        "ramavath.cse.24@nitap.ac.in" to BirthdayStudent("RAMAVATH NAGESH", "28-08-2006"),
        "riya.cse.24@nitap.ac.in" to BirthdayStudent("RIYA YADAV", "04-11-2005"),
        "rohit.cse.24@nitap.ac.in" to BirthdayStudent("ROHIT SHARMA", "14-04-2005"),
        "sagelo.cse.24@nitap.ac.in" to BirthdayStudent("SAGELO RANGMANG", "24-03-2005"),
        "sandeep.cse.24@nitap.ac.in" to BirthdayStudent("SANDEEP BUNKAR", "06-06-2005"),
        "satam.cse.24@nitap.ac.in" to BirthdayStudent("SATAM BISANDEY", "07-02-2008"),
        "tantapureddy.cse.24@nitap.ac.in" to BirthdayStudent("TANTAPUREDDY NITHIN", "31-05-2007"),
        "tenzin.cse.24@nitap.ac.in" to BirthdayStudent("TENZIN PHUNTSO", "25-05-2004"),
        "vikash.cse.24@nitap.ac.in" to BirthdayStudent("VIKASH PEGU", "02-01-2007"),
        "vinay.cse.24@nitap.ac.in" to BirthdayStudent("VINAY PAL", "23-08-2004")
    )

    /**
     * Look up the student's birthday information based on their email.
     * Robust against accidental leading/trailing spaces.
     */
    fun getBirthdayForUser(email: String): BirthdayStudent? {
        return mapping[email.trim()]
    }

    /**
     * Checks if today is the birthday of the student associated with the given email.
     * Compares only Day and Month, ignoring the year.
     */
    @RequiresApi(Build.VERSION_CODES.O)
    fun isBirthdayToday(email: String): Boolean {
        val student = getBirthdayForUser(email) ?: return false
        
        return try {
            val dob = LocalDate.parse(student.dateOfBirth, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            val today = LocalDate.now()
            
            dob.dayOfMonth == today.dayOfMonth && dob.month == today.month
        } catch (e: Exception) {
            false
        }
    }
}
