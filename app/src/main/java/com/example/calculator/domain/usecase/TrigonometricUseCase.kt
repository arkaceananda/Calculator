package com.example.calculator.usecase

import com.example.calculator.model.CalculationResult
import com.example.calculator.model.TrigonometricOperation
import com.example.calculator.domain.calculator.TrigonometricEngine

class TrigonometricUseCase (private val calculator: TrigonometricEngine = TrigonometricEngine()) {
    fun execute(degress: Double, operation: TrigonometricOperation): CalculationResult {
        return when (operation) {
            TrigonometricOperation.SIN -> calculator.calculateSin(degress)
            TrigonometricOperation.COS -> calculator.calculateCos(degress)
            TrigonometricOperation.TAN -> calculator.calculateTan(degress)
            TrigonometricOperation.NONE -> CalculationResult.Error("Pilih Operasi")
        }
    }
}