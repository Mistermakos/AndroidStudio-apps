package com.example.healthapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.healthapp.ui.theme.HealthAppTheme
import kotlinx.coroutines.time.delay
import java.sql.Time

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HealthAppTheme {
                    Game()
            }
        }
    }
}

@Composable
fun Game() {
    var player1Score by remember { mutableIntStateOf(0) }
    var player2Score by remember { mutableIntStateOf(0) }
    var resultText by remember { mutableStateOf("") }
    var ready by remember { mutableStateOf(false) }
    var player1enable by remember { mutableStateOf(false) }
    var player2enable by remember { mutableStateOf(false) }
    var isWaitingForClick by remember { mutableStateOf(false) }

    // Coroutine to control "ready" state only if not waiting for click
    LaunchedEffect(key1 = isWaitingForClick, key2 = resultText) {
        if (!isWaitingForClick && resultText.isEmpty()) {
            while (!isWaitingForClick && resultText.isEmpty()) {
                val arr = intArrayOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
                if (arr.random() % 2 == 0) {
                    ready = true
                    player1enable = true
                    player2enable = true
                    isWaitingForClick = true  // Stop further randomizing until click
                } else {
                    ready = false
                    player1enable = false
                    player2enable = false
                }
                kotlinx.coroutines.delay(1000)
            }
        }
    }

    fun CheckAndReset() {
        if (player1Score == 3) {
            resultText = "Player 1 Won"
        } else if (player2Score == 3) {
            resultText = "Player 2 Won"
        }

        ready = false
        player1enable = false
        player2enable = false
        isWaitingForClick = false // Start randomizing again
    }

    // UI
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Game Result")

            Text(
                text = if (resultText.isNotEmpty()) resultText else "Nobody's won yet",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = if (resultText.isNotEmpty()) Color(0xFF388E3C) else Color.Gray,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            Box(
                modifier = Modifier
                    .size(100.dp)
                    .border(
                        width = 64.dp,
                        color = if (ready) Color.Green else Color.Red,
                        shape = CircleShape
                    )
                    .padding(16.dp)
            )

            Row(
                modifier = Modifier.padding(top = 24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Player 1")
                    Text("Score: $player1Score")
                    Button(
                        onClick = {
                            player1Score++
                            CheckAndReset()
                        },
                        enabled = player1enable,
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("Push Me")
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text("Player 2")
                    Text("Score: $player2Score")
                    Button(
                        onClick = {
                            player2Score++
                            CheckAndReset()
                        },
                        enabled = player2enable,
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("Push Me")
                    }
                }
            }
        }
    }
}
