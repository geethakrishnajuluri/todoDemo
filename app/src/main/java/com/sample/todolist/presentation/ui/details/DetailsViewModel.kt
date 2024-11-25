package com.sample.todolist.presentation.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sample.todolist.domain.usecase.AddTodoUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DetailsUiState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val errorMessage: String? = null
)

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val addTodoUseCase: AddTodoUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState

    fun addTodo(title: String) {
        if (title == "Error") {
            _uiState.value = DetailsUiState(errorMessage = "Invalid input!")
            return
        }

        _uiState.value = DetailsUiState(isLoading = true)

        viewModelScope.launch {
            try {
                addTodoUseCase(title)
                delay(3000)// Wait for 3 seconds
                _uiState.value = DetailsUiState(success = true)
            } catch (e: Exception) {
                _uiState.value = DetailsUiState(errorMessage = e.message)
            }
        }
    }

    fun resetState() {
        _uiState.value = DetailsUiState()
    }
}