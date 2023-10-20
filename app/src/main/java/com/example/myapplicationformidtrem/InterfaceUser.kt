package com.example.myapplicationformidtrem

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class InterfaceUser : ComponentActivity() {
    @Preview(showBackground=true)
    @Composable
    fun GameScreen() {
        var targetValue by remember { mutableStateOf((0..100).random()) }
        var sliderValue by remember { mutableStateOf(50f) }
        var score by remember { mutableStateOf(0) }
        var totalScore by remember { mutableStateOf(0) }
        var feedback by remember { mutableStateOf("") }
        var roundCount by remember { mutableStateOf(1) }
        val maxRounds = 5 // Set the maximum number of rounds here

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Target: $targetValue",
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))

            Slider(
                value = sliderValue,
                onValueChange = { value ->
                    sliderValue = value
                },
                valueRange = 0f..100f,
                steps = 100,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val difference = kotlin.math.abs(targetValue - sliderValue.toInt())
                    when {
                        difference <= 3 -> {
                            score += 5
                            feedback = "Excellent guess! You've earned 5 points."
                        }
                        difference <= 8 -> {
                            score += 1
                            feedback = "Close, but not quite. You've earned 1 point."
                        }
                        else -> {
                            feedback = "Not even close! No points earned."
                        }
                    }

                    if (roundCount < maxRounds) {
                        roundCount++
                        totalScore += score
                        score = 0 // Reset score for the next round
                        targetValue = (0..100).random() // Generate a new random target value
                        sliderValue = 50f // Reset slider to the middle
                    } else {
                        feedback = "Game Over! Your total score: $totalScore"
                    }
                },
                modifier = Modifier.align(CenterHorizontally)
            ) {
                Text(text = "Hit Me!")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Round: $roundCount",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = feedback,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(CenterHorizontally)
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MyApp {
            GameScreen()
        }
    }

    @Composable
    fun MyApp(content: @Composable () -> Unit) {
        MaterialTheme {
            content()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp {
                GameScreen()
            }
        }
    }
}
