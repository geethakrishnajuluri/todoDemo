package com.sample.todolist.domain.usecase

import com.sample.todolist.data.repo.TodoRepository
import com.sample.todolist.domain.model.Todo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class GetTodosUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    operator fun invoke(query: String = ""): Flow<List<Todo>> {
        return repository.getTodos(query).map { entities ->
            entities.map { Todo(it.id, it.title) }
        }
    }
}