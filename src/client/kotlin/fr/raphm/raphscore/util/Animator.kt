package fr.raphm.raphscore.util

import kotlin.math.roundToInt
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.PI

class Animator(
    var startValue: Float,
    var endValue: Float,
    var duration: Float,
    var updateListener: (Float) -> Unit
) {

    private var elapsedTime = 0f
    private val totalDuration = duration
    var isAnimating = false

    fun start() {
        elapsedTime = 0f
        isAnimating = true
    }

    private fun sineEase(t: Float): Float {
        return (sin((t * PI / 2).toFloat()))
    }

    fun update(delta: Float) {
        if (!isAnimating) return

        elapsedTime += delta

        val fraction = (elapsedTime / totalDuration).coerceIn(0f, 1f)

        val easedFraction = sineEase(fraction)

        val interpolatedValue = startValue + (endValue - startValue) * easedFraction
        updateListener(interpolatedValue)

        if (fraction >= 1f) {
            isAnimating = false
            updateListener(endValue)
        }
    }
}

