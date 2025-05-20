package com.example.healthapp

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthapp.ui.theme.HealthAppTheme
import kotlinx.coroutines.time.delay
import java.sql.Time

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HealthAppTheme {
                    Form()
            }
        }
    }
}


@Composable
fun Form() {
    var sent by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(false) }
    var levelOfActivity by remember { mutableIntStateOf(0) }
    var progress by remember { mutableFloatStateOf(0f) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
    ) {
        // Name Input
        Text("Put your name:")
        TextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.fillMaxWidth()
        )

        // Checkbox if you're active
        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            Text("Are you active")
            Checkbox(
                checked = active,
                onCheckedChange = { active = it }
            )
        }

        // Radio Buttons
        Text("Degree of activity:")
        Row {
            listOf("Low", "Average", "High").forEachIndexed { index, label ->
                Column(horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
                    RadioButton(
                        selected = levelOfActivity == index,
                        onClick = { levelOfActivity = index }
                    )
                    Text(label)
                }
            }
        }

        // Button for sending
        Button(onClick = { sent = true }) {
            Text("Send the form")
        }

        // Show progres bar
        if (sent) {
            if(progress != 1f) {
                LaunchedEffect(Unit) {
                    val duration = 2000
                    val steps = 100
                    val delayPerStep = duration / steps
                    for (i in 1..steps) {
                        progress = i / steps.toFloat()
                        kotlinx.coroutines.delay(delayPerStep.toLong())
                    }
                }
                LinearProgressIndicator(
                    progress = { progress },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                )

            }
            if(progress == 1f){
                var level:String = "";
                if(active == true){
                    if(levelOfActivity == 1){
                        level = "Nice"
                    }
                    if(levelOfActivity == 2){
                        level = "Amazing"
                    }
                }
                if(active == false){
                    if(levelOfActivity == 0){
                        level = "Low"
                    }
                }
                else{
                    level = "Average"
                }

                Text("Hi $name! Your level of health is: ${level}",
                    modifier = Modifier.padding(top = 8.dp))
            }
        }
    }
}

