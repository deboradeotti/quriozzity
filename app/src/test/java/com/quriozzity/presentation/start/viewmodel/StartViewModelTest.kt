package com.quriozzity.presentation.start.viewmodel

import app.cash.turbine.test
import com.quriozzity.presentation.start.action.StartAction
import com.quriozzity.presentation.start.effect.StartUiEffect
import com.quriozzity.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class StartViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var sut: StartViewModel

    @Before
    fun setUp() {
        sut = StartViewModel()
    }

    @Test
    fun `GIVEN OnClickStart action WHEN sendAction is called THEN it should emit NavigateToQuiz effect`() = runTest {
        sut.uiEffect.test {
            sut.sendAction(StartAction.Action.OnClickStart)

            assertEquals(StartUiEffect.NavigateToQuiz, awaitItem())
        }
    }

    @Test
    fun `GIVEN OnClickAbout action WHEN sendAction is called THEN it should emit NavigateToAbout effect`() = runTest {
        sut.uiEffect.test {
            sut.sendAction(StartAction.Action.OnClickAbout)

            assertEquals(StartUiEffect.NavigateToAbout, awaitItem())
        }
    }
}