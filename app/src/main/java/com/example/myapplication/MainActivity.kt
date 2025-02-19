package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.AppNavHost
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.DataViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // Inisialisasi ViewModel
                val dataViewModel: DataViewModel = viewModel()
                // Menampilkan Navigation Host
                AppNavHost(viewModel = dataViewModel)
            }
        }
    }
}
