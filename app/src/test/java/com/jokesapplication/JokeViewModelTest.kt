package com.jokesapplication

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jokesapplication.data.repo.JokesRepo
import com.jokesapplication.domain.preferences.PreferencesProvider
import com.jokesapplication.presentation.DispatcherProvider
import com.jokesapplication.presentation.remote.JokeViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineExceptionHandler
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class JokeViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val testDispatcher = TestCoroutineDispatcher()
    private val testErrorHandler = TestCoroutineExceptionHandler()
    @Mock
    lateinit var prefs: PreferencesProvider
    @Mock
    lateinit var repo: JokesRepo

    private val dispatcherProvider = object : DispatcherProvider {

        override fun io(): CoroutineDispatcher = testDispatcher

        override fun main(): CoroutineDispatcher = testDispatcher

        override fun computation(): CoroutineDispatcher = testDispatcher

    }

    private lateinit var viewModel: JokeViewModel

    @Test
    fun `test online loading`() {
        val first = null
        val last = null
        Mockito.`when`(prefs.isOffline()).thenReturn(false)
        Mockito.`when`(prefs.getFirstName()).thenReturn(first)
        Mockito.`when`(prefs.getLastName()).thenReturn(last)
        viewModel = JokeViewModel(repo, prefs, dispatcherProvider)
        runBlocking {
            Mockito.verify(repo).getJokes(first, last)
        }
    }

    @Test
    fun `test offline loading`() {
        Mockito.`when`(prefs.isOffline()).thenReturn(true)
        viewModel = JokeViewModel(repo, prefs, dispatcherProvider)
        runBlocking {
            Mockito.verifyZeroInteractions(repo)
        }
    }

    @Test
    fun `test offline shake`() {
        Mockito.`when`(prefs.isOffline()).thenReturn(true)
        viewModel = JokeViewModel(repo, prefs, dispatcherProvider)
        viewModel.random()
        runBlocking {
            Mockito.verify(repo).getRandomJoke()
        }
    }

    @Test
    fun `test online shake`() {
        Mockito.`when`(prefs.isOffline()).thenReturn(false)
        viewModel = JokeViewModel(repo, prefs, dispatcherProvider)
        viewModel.random()
        runBlocking {
            Mockito.verify(repo, Mockito.never()).getRandomJoke()
        }
    }

    @After
    fun cleanup() {
        testDispatcher.cleanupTestCoroutines()
        testErrorHandler.cleanupTestCoroutines()
    }

}
