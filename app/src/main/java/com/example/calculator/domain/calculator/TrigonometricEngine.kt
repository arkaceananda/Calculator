package com.example.calculator.domain.calculator

import com.example.calculator.model.CalculationResult
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

class TrigonometricEngine {
    private fun degreesToRadians(degrees: Double): Double {
        return degrees * PI / 180.0
    }

    fun calculateSin(degrees: Double): CalculationResult {
        return try {
            val radians = degreesToRadians(degrees)
            val result = sin(radians)
            CalculationResult.Success(result)
        } catch (e: Exception) {
            CalculationResult.Error("Error saat menghitung sin: ${e.message}")
        }
    }

    fun calculateCos(degrees: Double): CalculationResult {
        return try {
            val radians = degreesToRadians(degrees)
            val result = cos(radians)
            CalculationResult.Success(result)
        } catch (e: Exception) {
            CalculationResult.Error("Error saat menghitung cos: ${e.message}")
        }
    }

    fun calculateTan(degrees: Double): CalculationResult {
        return try {
            val radians = degreesToRadians(degrees)
            val cosValue = cos(radians)

            if (abs(cosValue) < 1e-10) {
                CalculationResult.Error("Tan tidak terdefinisi")
            } else {
                val result = tan(radians)
                CalculationResult.Success(result)
            }
        } catch (e: Exception) {
            CalculationResult.Error("Error saat menghitung tan: ${e.message}")
        }
    }
}