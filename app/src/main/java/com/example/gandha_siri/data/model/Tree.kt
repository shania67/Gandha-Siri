package com.example.gandha_siri.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trees")
data class Tree(
    @PrimaryKey
    val id: String,

    val name: String,
    val girth: String,
    val age: String,

    val location: String,
    val latitude: Double,
    val longitude: Double,

    val imageUri: String
)