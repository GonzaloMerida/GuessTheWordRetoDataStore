package com.example.guesstheword.utils

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MyTimer(
    private val max: Float,
    private val step : Float,
    private val refreshIntervalTime : Long
    ) {

    val timer : Flow<Float> = flow<Float> {
        var i = 0f
        while(i <= max) {
            emit(i)
            i+=step
            delay(refreshIntervalTime)
        }
    }

}