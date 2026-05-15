package com.example.gandha_siri.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alerts")
data class Alert(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val message: String,
    val timestamp: Long
)