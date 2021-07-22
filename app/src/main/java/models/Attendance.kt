package models

data class Attendance(
    val percentage: Double = 0.0,
    val subjectCode: String = "",
    val attendedClasses: Int = 0,
    val totalClasses: Int = 0
)
