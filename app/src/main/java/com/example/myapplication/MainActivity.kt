package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.navigation.AppNavHost
import com.example.myapplication.ui.theme.MyApplicationTheme
import com.example.myapplication.viewmodel.DataViewModel
import com.example.myapplication.viewmodel.ProfileViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: DataViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme  {
                Main(viewModel = viewModel,
                    profileViewModel = profileViewModel
                )
            }
        }
    }
}
