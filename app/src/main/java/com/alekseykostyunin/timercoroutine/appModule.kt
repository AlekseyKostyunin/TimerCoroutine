package com.alekseykostyunin.timercoroutine

import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModel

val appModule = module {
    viewModel { TimerViewModel() }
}