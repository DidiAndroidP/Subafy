package com.subafy.subafy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import com.subafy.subafy.src.core.ui.theme.SubafyTheme
import com.subafy.subafy.src.core.navigate.NavigationWrapper

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SubafyTheme {
                NavigationWrapper()
            }
        }
    }
}