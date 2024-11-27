package com.sample.todolist.domain.usecase

import com.example.database.TodoEntity
import com.example.database.repo.TodoRepository
import javax.inject.Inject

class AddTodoUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(title: String) {
        repository.addTodo(TodoEntity(title = title))
    }
}