package com.example.calculator.domain.calculator

import com.example.calculator.domain.model.CalculationResult

class ArithmeticEngine {
    fun add(num1: Double, num2: Double): CalculationResult {
        return try {
            CalculationResult.Success(num1 + num2)
        } catch (e: Exception) {
            CalculationResult.Error("Error saat penjumlahan: ${e.message}")
        }
    }

    fun subtract(num1: Double, num2: Double): CalculationResult {
        return try {
            CalculationResult.Success(num1 - num2)
        } catch (e: Exception) {
            CalculationResult.Error("Error saat pengurangan: ${e.message}")
        }
    }

    fun multiply(num1: Double, num2: Double): CalculationResult {
        return try {
            CalculationResult.Success(num1 * num2)
        } catch (e: Exception) {
            CalculationResult.Error("Error saat perkalian: ${e.message}")
        }
    }

    fun divide(num1: Double, num2: Double): CalculationResult {
        return try {
            if (num2 == 0.0) {
                CalculationResult.Error("Tidak dapat membagi dengan nol")
            } else {
                CalculationResult.Success(num1 / num2)
            }
        } catch (e: Exception) {
            CalculationResult.Error("Error saat pembagian: ${e.message}")
        }
    }

    fun modulo(num1: Double, num2: Double): CalculationResult {
        return try {
            if (num2 == 0.0) {
                CalculationResult.Error("Tidak dapat modulo dengan nol")
            } else {
                CalculationResult.Success(num1 % num2)
            }
        } catch (e: Exception) {
            CalculationResult.Error("Error saat modulo: ${e.message}")
        }
    }
}