package com.sample.todolist.presentation.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.todolist.domain.model.Todo
import com.sample.todolist.domain.usecase.AddTodoUseCase
import com.sample.todolist.domain.usecase.GetTodosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class MainViewModel @Inject constructor(
    private val getTodosUseCase: GetTodosUseCase
) : ViewModel() {
    private val _todosState = MutableStateFlow<List<Todo>>(emptyList())
    val todosState = _todosState.asStateFlow()

    private val _searchState = MutableStateFlow("")
    val searchState: StateFlow<String> = _searchState

    init {
        fetchTodos()
    }

    fun fetchTodos(query: String = "") {
        viewModelScope.launch {
            getTodosUseCase(query).collect { todos ->
                _todosState.value = todos
            }
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchState.value = query
        fetchTodos(query)
    }
}