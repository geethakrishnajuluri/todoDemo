package com.sample.todolist.di

import com.example.database.repo.TodoRepository
import com.sample.todolist.domain.usecase.AddTodoUseCase
import com.sample.todolist.domain.usecase.GetTodosUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideGetTodosUseCase(repository: TodoRepository) = GetTodosUseCase(repository)

    @Provides
    fun provideAddTodoUseCase(repository: TodoRepository) = AddTodoUseCase(repository)
}