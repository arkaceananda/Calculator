package com.example.calculator.presentation

import androidx.compose.animation.*
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator.R
import com.example.calculator.domain.model.ArithmaticOperation
import com.example.calculator.domain.model.HistoryItem
import com.example.calculator.domain.model.TrigonometricOperation
import com.example.calculator.ui.theme.CalculatorTheme
import com.example.calculator.ui.theme.Typography
import java.text.SimpleDateFormat
import java.util.*


@Composable
fun CalculatorScreen(
    viewModel: CalculatorViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val colors = CalculatorTheme.colors
    
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
    ) {
        AnimatedVisibility(
            visible = !uiState.isHistoryExpanded,
            enter = fadeIn() + slideInVertically(initialOffsetY = { -it }),
            exit = fadeOut() + slideOutVertically(targetOffsetY = { -it })
        ) {
            CalculatorMainScreen(uiState, viewModel)
        }
        AnimatedVisibility(
            visible = uiState.isHistoryExpanded,
            enter = fadeIn(initialAlpha = 0.3f) + expandVertically(),
            exit = fadeOut(animationSpec = tween(durationMillis = 500)) + shrinkOut()
        ) {
            FullHistoryScreen(
                history = uiState.history,
                onMinimize = { viewModel.toggleHistoryExpanded() },
                onClearHistory = { viewModel.clearHistory() },
                onHistoryItemClick = { viewModel.onHistoryItemClick(it) }
            )
        }
    }
}

@Composable
fun CalculatorMainScreen(
    uiState: CalculatorUiState,
    viewModel: CalculatorViewModel
) {
    val colors = CalculatorTheme.colors
    val typography = CalculatorTheme.typography

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(horizontal = 20.dp)
            .padding(bottom = 32.dp, top = 16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = { viewModel.toggleHistoryExpanded() }) {
                Icon(
                    painter = painterResource(id = R.drawable.history),
                    contentDescription = "History",
                    tint = colors.primary,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
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
                    style = typography.bodyLarge.copy(
                        fontSize = 32.sp,
                        color = colors.textSecondary,
                        textAlign = TextAlign.End
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    maxLines = 1
                )
            }
            Text(
                text = uiState.displayText,
                style = typography.displayLarge.copy(
                    fontSize = 64.sp,
                    fontWeight = FontWeight.Light,
                    color = if (uiState.isError) colors.danger else colors.textPrimary,
                    textAlign = TextAlign.End
                ),
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
                    backgroundColor = colors.accent,
                    textColor = Color.Black,
                    fontSize = 22.sp,
                    textStyle = Typography.bodyLarge
                )
                CalculatorButton(
                    text = "COS",
                    onClick = { viewModel.onTrigonometricClick(TrigonometricOperation.COS) },
                    modifier = Modifier.weight(1f),
                    backgroundColor = colors.accent,
                    textColor = Color.Black,
                    textStyle = Typography.bodyLarge,
                    fontSize = 22.sp
                )
                CalculatorButton(
                    text = "TAN",
                    onClick = { viewModel.onTrigonometricClick(TrigonometricOperation.TAN) },
                    modifier = Modifier.weight(1f),
                    backgroundColor = colors.accent,
                    textColor = Color.Black,
                    textStyle = Typography.bodyLarge,
                    fontSize = 22.sp
                )
                CalculatorButton(
                    text = "DEL",
                    onClick = { viewModel.deleteLastDigit() },
                    modifier = Modifier.weight(1f),
                    backgroundColor = colors.danger,
                    textColor = colors.textPrimary,
                    textStyle = Typography.bodyLarge,
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
                    backgroundColor = colors.neutralButton,
                    textColor = colors.textPrimary
                )
                CalculatorButton(
                    text = ")",
                    onClick = { viewModel.onParenthesisClick(")") },
                    modifier = Modifier.weight(1f),
                    backgroundColor = colors.neutralButton,
                    textColor = colors.textPrimary
                )
                CalculatorButton(
                    text = "%",
                    onClick = { viewModel.onOperationClick(ArithmaticOperation.MODULO) },
                    modifier = Modifier.weight(1f),
                    backgroundColor = colors.neutralButton,
                    textColor = colors.textPrimary
                )
                CalculatorButton(
                    text = "÷",
                    onClick = { viewModel.onOperationClick(ArithmaticOperation.DIVIDE) },
                    modifier = Modifier.weight(1f),
                    backgroundColor = colors.operatorButton,
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
                    modifier = Modifier.weight(1f),
                    backgroundColor = colors.numberButton,
                    textColor = colors.textPrimary
                )
                CalculatorButton(
                    text = "8",
                    onClick = { viewModel.onNumberClick("8") },
                    modifier = Modifier.weight(1f),
                    backgroundColor = colors.numberButton,
                    textColor = colors.textPrimary
                )
                CalculatorButton(
                    text = "9",
                    onClick = { viewModel.onNumberClick("9") },
                    modifier = Modifier.weight(1f),
                    backgroundColor = colors.numberButton,
                    textColor = colors.textPrimary
                )
                CalculatorButton(
                    text = "×",
                    onClick = { viewModel.onOperationClick(ArithmaticOperation.MULTIPLY) },
                    modifier = Modifier.weight(1f),
                    backgroundColor = colors.operatorButton,
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
                    modifier = Modifier.weight(1f),
                    backgroundColor = colors.numberButton,
                    textColor = colors.textPrimary
                )
                CalculatorButton(
                    text = "5",
                    onClick = { viewModel.onNumberClick("5") },
                    modifier = Modifier.weight(1f),
                    backgroundColor = colors.numberButton,
                    textColor = colors.textPrimary
                )
                CalculatorButton(
                    text = "6",
                    onClick = { viewModel.onNumberClick("6") },
                    modifier = Modifier.weight(1f),
                    backgroundColor = colors.numberButton,
                    textColor = colors.textPrimary
                )
                CalculatorButton(
                    text = "−",
                    onClick = { viewModel.onOperationClick(ArithmaticOperation.SUBTRACT) },
                    modifier = Modifier.weight(1f),
                    backgroundColor = colors.operatorButton,
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
                    modifier = Modifier.weight(1f),
                    backgroundColor = colors.numberButton,
                    textColor = colors.textPrimary
                )
                CalculatorButton(
                    text = "2",
                    onClick = { viewModel.onNumberClick("2") },
                    modifier = Modifier.weight(1f),
                    backgroundColor = colors.numberButton,
                    textColor = colors.textPrimary
                )
                CalculatorButton(
                    text = "3",
                    onClick = { viewModel.onNumberClick("3") },
                    modifier = Modifier.weight(1f),
                    backgroundColor = colors.numberButton,
                    textColor = colors.textPrimary
                )
                CalculatorButton(
                    text = "+",
                    onClick = { viewModel.onOperationClick(ArithmaticOperation.ADD) },
                    modifier = Modifier.weight(1f),
                    backgroundColor = colors.operatorButton,
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
                    backgroundColor = colors.danger,
                    textColor = colors.textPrimary,
                    textStyle = Typography.bodyLarge
                )
                CalculatorButton(
                    text = "0",
                    onClick = { viewModel.onNumberClick("0") },
                    modifier = Modifier.weight(1f),
                    backgroundColor = colors.numberButton,
                    textColor = colors.textPrimary
                )
                CalculatorButton(
                    text = ".",
                    onClick = { viewModel.onDecimalClick() },
                    modifier = Modifier.weight(1f),
                    backgroundColor = colors.numberButton,
                    textColor = colors.textPrimary
                )
                CalculatorButton(
                    text = "=",
                    onClick = { viewModel.calculateResult() },
                    modifier = Modifier.weight(1f),
                    backgroundColor = colors.operatorButton,
                    textColor = Color.White
                )
            }
        }
    }
}

@Composable
fun FullHistoryScreen(
    history: List<HistoryItem>,
    onMinimize: () -> Unit,
    onClearHistory: () -> Unit,
    onHistoryItemClick: (HistoryItem) -> Unit
) {
    val colors = CalculatorTheme.colors
    val typography = CalculatorTheme.typography

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .safeDrawingPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "History",
                style = typography.displayLarge.copy(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary
                )
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                IconButton(
                    onClick = onClearHistory,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(colors.neutralButton)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.trash),
                        contentDescription = "Clear History",
                        tint = colors.textPrimary,
                        modifier = Modifier.size(30.dp)
                    )
                }
                IconButton(
                    onClick = onMinimize,
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(colors.primary)
                ) {
                    Text(
                        text = "−",
                        fontSize = 24.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        HorizontalDivider(color = colors.textSecondary.copy(alpha = 0.1f))

        if (history.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No history yet",
                    style = typography.bodyLarge.copy(
                        color = colors.textSecondary.copy(alpha = 0.4f)
                    )
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(history) { item ->
                    HistoryItemCard(
                        item = item,
                        onClick = { onHistoryItemClick(item) }
                    )
                }
            }
        }
    }
}

@Composable
fun HistoryItemCard(
    item: HistoryItem,
    onClick: () -> Unit
) {
    val colors = CalculatorTheme.colors
    val typography = CalculatorTheme.typography
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val timeString = dateFormat.format(Date(item.timestamp))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = colors.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.expression,
                    style = typography.bodyLarge.copy(
                        fontSize = 16.sp,
                        color = colors.textSecondary
                    ),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = timeString,
                    style = typography.bodyLarge.copy(
                        fontSize = 12.sp,
                        color = colors.textSecondary.copy(alpha = 0.5f)
                    )
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "= ${item.result}",
                style = typography.bodyLarge.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Medium,
                    color = colors.textPrimary
                )
            )
        }
    }
}

@Composable
fun CalculatorButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.secondary,
    textColor: Color = MaterialTheme.colorScheme.onSecondary,
    textStyle: TextStyle = CalculatorTheme.typography.bodyLarge,
    fontSize: TextUnit = 28.sp
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val haptic = LocalHapticFeedback.current
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.92f else 1f,
        label = "press-scale"
    )

    Button(
        onClick = {
            haptic.performHapticFeedback(HapticFeedbackType.TextHandleMove)
            onClick()
        },
        interactionSource = interactionSource,
        modifier = modifier
            .height(75.dp)
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale
            ),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp,
            pressedElevation = 1.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor
        ),
        shape = CircleShape,
        contentPadding = PaddingValues(0.dp)
    ) {
        Text(
            text = text,
            style = textStyle.copy(
                fontSize = fontSize,
                fontWeight = FontWeight.Medium,
                color = textColor
            )
        )
    }
}
