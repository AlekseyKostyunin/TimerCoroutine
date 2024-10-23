package com.alekseykostyunin.timercoroutine

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@SuppressLint("DefaultLocale")
@Composable
fun GetOneTimer(
    timer: MyTimer,
    viewModel: TimerViewModel
) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, bottom = 15.dp),
        elevation = CardDefaults.elevatedCardElevation(6.dp),
    ) {
        val showSeconds = remember { mutableStateOf(true) }
        var checked by remember { mutableStateOf(false) }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Text("Таймер ${timer.id}", fontWeight = FontWeight.Bold, fontSize = 24.sp)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = if (showSeconds.value) {
                    timer.time.let { String.format("%02d:%02d", it / 60, it % 60) }
                } else {
                    timer.time.let { String.format("%02d мин.", it / 60) }
                },
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Button(
                    onClick = {
                        viewModel.startTimer(timer.id)
                    },
                    Modifier.padding(end = 25.dp)
                ) {
                    Text(text = "Старт")
                }
                Button(
                    onClick = {
                        viewModel.cancelTimer(timer.id)
                    }
                ) {
                    Text(text = "Сброс")
                }
            }

            Spacer(modifier = Modifier.height(26.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Показывать только минуты", Modifier.padding(end = 25.dp))
                Switch(
                    checked = checked,
                    onCheckedChange = {
                        checked = it
                        showSeconds.value = !checked
                    },
                    thumbContent = {
                        if (checked) {
                            Icon(
                                imageVector = Icons.Filled.Check,
                                contentDescription = null,
                                modifier = Modifier.size(SwitchDefaults.IconSize),
                            )
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(26.dp))

        }
    }
}