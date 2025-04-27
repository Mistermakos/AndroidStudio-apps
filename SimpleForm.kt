package com.example.SipleForm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Form()
        }
    }
}

@Composable
fun Form(name: String, modifier: Modifier = Modifier) {

    val radioOptions = listOf("Blue", "Red", "Green")
    var Liking by remember { mutableStateOf(false) }
    var FirstName by remember { mutableStateOf("") }
    var Colour by remember { mutableStateOf("") }
    var Sent by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (!Sent) {
                Text("Give your name:")
                TextField(
                    value = FirstName,
                    onValueChange = { FirstName = it },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Do you like watching nature?")
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(
                        checked = Liking,
                        onCheckedChange = { Liking = it }
                    )
                    Text("You like nature?")
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text("Choose your prefered colour:")
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    for (radioOption in radioOptions) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = Colour == radioOption,
                                onClick = { Colour = radioOption }
                            )
                            Text(radioOption)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { Sent = true }) {
                    Text("Send the form!")
                }
            } else {
                Text("Name: $FirstName")
                Text("Favorite colour: $Colour")
                Text("Do you like nature? ${if (Liking) "Yes" else "No"}")

                Spacer(modifier = Modifier.height(16.dp))

                var imageRes = 0

		//You need to add 3 photos to drawable folder for the code to work properly.
		//eg. Red.jpg, Green.jpg, Blue.jpg
                if (Colour == "Blue") {
                    imageRes = R.drawable.Blue
                } else if (Colour == "Red") {
                    imageRes = R.drawable.Red
                } else if (Colour == "Green") {
                    imageRes = R.drawable.Green
                }

                if (imageRes != 0) {
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = "Colour $Colour",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp),
                        contentScale = ContentScale.Crop
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { Sent = false }) {
                    Text("Go back to form!")
                }
            }
        }
    }
}
