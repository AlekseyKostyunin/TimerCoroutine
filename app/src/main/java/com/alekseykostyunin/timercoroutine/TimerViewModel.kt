package com.alekseykostyunin.timercoroutine

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class TimerViewModel : ViewModel() {

    private val initialTimer = mutableListOf(MyTimer(1, 0))

    private val _timers = MutableStateFlow<List<MyTimer>>(initialTimer)
    val timers: StateFlow<List<MyTimer>> = _timers

    private var disposables: MutableMap<Int, Job> = mutableMapOf()

    fun createNewTimer() {
        val idLastTimer = _timers.value.last().id
        val newTimer = MyTimer(idLastTimer + 1, 0)
        _timers.value += newTimer
    }

    fun startTimer(timerId: Int) {
        if (disposables.containsKey(timerId) && !disposables[timerId]!!.isActive) {
            return
        }

        disposables[timerId] = viewModelScope.launch {
            while (isActive) {
                _timers.value = _timers.value.map { timer ->
                    if(timer.id == timerId) {
                        timer.copy(time = timer.time + 1)
                    } else {
                        timer
                    }
                }
                delay(1000)
            }
            _timers.value = _timers.value.map { timer ->
                if (timer.id == timerId) {
                    timer.copy(time = 0)
                } else {
                    timer
                }
            }
        }
    }

    fun cancelTimer(timerId: Int) {
        val timer = _timers.value.find { it.id == timerId }
        if (timer != null) {
            _timers.value = _timers.value.map {
                if (it.id == timerId) {
                    it.copy(time = 0)
                } else {
                    it
                }
            }
            disposables[timerId]?.cancel()
            disposables.remove(timerId)
        }
    }
}