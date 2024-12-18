package com.example.database.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.database.entity.TodoEntity

// Entity class is TodoItem and DAO is TodoDao
@Database(entities = [TodoEntity::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}