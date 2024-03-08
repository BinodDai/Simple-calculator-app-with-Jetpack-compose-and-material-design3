package com.binod.calculatordemo.ui.calculator

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.binod.calculatordemo.ui.calculator.components.Key

@Composable
fun KeyPad(onIntent: (CalculatorIntent) -> Unit) {
    val buttons = listOf(
        "1", "2", "3",
        "4", "5", "6",
        "7", "8", "9",
        "Delete", "0", "Clear"
    )

    Column {
        for (row in buttons.chunked(3)) {
            Row(
                modifier = Modifier.fillMaxWidth()
            )
            {
                row.forEach { button ->
                    Key(button, modifier = Modifier.weight(1f))
                    {
                        when (button) {
                            "Delete" -> onIntent(CalculatorIntent.Delete)
                            "Clear" -> onIntent(CalculatorIntent.Clear)
                            else -> onIntent(CalculatorIntent.Number(button))
                        }
                    }
                }
            }
        }
    }
}