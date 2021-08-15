package com.odnovolov.randomuser.model

interface User {
    val id: String
    val firstName: String
    val lastName: String
    val photoUrl: String
    val age: Int
    val city: String
    val email: String
}

