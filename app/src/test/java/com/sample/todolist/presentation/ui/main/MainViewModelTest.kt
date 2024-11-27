package com.sample.todolist.presentation.ui.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sample.todolist.domain.model.Todo
import com.sample.todolist.domain.usecase.GetTodosUseCase
import io.mockk.clearMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val getTodosUseCase: GetTodosUseCase = mockk() // Mock the use case
    private val testDispatcher =  StandardTestDispatcher()
    lateinit var mainViewModel : MainViewModel


    val mockTodos = listOf(
        Todo(1, "Todo 1"),
        Todo(2, "Todo 2")
    )

    @Before
    fun setup() {
        clearMocks(getTodosUseCase)


        Dispatchers.setMain(testDispatcher)


        coEvery { getTodosUseCase(any()) } returns flowOf(mockTodos)
        mainViewModel = MainViewModel(getTodosUseCase)
    }

    @Test
    fun `init should fetch todos on creation`() = runTest {
        // Assert that the state is updated with the mock todos
        assertEquals(mockTodos, mainViewModel.todosState.value)
    }


    @Test
    fun `fetchTodos should update todosState when getTodosUseCase returns data`() = runTest {
        val todo1 = Todo(1, "Buy groceries")
        // Given
        val todos = listOf(
            todo1,
            Todo(2, "Clean the house")
        )
        coEvery { getTodosUseCase("") } returns flow { emit(todos) }
        coEvery { getTodosUseCase("groceries") } returns flow { emit(listOf(todo1)) }

        // When
        mainViewModel.fetchTodos("groceries")

        //wait
        advanceUntilIdle()

        // Then
        assertEquals(todo1, mainViewModel.todosState.value.get(0))
        coVerify { getTodosUseCase("") }
    }

    @Test
    fun `onSearchQueryChanged should update search state and fetch todos with query`() = runTest {

        val query = "groceries"
        mainViewModel = spyk( MainViewModel(getTodosUseCase)) //spying MainViewModel

        mainViewModel.onSearchQueryChanged(query)

        assertEquals(query, mainViewModel.searchState.value)
        verify { mainViewModel.fetchTodos(query) }
    }


    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}