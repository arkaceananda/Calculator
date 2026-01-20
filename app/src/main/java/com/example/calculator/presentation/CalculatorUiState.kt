package com.example.calculator.presentation

import com.example.calculator.model.ArithmaticOperation
import com.example.calculator.model.HistoryItem
import com.example.calculator.model.TrigonometricOperation

data class CalculatorUiState(
    val displayText: String = "0",
    val expressionText: String = "",
    val currentInput: String = "",
    val previousValue: Double? = null,
    val currentOperation: ArithmaticOperation = ArithmaticOperation.NONE,
    val currentTrigOperation: TrigonometricOperation = TrigonometricOperation.NONE,
    val waitingForSecondOperand: Boolean = false,
    val isError: Boolean = false,
    val showResult: Boolean = false,
    val history: List<HistoryItem> = emptyList(),
    val isHistoryExpanded: Boolean = false
)
