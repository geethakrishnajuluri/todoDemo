package com.sample.todolist.data.repo

import com.sample.todolist.data.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getTodos(query: String): Flow<List<TodoEntity>>
    suspend fun addTodo(todo: TodoEntity)
}