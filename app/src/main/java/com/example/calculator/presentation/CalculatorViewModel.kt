package com.example.calculator.presentation

import androidx.lifecycle.ViewModel
import com.example.calculator.domain.model.ArithmaticOperation
import com.example.calculator.domain.model.CalculationResult
import com.example.calculator.domain.model.HistoryItem
import com.example.calculator.domain.model.TrigonometricOperation
import com.example.calculator.domain.usecase.ArithmeticUseCase
import com.example.calculator.domain.usecase.TrigonometricUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import net.objecthunter.exp4j.ExpressionBuilder
import java.util.Locale

class CalculatorViewModel(
    private val trigonometricUseCase: TrigonometricUseCase = TrigonometricUseCase(),
    private val arithmeticUseCase: ArithmeticUseCase = ArithmeticUseCase()
) : ViewModel() {

    private val _uiState = MutableStateFlow(CalculatorUiState())
    val uiState: StateFlow<CalculatorUiState> = _uiState.asStateFlow()

    fun onNumberClick(number: String) {
        if (_uiState.value.isError || _uiState.value.showResult) {
            _uiState.update { 
                CalculatorUiState(
                    currentInput = number,
                    displayText = number,
                    showResult = false,
                    history = it.history
                )
            }
            return
        }
        
        _uiState.update { currentState ->
            val newInput = if (currentState.currentInput == "0") number else currentState.currentInput + number
            currentState.copy(
                currentInput = newInput,
                displayText = newInput
            )
        }
    }
    fun onOperationClick(operation: ArithmaticOperation) {
        if (_uiState.value.isError) return

        _uiState.update { currentState ->
            val baseInput = currentState.currentInput
            
            val (newExpression, newInput) = when {
                currentState.showResult -> {
                    ("${currentState.currentInput} ${operation.symbol} " to "0")
                }
                baseInput != "0" -> {
                    ("${currentState.expressionText}${baseInput} ${operation.symbol} " to "0")
                }
                currentState.expressionText.isNotEmpty() -> {
                    val cleanExpression = currentState.expressionText.trimEnd()
                        .replace(Regex("[x÷+\\-−× ]+$"), "")
                    ("$cleanExpression ${operation.symbol} " to "0")
                }
                else -> {
                    ("0 ${operation.symbol} " to "0")
                }
            }

            currentState.copy(
                expressionText = newExpression,
                currentInput = newInput,
                displayText = newInput,
                showResult = false
            )
        }
    }

    fun calculateResult() {
        val currentState = _uiState.value
        if (currentState.showResult || currentState.isError) return

        val inputPart = if (currentState.currentInput == "0" && currentState.expressionText.isNotEmpty()) "" else currentState.currentInput
        var rawExpression = (currentState.expressionText + inputPart).trim()

        if (rawExpression.isEmpty()) return
        rawExpression = rawExpression.replace(Regex("[x÷+\\-−× ]+$"), "")
        
        if (rawExpression.isEmpty()) return

        try {
            val finalExpression = prepareExpression(rawExpression)
            val resultValue = ExpressionBuilder(finalExpression).build().evaluate()
            
            val formattedResult = formatResult(resultValue)
            addToHistory(rawExpression, formattedResult)
            arithmeticUseCase.execute(resultValue, 0.0, ArithmaticOperation.NONE)

            _uiState.update {
                it.copy(
                    expressionText = rawExpression,
                    currentInput = formattedResult,
                    displayText = formattedResult,
                    showResult = true,
                    isError = false
                )
            }
        } catch (e: Exception) {
            _uiState.update {
                it.copy(
                    displayText = "Error",
                    isError = true,
                    showResult = true
                )
            }
        }
    }

    private fun prepareExpression(expr: String): String {
        return expr
            .replace("x", "*")
            .replace("×", "*")
            .replace("÷", "/")
            .replace("−", "-")
            .replace("-", "-")
            .replace("%", "/100")
            .replace(Regex("(\\))(\\d)"), "$1*$2")
            .replace(Regex("(\\d)(\\()"), "$1*$2")
            .replace(")(", ")*(")
            .replace(" ", "")
    }

    private fun formatResult(value: Double): String {
        return if (value % 1.0 == 0.0) value.toLong().toString()
        else {
            val formatted = String.format(Locale.US, "%.8f", value).trimEnd('0').trimEnd('.')
            if (formatted.length > 12) String.format(Locale.US, "%.6e", value) else formatted
        }
    }

    fun onTrigonometricClick(operation: TrigonometricOperation) {
        val angleText = _uiState.value.currentInput
        val angle = angleText.toDoubleOrNull() ?: return
        val result = trigonometricUseCase.execute(angle, operation)
        if (result is CalculationResult.Success) {
            val formatted = formatResult(result.value)
            
            addToHistory("${operation.name}($angleText)", formatted)

            _uiState.update { it.copy(displayText = formatted, currentInput = formatted, showResult = true) }
        }
    }

    fun clearAll() { _uiState.update { currentState -> CalculatorUiState(history = currentState.history) } }

    fun deleteLastDigit() {
        if (_uiState.value.showResult) { clearAll(); return }
        _uiState.update { currentState ->
            if (currentState.currentInput.length > 1) {
                val newInput = currentState.currentInput.dropLast(1)
                currentState.copy(currentInput = newInput, displayText = newInput)
            } else {
                currentState.copy(currentInput = "0", displayText = "0")
            }
        }
    }

    fun onDecimalClick() {
        if (_uiState.value.showResult) {
            _uiState.update { currentState -> CalculatorUiState(currentInput = "0.", displayText = "0.", history = currentState.history) }
            return
        }
        _uiState.update { currentState ->
            if (!currentState.currentInput.contains(".")) {
                val newInput = currentState.currentInput + "."
                currentState.copy(currentInput = newInput, displayText = newInput)
            } else currentState
        }
    }

    fun onParenthesisClick(parenthesis: String) {
        _uiState.update { currentState ->
            val currentNumber = if (currentState.currentInput != "0") currentState.currentInput else ""
            var prefix = ""
            if (parenthesis == "(" && (currentNumber.isNotEmpty() || currentState.expressionText.trim().endsWith(")"))) {
                prefix = "x"
            }
            currentState.copy(
                expressionText = currentState.expressionText + currentNumber + prefix + parenthesis,
                currentInput = "0", displayText = "0", showResult = false
            )
        }
    }
    
    fun toggleHistoryExpanded() {
        _uiState.update { it.copy(isHistoryExpanded = !it.isHistoryExpanded) }
    }
    
    fun clearHistory() {
        _uiState.update { it.copy(history = emptyList()) }
    }
    
    fun onHistoryItemClick(item: HistoryItem) {
        _uiState.update { 
            it.copy(
                currentInput = item.result,
                displayText = item.result,
                expressionText = "",
                showResult = false,
                isHistoryExpanded = false
            )
        }
    }
    
    private fun addToHistory(expression: String, result: String) {
        val newItem = HistoryItem(
            expression = expression,
            result = result
        )
        _uiState.update { it.copy(history = listOf(newItem) + it.history) }
    }
}
