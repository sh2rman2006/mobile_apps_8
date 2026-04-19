package com.example.lab8

import android.content.Context
import android.media.AudioManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.MediaController
import android.widget.SeekBar
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class VideoActivity : AppCompatActivity() {

    private lateinit var videoView: VideoView
    private lateinit var volumeSeekBar: SeekBar
    private lateinit var btnPlayVideo: Button
    private lateinit var audioManager: AudioManager
    private lateinit var mediaController: MediaController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        videoView = findViewById(R.id.videoView)
        volumeSeekBar = findViewById(R.id.volumeSeekBar)
        btnPlayVideo = findViewById(R.id.btnPlayVideo)

        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager

        setupVolumeControl()
        setupVideo()

        btnPlayVideo.setOnClickListener {
            AudioPlayerManager.pauseAudio()
            videoView.start()
        }
    }

    override fun onStart() {
        super.onStart()
        AudioPlayerManager.pauseAudio()
    }

    private fun setupVolumeControl() {
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)

        volumeSeekBar.max = maxVolume
        volumeSeekBar.progress = currentVolume

        volumeSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })
    }

    private fun setupVideo() {
        mediaController = MediaController(this)
        mediaController.setAnchorView(videoView)
        videoView.setMediaController(mediaController)

        val videoUri = Uri.parse("android.resource://$packageName/${R.raw.video_sample}")
        videoView.setVideoURI(videoUri)
    }

    override fun onStop() {
        super.onStop()
        AudioPlayerManager.resumeAudioWithDelay()
    }

    override fun onDestroy() {
        super.onDestroy()
        videoView.stopPlayback()
    }
}