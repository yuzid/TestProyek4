package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.navigation.AppNavHost
import com.example.myapplication.ui.theme.JetsnackTheme
import com.example.myapplication.viewmodel.DataViewModel
import com.example.myapplication.viewmodel.ProfileViewModel
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    private val dataViewModel: DataViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppWithSplashScreen(dataViewModel, profileViewModel)
        }
    }
}

@Composable
fun AppWithSplashScreen(dataViewModel: DataViewModel, profileViewModel: ProfileViewModel) {
    var showSplash by remember { mutableStateOf(true) }

    // Timer untuk splash screen
    LaunchedEffect(Unit) {
        delay(2000) // Splash screen 2 detik
        showSplash = false
    }

    if (showSplash) {
        SplashScreen()
    } else {
        JetsnackTheme {
            Main(viewModel = dataViewModel, profileViewModel = profileViewModel)
        }
    }
}

@Composable
fun SplashScreen() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Color.Red
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.mipmap.splash_logo),
                contentDescription = "Splash Logo",
                modifier = Modifier.size(150.dp)
            )
        }
    }
}
