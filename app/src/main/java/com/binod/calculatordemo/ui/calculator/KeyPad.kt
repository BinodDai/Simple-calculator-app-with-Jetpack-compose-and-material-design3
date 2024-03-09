package com.binod.calculatordemo.ui.calculator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.binod.calculatordemo.ui.calculator.components.Key
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun KeyPad(onIntent: (CalculatorIntent) -> Unit, hasDataToClear: () -> Boolean) {
    val buttons = listOf(
        "1", "2", "3", "4", "5", "6", "7", "8", "9", "Delete", "0", "Clear"
    )
    val snackBarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Column {
        buttons.chunked(3).forEach { row ->
            Row(modifier = Modifier.fillMaxWidth()) {
                row.forEach { button ->
                    Key(button, modifier = Modifier.weight(1f)) {
                        handleButtonAction(
                            button,
                            hasDataToClear,
                            snackBarHostState,
                            scope,
                            onIntent
                        )
                    }
                }
            }
        }
        SnackbarHost(hostState = snackBarHostState)
    }
}

private fun handleButtonAction(
    button: String,
    hasDataToClear: () -> Boolean,
    snackBarHostState: SnackbarHostState,
    scope: CoroutineScope,
    onIntent: (CalculatorIntent) -> Unit
) {
    scope.launch {
        when (button) {
            "Delete", "Clear" -> if (hasDataToClear()) {
                if (button == "Clear") {
                    val result = snackBarHostState.showSnackbar(
                        message = "Confirm clear all?",
                        actionLabel = "Confirm"
                    )
                    if (result == SnackbarResult.ActionPerformed) {
                        onIntent(CalculatorIntent.Clear)
                    }
                } else {
                    onIntent(CalculatorIntent.Delete)
                }
            } else {
                snackBarHostState.showSnackbar(message = "No data to clear.")
            }
            else -> onIntent(CalculatorIntent.Number(button))
        }
    }
}