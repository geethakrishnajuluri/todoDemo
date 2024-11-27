package com.example.database.repo

import com.example.database.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getTodos(query: String): Flow<List<TodoEntity>>
    suspend fun addTodo(todo: TodoEntity)
}