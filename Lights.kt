package com.example.Lights

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                Lights()
        }
    }
}

@Composable
fun Lights() {
    val tab = remember { mutableStateListOf(*BooleanArray(9) { false }.toTypedArray()) }
    var start by remember { mutableStateOf(false) }
    var prawo by remember { mutableStateOf(0) }
    var lewo by remember { mutableStateOf(0) }
    var odbite by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
            .padding(top = 5.dp),
        ) {
            for (i in 0..8) {
                Checkbox(
                    checked = tab[i],
                    onCheckedChange = { checked ->
                        tab[i] = checked
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = if (i < 4) Color.Yellow else if (i ==4) Color.Green else Color.Blue,
                        uncheckedColor = Color.Gray,
                        checkmarkColor = Color.Black
                    ),
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(onClick = {
                if (start) {
                    start = false
                } else {
                    start = true
                    lewo = 0;
                    prawo = 8;
                    scope.launch {
                        while (start) {
                            tab[lewo] = true
                            tab[prawo] = true
                            delay(100)
                            tab[prawo] = false
                            tab[lewo] = false
                            if (lewo == 0 && prawo == 8 && odbite == 1) {
                                odbite = 0
                            }
                            if (lewo == 3 && prawo == 5 && odbite == 0) {
                                odbite = 1
                                tab[4] = true
                                delay(100)
                                tab[4] = false
                            }
                            if (odbite == 1) {
                                lewo -= 1
                                prawo += 1
                            } else {
                                lewo += 1
                                prawo -= 1
                            }
                        }
                    }
                }
            }) {
                Text("Start/Stop")
            }
        }
    }
}
