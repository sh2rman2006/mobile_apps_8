package com.example.lab8

import android.content.Context
import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper

object AudioPlayerManager {

    private var mediaPlayer: MediaPlayer? = null

    fun startAudio(context: Context) {
        if (mediaPlayer == null) {
            mediaPlayer = MediaPlayer.create(context.applicationContext, R.raw.audio_sample)
            mediaPlayer?.isLooping = true
        }

        if (mediaPlayer?.isPlaying == false) {
            mediaPlayer?.start()
        }
    }

    fun pauseAudio() {
        if (mediaPlayer?.isPlaying == true) {
            mediaPlayer?.pause()
        }
    }

    fun resumeAudioWithDelay() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (mediaPlayer != null && mediaPlayer?.isPlaying == false) {
                mediaPlayer?.start()
            }
        }, 1500)
    }

    fun stopAndRelease() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}