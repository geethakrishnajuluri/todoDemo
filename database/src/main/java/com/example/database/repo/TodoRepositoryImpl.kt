package com.example.database.repo

import com.example.database.entity.TodoEntity
import com.example.database.database.TodoDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao
) : TodoRepository {
    override fun getTodos(query: String): Flow<List<TodoEntity>> = todoDao.getTodos(query)
    override suspend fun addTodo(todo: TodoEntity) = todoDao.insertTodo(todo)
}