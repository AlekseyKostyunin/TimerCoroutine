package com.alekseykostyunin.timercoroutine

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.alekseykostyunin.timercoroutine.ui.theme.TimerCoroutineTheme
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Внедрение зависимостей через Koin
        val viewModel: TimerViewModel = getViewModel()

        enableEdgeToEdge()
        setContent {
            TimerCoroutineTheme {
                val timers by viewModel.timers.collectAsState()
                Scaffold(
                    content = { innerPadding ->
                        Box(
                            Modifier
                                .background(MaterialTheme.colorScheme.background)
                                .fillMaxSize()
                                .padding(innerPadding)
                        ) {
                            Column {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Button(
                                        onClick = {
                                            viewModel.createNewTimer()
                                        },
                                        Modifier.padding(16.dp)
                                    ) {
                                        Text(text = "Создать новый таймер")
                                    }
                                }

                                LazyColumn {
                                    items(
                                        items = timers,
                                        key = { it.id }
                                    ) {
                                        GetOneTimer(
                                            it,
                                            viewModel
                                        )
                                    }
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}