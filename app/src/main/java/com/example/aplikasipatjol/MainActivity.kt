package com.example.aplikasipatjol

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.aplikasipatjol.data.repository.MockSmsRepositoryImpl
import com.example.aplikasipatjol.domain.usecase.GetInboxMessagesUseCase
import com.example.aplikasipatjol.domain.usecase.GetSpamMessagesUseCase
import com.example.aplikasipatjol.domain.usecase.ScanMessagesUseCase
import com.example.aplikasipatjol.presentation.ui.screens.MainScreen
import com.example.aplikasipatjol.presentation.viewmodel.SmsViewModel
import com.example.aplikasipatjol.presentation.viewmodel.SmsViewModelFactory
import com.example.aplikasipatjol.ui.theme.AplikasiPATJOLTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val repository = MockSmsRepositoryImpl()
        val getInboxMessagesUseCase = GetInboxMessagesUseCase(repository)
        val getSpamMessagesUseCase = GetSpamMessagesUseCase(repository)
        val scanMessagesUseCase = ScanMessagesUseCase(repository)
        val factory = SmsViewModelFactory(getInboxMessagesUseCase, getSpamMessagesUseCase, scanMessagesUseCase)

        setContent {
            val viewModel: SmsViewModel = viewModel(factory = factory)
            AplikasiPATJOLTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        MainScreen(viewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AplikasiPATJOLTheme {
        Greeting("Android")
    }
}