package jp.sastudio.countdowntimer

import android.content.res.AssetFileDescriptor
import android.media.*
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

// Thanks to
// https://blog.codecamp.jp/android-app-development-1
// https://akira-watson.com/android/countdowntimer.html
// https://akira-watson.com/android/audio-player.html
// http://android-note.open-memo.net/sub/sound__sound_rsc.html

class MainActivity : AppCompatActivity() {
    private val timeInMillis: Long = 3 * 60 * 1000
    private val countDownInterval: Long = 1000
    private var timeLeftInMillis: Long = 3 * 60 * 1000
    private var timer: CountDownTimer? = null
    private var running: Boolean = false
    private var player: MediaPlayer = MediaPlayer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        volumeControlStream = AudioManager.STREAM_MUSIC

        initializeTimer(timeInMillis)
        initializePlayer()

        findViewById<Button>(R.id.buttonStartPause).setOnClickListener {
            if (running) {
                pauseTimer()
            }
            else {
                startTimer()
            }
        }
        findViewById<Button>(R.id.buttonReset).setOnClickListener {
            resetTimer()
        }
    }

    private fun initializeTimer(millisInFuture: Long) {
        timer = object : CountDownTimer( millisInFuture, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeftInMillis = millisUntilFinished
                var minute = millisUntilFinished / 1000 / 60
                var second = millisUntilFinished / 1000 % 60
                findViewById<TextView>(R.id.textViewCountDown).text =
                    "%02d:%02d".format(minute, second)
            }
            override fun onFinish() {
                player.pause()
                player.seekTo(0)
                initializeTimer(timeInMillis)
                running = false
                findViewById<Button>(R.id.buttonStartPause).text = getString(R.string.Start)
                findViewById<Button>(R.id.buttonReset).visibility = View.VISIBLE
            }
        }

        var minute = millisInFuture / 1000 / 60
        var second = millisInFuture / 1000 % 60
        findViewById<TextView>(R.id.textViewCountDown).text =
            "%02d:%02d".format(minute, second)
    }

    private fun initializePlayer() {
        var forestBear: AssetFileDescriptor  = assets.openFd("Forest_Bear.m4a")
        player.setDataSource(forestBear)
        player.prepare()
    }

    private fun startTimer() {
        player.start()
        timer?.start()
        running = true
        findViewById<Button>(R.id.buttonStartPause).text = getString(R.string.Stop)
        findViewById<Button>(R.id.buttonReset).visibility = View.INVISIBLE
    }

    private fun pauseTimer() {
        player.pause()
        timer?.cancel()
        initializeTimer(timeLeftInMillis)
        running = false
        findViewById<Button>(R.id.buttonStartPause).text = getString(R.string.Start)
        findViewById<Button>(R.id.buttonReset).visibility = View.VISIBLE
    }

    private fun resetTimer() {
        player.seekTo(0)
        initializeTimer(timeInMillis)
    }
}