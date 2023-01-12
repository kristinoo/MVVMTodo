package com.codinginflow.mvvmtodo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao

interface TaskDao {

    @Query (value = "SELECT * FROM task_table")
    fun getTasks(): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task : Task)

    @Update
    suspend fun update(task : Task)

    @Delete
    suspend fun delete(task : Task)

}