package com.example.studentlist.data

import java.util.UUID

data class Group(
    val id: UUID = UUID.randomUUID(),
    val name: String="",
    val facultyID: UUID?=null
)