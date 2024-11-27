package com.sample.todolist.presentation.ui.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sample.todolist.domain.usecase.AddTodoUseCase
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class DetailsViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule() // Ensures LiveData works synchronously in tests

    private lateinit var detailsViewModel: DetailsViewModel
    private val addTodoUseCase: AddTodoUseCase = mockk()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        clearMocks(addTodoUseCase)
        detailsViewModel = DetailsViewModel(addTodoUseCase)


        Dispatchers.setMain(testDispatcher)
    }

    @Test
    fun `addTodo should set loading state and then success on successful`() = runTest {
        // Given
        val todoTitle = "Test Todo"
        coEvery { addTodoUseCase(todoTitle) } returns Unit // Mock successful use case execution

        // when
        detailsViewModel.addTodo(todoTitle)

        // then
        assertTrue(detailsViewModel.uiState.value.isLoading)

        testDispatcher.scheduler.advanceTimeBy(3100)

        assertTrue(detailsViewModel.uiState.value.success)
        assertNull(detailsViewModel.uiState.value.errorMessage)

        coVerify { addTodoUseCase(todoTitle) }
    }

    @Test
    fun `addTodo should set error state when use case throws exception`() = runTest {

        val todoTitle = "Test Todo"
        val exception = Exception("Network error")
        coEvery { addTodoUseCase(todoTitle) } throws exception // Mock exception

        // Act
        detailsViewModel.addTodo(todoTitle)


        assertTrue(detailsViewModel.uiState.value.isLoading)
        testDispatcher.scheduler.advanceTimeBy(3100)
        assertFalse(detailsViewModel.uiState.value.success)
        assertEquals("Network error", detailsViewModel.uiState.value.errorMessage)
        coVerify { addTodoUseCase(todoTitle) }
    }

    @Test
    fun `addTodo should set error state when title is 'Error'`() = runTest {
        val invalidTitle = "Error"


        detailsViewModel.addTodo(invalidTitle)


        assertFalse(detailsViewModel.uiState.value.isLoading)
        assertFalse(detailsViewModel.uiState.value.success)
        assertEquals("Invalid input!", detailsViewModel.uiState.value.errorMessage)
    }

    @Test
    fun `resetState should reset the UI state to initial values`() = runTest {
        detailsViewModel.addTodo("Test Todo") // Set some state


        detailsViewModel.resetState()


        assertFalse(detailsViewModel.uiState.value.isLoading)
        assertFalse(detailsViewModel.uiState.value.success)
        assertNull(detailsViewModel.uiState.value.errorMessage)
    }
}