package com.example.calculator.ui.theme

import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val CustomLightColors = CalculatorColorScheme(
    background = Color(0xFFF9FAFB),
    surface = Color.White,
    primary = Color(0xFF1F2933),
    accent = Color(0xFFFFF867),
    danger = Color(0xFFFB2F38),
    numberButton = Color(0xFFE0E0E0),
    operatorButton = Color(0xFF2C52B3),
    neutralButton = Color(0xFFBDBDBD),
    textPrimary = Color.Black,
    textSecondary = Color(0xFF666666)
)

val CustomDarkColors = CalculatorColorScheme(
    background = Color(0xFF0B0D10),
    surface = Color(0xFF15181D),
    primary = Color(0xFFE5E7EB),
    accent = Color(0xFF9CA3AF),
    danger = Color(0xFF6B7280),
    numberButton = Color(0xFF1C1F25),
    operatorButton = Color(0xFF2A2E35),
    neutralButton = Color(0xFF262A30),
    textPrimary = Color(0xFFF9FAFB),
    textSecondary = Color(0xFF9CA3AF)
)