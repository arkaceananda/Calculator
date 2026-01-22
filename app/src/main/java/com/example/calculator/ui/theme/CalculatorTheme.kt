package com.example.calculator.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class CalculatorColorScheme(
    val background: Color,
    val surface: Color,

    val primary: Color,
    val accent: Color,
    val danger: Color,

    val numberButton: Color,
    val operatorButton: Color,
    val neutralButton: Color,

    val textPrimary: Color,
    val textSecondary: Color
)

val LocalCalculatorColors = staticCompositionLocalOf<CalculatorColorScheme> {
    error("No CalculatorColorScheme provided")
}

object CalculatorTheme {
    val colors: CalculatorColorScheme
        @Composable
        @ReadOnlyComposable
        get() = LocalCalculatorColors.current

    val typography: Typography
        @Composable
        @ReadOnlyComposable
        get() = MaterialTheme.typography
}
