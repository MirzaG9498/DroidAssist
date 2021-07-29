package models

data class Attendance(
    val branch: String = "",
    val sem: String = "",
    val sec: String = "",
    val usn: String = "",
    val subjectCode: String = "",
    val attendedClasses: String = "",
    val totalClasses: String = ""
)
