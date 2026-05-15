package com.example.gandha_siri.data.dao

import androidx.room.*
import com.example.gandha_siri.data.model.Tree
import com.example.gandha_siri.data.model.Alert
import kotlinx.coroutines.flow.Flow

@Dao
interface TreeDao {

    // 🌳 TREE

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTree(tree: Tree)

    @Query("SELECT * FROM trees")
    fun getAllTrees(): Flow<List<Tree>>

    @Delete
    suspend fun deleteTree(tree: Tree)


    // 🚨 ALERTS

    @Insert
    suspend fun insertAlert(alert: Alert)

    @Query("SELECT * FROM alerts ORDER BY timestamp DESC")
    fun getAllAlerts(): Flow<List<Alert>>

    @Query("DELETE FROM alerts")
    suspend fun clearAlerts()
}