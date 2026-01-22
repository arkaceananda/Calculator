package com.example.calculator.domain.model

sealed class CalculationResult {
    data class Success(val value: Double) : CalculationResult()
    data class Error(val message: String) : CalculationResult()
}