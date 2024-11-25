package com.sample.todolist.data.repo

import com.sample.todolist.data.TodoEntity
import com.sample.todolist.data.database.TodoDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao
) : TodoRepository {
    override fun getTodos(query: String): Flow<List<TodoEntity>> = todoDao.getTodos(query)
    override suspend fun addTodo(todo: TodoEntity) = todoDao.insertTodo(todo)
}