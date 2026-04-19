package com.example.lab8

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var btnPrev: Button
    private lateinit var btnNext: Button
    private lateinit var btnSlideshow: Button
    private lateinit var btnOpenVideo: Button

    private val images = intArrayOf(
        R.drawable.img1,
        R.drawable.img2,
        R.drawable.img3,
        R.drawable.img4
    )

    private var currentIndex = 0
    private var slideshowTimer: Timer? = null
    private var isSlideshowRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)
        btnPrev = findViewById(R.id.btnPrev)
        btnNext = findViewById(R.id.btnNext)
        btnSlideshow = findViewById(R.id.btnSlideshow)
        btnOpenVideo = findViewById(R.id.btnOpenVideo)

        showImage(currentIndex)

        btnPrev.setOnClickListener {
            showPreviousImage()
        }

        btnNext.setOnClickListener {
            showNextImage()
        }

        btnSlideshow.setOnClickListener {
            toggleSlideshow()
        }

        btnOpenVideo.setOnClickListener {
            val intent = Intent(this, VideoActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        AudioPlayerManager.startAudio(this)
    }

    private fun showImage(index: Int) {
        if (index >= 0 && index < images.size) {
            imageView.setImageResource(images[index])
            currentIndex = index
        }
    }

    private fun showNextImage() {
        currentIndex = (currentIndex + 1) % images.size
        showImage(currentIndex)
    }

    private fun showPreviousImage() {
        currentIndex = (currentIndex - 1 + images.size) % images.size
        showImage(currentIndex)
    }

    private fun toggleSlideshow() {
        if (isSlideshowRunning) {
            slideshowTimer?.cancel()
            slideshowTimer = null
            isSlideshowRunning = false
            btnSlideshow.text = getString(R.string.slideshow_button)
        } else {
            slideshowTimer = Timer()
            slideshowTimer?.schedule(object : TimerTask() {
                override fun run() {
                    runOnUiThread {
                        showNextImage()
                    }
                }
            }, 0, 3000)

            isSlideshowRunning = true
            btnSlideshow.text = getString(R.string.stop_slideshow_button)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        slideshowTimer?.cancel()
        slideshowTimer = null
    }
}