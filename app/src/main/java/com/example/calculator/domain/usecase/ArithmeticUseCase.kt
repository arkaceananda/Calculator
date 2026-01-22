package com.example.calculator.domain.usecase

import com.example.calculator.domain.model.CalculationResult
import com.example.calculator.domain.model.ArithmaticOperation
import com.example.calculator.domain.calculator.ArithmeticEngine

class ArithmeticUseCase(private val calculator: ArithmeticEngine = ArithmeticEngine()) {
    fun execute(num1: Double, num2: Double, operation: ArithmaticOperation): CalculationResult {
        return when (operation) {
            ArithmaticOperation.ADD -> calculator.add(num1, num2)
            ArithmaticOperation.SUBTRACT -> calculator.subtract(num1, num2)
            ArithmaticOperation.MULTIPLY -> calculator.multiply(num1, num2)
            ArithmaticOperation.DIVIDE -> calculator.divide(num1, num2)
            ArithmaticOperation.MODULO -> calculator.modulo(num1, num2)
            ArithmaticOperation.NONE -> CalculationResult.Error("Pilih Operasi")
        }
    }
}