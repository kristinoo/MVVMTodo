package com.codinginflow.mvvmtodo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.codinginflow.mvvmtodo.ui.tasks.SortOrder
import kotlinx.coroutines.flow.Flow

@Dao

interface TaskDao {

    fun getTasks(query: String, sortOrder: SortOrder, hideCompleted: Boolean) : Flow<List<Task>> =
        when(sortOrder) {
            SortOrder.BY_DATE -> getTasksSortedByDateCreated(query, hideCompleted)
            SortOrder.BY_NAME -> getTasksSortedByName(query, hideCompleted)
        }

    @Query (value = "SELECT * FROM task_table WHERE(completed != :hideCompleted OR completed = 0) AND name LIKE '%' || :searchQuery || '%' ORDER BY important DESC, name")
    fun getTasksSortedByName(searchQuery: String, hideCompleted: Boolean): Flow<List<Task>>

    @Query (value = "SELECT * FROM task_table WHERE(completed != :hideCompleted OR completed = 0) AND name LIKE '%' || :searchQuery || '%' ORDER BY important DESC, created")
    fun getTasksSortedByDateCreated(searchQuery: String, hideCompleted: Boolean): Flow<List<Task>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task : Task)

    @Update
    suspend fun update(task : Task)

    @Delete
    suspend fun delete(task : Task)

}