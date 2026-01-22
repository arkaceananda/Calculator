package com.example.calculator.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.calculator.ui.theme.CalculatorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDark = isSystemInDarkTheme()
            LaunchedEffect(isDark) {
                enableEdgeToEdge(
                    statusBarStyle = if (!isDark) {
                        SystemBarStyle.light(
                            android.graphics.Color.TRANSPARENT,
                            android.graphics.Color.TRANSPARENT
                        )
                    } else {
                        SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
                    },
                    navigationBarStyle = if (!isDark) {
                        SystemBarStyle.light(
                            android.graphics.Color.TRANSPARENT,
                            android.graphics.Color.TRANSPARENT
                        )
                    } else {
                        SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
                    }
                )
            }
            CalculatorTheme (darkTheme = isDark){
                CalculatorScreen()
            }
        }
    }
}