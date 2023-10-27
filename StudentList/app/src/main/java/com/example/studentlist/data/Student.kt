package com.example.studentlist.data

import java.util.Date
import java.util.UUID

data class Student(
    val id: UUID = UUID.randomUUID(),
    var lastname: String="",
    var firstname: String="",
    var middleName: String="",
    var birthDate: Date = Date(),
    var groupID: UUID?=null
)