package com.example.myapplication.data.model

data class UserEntity(
    val id: Int,
    val name: String,
    val username: String,
    val email: String,
    val address: Address,
    val phone: String,
    val website: String,
    val company: Company
)

data class Address(
    val street: String,
    val suite: String,
    val city: String,
    val zipcode: String,
    val geo: Geo
)

data class Company(
    val name: String,
    val catchPhrase: String,
    val bs: String
)

data class Geo(
    var lat: String,
    var lng: String
)