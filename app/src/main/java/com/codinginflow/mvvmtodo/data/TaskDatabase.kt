package com.codinginflow.mvvmtodo.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.codinginflow.mvvmtodo.di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Task::class], version = 1)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun taskDao(): TaskDao

    class Callback @Inject constructor(
        private val database: Provider<TaskDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            //db operation
            val dao = database.get().taskDao()
            applicationScope.launch {
                dao.insert(Task("Database", important = true))
                dao.insert(Task("Web app", completed = true))
                dao.insert(Task("Discrete_math"))
                dao.insert(Task("Meeting at 17^00", important = true))
                dao.insert(Task("Wake up", completed = true))
            }
        }
    }
}