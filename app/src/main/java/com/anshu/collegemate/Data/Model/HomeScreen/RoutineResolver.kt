package com.anshu.collegemate.Data.Model.HomeScreen

object RoutineResolver {

    /**
     * Resolves the routine for a specific day and student email.
     */
    fun resolveDayRoutine(day: String, email: String): List<ScheduleCardData> {
        val baseRoutine = RoutineSeed.weeklyRoutine[day.lowercase()] ?: return emptyList()
        val studentOE = OESeed.getElectiveForUser(email)

        return baseRoutine.map { resolveClass(it, studentOE) }
    }

    /**
     * Resolves the list of distinct classes for a specific student email.
     * Used for subject selection in Assignments/Tests.
     */
    fun resolveDistinctClasses(email: String): List<ScheduleCardData> {
        val studentOE = OESeed.getElectiveForUser(email)
        return RoutineSeed.setOfDistinctClasses.map { resolveClass(it, studentOE) }
    }

    /**
     * Private helper to resolve a single ScheduleCardData if it's an OE-3 slot.
     */
    private fun resolveClass(scd: ScheduleCardData, studentOE: StudentOE?): ScheduleCardData {
        // Only resolve if it's the OE-3 slot
        if (scd.subjectCode != "OE-3") return scd

        // Safe fallback: If no OE mapping is found, or if mapping has blank code/name, return generic.
        if (studentOE == null || studentOE.subjectCode.isBlank() || studentOE.subjectName.isBlank()) {
            return scd
        }

        // Return a copy with personalized OE information
        return scd.copy(
            subjectCode = studentOE.subjectCode,
            name = studentOE.subjectName,
            instructor = studentOE.instructor,
            roomNo = studentOE.venue,
            syllabusLink = studentOE.syllabusLink
        )
    }
}
