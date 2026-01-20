package com.example.calculator.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator.model.ArithmaticOperation
import com.example.calculator.model.TrigonometricOperation

@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
            .safeDrawingPadding()
            .padding(horizontal = 20.dp)
            .padding(bottom = 32.dp, top = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.End
        ) {
            if (uiState.expressionText.isNotEmpty()) {
                Text(
                    text = uiState.expressionText,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White.copy(alpha = 0.5f),
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    maxLines = 1
                )
            }

            Text(
                text = uiState.displayText,
                fontSize = 64.sp,
                fontWeight = FontWeight.Light,
                color = if (uiState.isError) Color(0xFFFF453A) else Color.White,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 16.dp),
                maxLines = 2,
                lineHeight = 70.sp
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                CalculatorButton(
                    text = "SIN",
                    onClick = { viewModel.onTrigonometricClick(TrigonometricOperation.SIN) },
                    modifier = Modifier.weight(1f),
                    backgroundColor = Color(0xFF505050),
                    textColor = Color.White,
                    fontSize = 22.sp
                )
                CalculatorButton(
                    text = "COS",
                    onClick = { viewModel.onTrigonometricClick(TrigonometricOperation.COS) },
                    modifier = Modifier.weight(1f),
                    backgroundColor = Color(0xFF505050),
                    textColor = Color.White,
                    fontSize = 22.sp
                )
                CalculatorButton(
                    text = "TAN",
                    onClick = { viewModel.onTrigonometricClick(TrigonometricOperation.TAN) },
                    modifier = Modifier.weight(1f),
                    backgroundColor = Color(0xFF505050),
                    textColor = Color.White,
                    fontSize = 22.sp
                )
                CalculatorButton(
                    text = "DEL",
                    onClick = { viewModel.deleteLastDigit() },
                    modifier = Modifier.weight(1f),
                    backgroundColor = Color(0xFFD4D4D2),
                    textColor = Color.Black,
                    fontSize = 22.sp
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                CalculatorButton(
                    text = "(",
                    onClick = { viewModel.onParenthesisClick("(") },
                    modifier = Modifier.weight(1f),
                    backgroundColor = Color(0xFF505050),
                    textColor = Color.White
                )
                CalculatorButton(
                    text = ")",
                    onClick = { viewModel.onParenthesisClick(")") },
                    modifier = Modifier.weight(1f),
                    backgroundColor = Color(0xFF505050),
                    textColor = Color.White
                )
                CalculatorButton(
                    text = "%",
                    onClick = { viewModel.onOperationClick(ArithmaticOperation.MODULO) },
                    modifier = Modifier.weight(1f),
                    backgroundColor = Color(0xFF505050),
                    textColor = Color.White
                )
                CalculatorButton(
                    text = "÷",
                    onClick = { viewModel.onOperationClick(ArithmaticOperation.DIVIDE) },
                    modifier = Modifier.weight(1f),
                    backgroundColor = Color(0xFFFF9F0A),
                    textColor = Color.White
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                CalculatorButton(
                    text = "7",
                    onClick = { viewModel.onNumberClick("7") },
                    modifier = Modifier.weight(1f)
                )
                CalculatorButton(
                    text = "8",
                    onClick = { viewModel.onNumberClick("8") },
                    modifier = Modifier.weight(1f)
                )
                CalculatorButton(
                    text = "9",
                    onClick = { viewModel.onNumberClick("9") },
                    modifier = Modifier.weight(1f)
                )
                CalculatorButton(
                    text = "×",
                    onClick = { viewModel.onOperationClick(ArithmaticOperation.MULTIPLY) },
                    modifier = Modifier.weight(1f),
                    backgroundColor = Color(0xFFFF9F0A),
                    textColor = Color.White
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                CalculatorButton(
                    text = "4",
                    onClick = { viewModel.onNumberClick("4") },
                    modifier = Modifier.weight(1f)
                )
                CalculatorButton(
                    text = "5",
                    onClick = { viewModel.onNumberClick("5") },
                    modifier = Modifier.weight(1f)
                )
                CalculatorButton(
                    text = "6",
                    onClick = { viewModel.onNumberClick("6") },
                    modifier = Modifier.weight(1f)
                )
                CalculatorButton(
                    text = "−",
                    onClick = { viewModel.onOperationClick(ArithmaticOperation.SUBTRACT) },
                    modifier = Modifier.weight(1f),
                    backgroundColor = Color(0xFFFF9F0A),
                    textColor = Color.White
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                CalculatorButton(
                    text = "1",
                    onClick = { viewModel.onNumberClick("1") },
                    modifier = Modifier.weight(1f)
                )
                CalculatorButton(
                    text = "2",
                    onClick = { viewModel.onNumberClick("2") },
                    modifier = Modifier.weight(1f)
                )
                CalculatorButton(
                    text = "3",
                    onClick = { viewModel.onNumberClick("3") },
                    modifier = Modifier.weight(1f)
                )
                CalculatorButton(
                    text = "+",
                    onClick = { viewModel.onOperationClick(ArithmaticOperation.ADD) },
                    modifier = Modifier.weight(1f),
                    backgroundColor = Color(0xFFFF9F0A),
                    textColor = Color.White
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                CalculatorButton(
                    text = "AC",
                    onClick = { viewModel.clearAll() },
                    modifier = Modifier.weight(1f),
                    backgroundColor = Color(0xFFD4D4D2),
                    textColor = Color.Black,
                )
                CalculatorButton(
                    text = "0",
                    onClick = { viewModel.onNumberClick("0") },
                    modifier = Modifier.weight(1f)
                )
                CalculatorButton(
                    text = ".",
                    onClick = { viewModel.onDecimalClick() },
                    modifier = Modifier.weight(1f)
                )
                CalculatorButton(
                    text = "=",
                    onClick = { viewModel.calculateResult() },
                    modifier = Modifier.weight(1f),
                    backgroundColor = Color(0xFFFF9F0A),
                    textColor = Color.White
                )
            }
        }
    }
}

@Composable
fun CalculatorButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xFF333333),
    textColor: Color = Color.White,
    fontSize: androidx.compose.ui.unit.TextUnit = 28.sp
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(75.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = FontWeight.Normal,
            color = textColor
        )
    }
}