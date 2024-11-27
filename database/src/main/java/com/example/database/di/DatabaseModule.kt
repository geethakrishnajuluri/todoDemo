package com.sample.todolist.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.database.database.TodoDao
import com.example.database.database.TodoDatabase
import com.example.database.repo.TodoRepository
import com.example.database.repo.TodoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideTodoDatabase(context: Context): TodoDatabase {
        return Room.databaseBuilder(context, TodoDatabase::class.java, "todo_db").build()
    }

    @Provides
    fun provideTodoDao(database: TodoDatabase): TodoDao = database.todoDao()


    @Provides
    fun provideTodoRepository(dao: TodoDao): TodoRepository = TodoRepositoryImpl(dao)

}