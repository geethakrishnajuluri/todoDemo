package com.sample.todolist.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.sample.todolist.data.database.TodoDao
import com.sample.todolist.data.database.TodoDatabase
import com.sample.todolist.data.repo.TodoRepository
import com.sample.todolist.data.repo.TodoRepositoryImpl
import com.sample.todolist.domain.usecase.AddTodoUseCase
import com.sample.todolist.domain.usecase.GetTodosUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {


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

    @Provides
    fun provideGetTodosUseCase(repository: TodoRepository) = GetTodosUseCase(repository)

    @Provides
    fun provideAddTodoUseCase(repository: TodoRepository) = AddTodoUseCase(repository)
}