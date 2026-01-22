package com.example.calculator.domain.model

enum class ArithmaticOperation(val symbol: String) {
    ADD("+"),
    SUBTRACT("−"),
    MULTIPLY("×"),
    DIVIDE("÷"),
    MODULO("%"),
    NONE("")
}

enum class TrigonometricOperation(symbol: String) {
    SIN("sin"),
    COS("cos"),
    TAN("tan"),
    NONE("")
}