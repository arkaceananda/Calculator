package com.example.calculator.model

enum class ArithmaticOperation(val symbol: String) {
    ADD("+"),
    SUBTRACT("−"),
    MULTIPLY("×"),
    DIVIDE("÷"),
    MODULO("%"),
    NONE("")
}

enum class TrigonometricOperation(val symbol: String) {
    SIN("sin"),
    COS("cos"),
    TAN("tan"),
    NONE("")
}