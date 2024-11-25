package com.sample.todolist.domain.usecase

import com.sample.todolist.data.TodoEntity
import com.sample.todolist.data.repo.TodoRepository
import javax.inject.Inject

class AddTodoUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(title: String) {
        repository.addTodo(TodoEntity(title = title))
    }
}