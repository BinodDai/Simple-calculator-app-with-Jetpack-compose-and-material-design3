package com.binod.calculatordemo.ui.calculator

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.binod.calculatordemo.ui.calculator.model.CalculatorState

class CalculatorViewModel : ViewModel() {
    private val _state = mutableStateOf(CalculatorState())
    val state: State<CalculatorState> = _state

    fun dispatch(intent: CalculatorIntent) {
        when (intent) {
            is CalculatorIntent.Number -> _state.value =
                state.value.copy(display = state.value.display + intent.value)
            is CalculatorIntent.Delete -> _state.value =
                state.value.copy(display = state.value.display.dropLast(1))
            is CalculatorIntent.Clear -> _state.value = CalculatorState()
        }
    }
}

sealed class CalculatorIntent {
    data class Number(val value: String) : CalculatorIntent()
    object Delete : CalculatorIntent()
    object Clear : CalculatorIntent()
}