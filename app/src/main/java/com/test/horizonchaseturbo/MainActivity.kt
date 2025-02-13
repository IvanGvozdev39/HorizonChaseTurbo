package com.test.horizonchaseturbo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.test.horizonchaseturbo.presentation.screens.StartScreen
import com.test.horizonchaseturbo.ui.theme.HorizonChaseTurboTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HorizonChaseTurboTheme {
                StartScreen()
            }
        }
    }
}