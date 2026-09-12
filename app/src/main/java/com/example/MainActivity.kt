package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.ui.navigation.VolunteerConnectApp
import com.example.ui.theme.VolunteerConnectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VolunteerConnectTheme {
                VolunteerConnectApp()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(text = "Volunteer Connect", modifier = modifier)
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VolunteerConnectTheme {
        VolunteerConnectApp()
    }
}
