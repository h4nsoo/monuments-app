package com.example.mohamedbelgacem.util

import android.content.Context
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

object HapticFeedbackManager {

    fun trigger(context: Context, isCorrect: Boolean, audioEnabled: Boolean) {
        if (isCorrect) {
            if (audioEnabled) playTone(ToneGenerator.TONE_PROP_ACK, 180)
            vibrate(context, longArrayOf(0, 55))
        } else {
            if (audioEnabled) playTone(ToneGenerator.TONE_PROP_NACK, 350)
            vibrate(context, longArrayOf(0, 90, 70, 90))
        }
    }

    private fun playTone(type: Int, durationMs: Int) {
        try {
            val tg = ToneGenerator(AudioManager.STREAM_MUSIC, 85)
            tg.startTone(type, durationMs)
        } catch (_: Exception) {}
    }

    @Suppress("DEPRECATION")
    private fun vibrate(context: Context, pattern: LongArray) {
        try {
            val vibrator = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createWaveform(pattern, -1))
            } else {
                vibrator.vibrate(pattern, -1)
            }
        } catch (_: Exception) {}
    }
}
