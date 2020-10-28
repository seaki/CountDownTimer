package jp.sastudio.countdowntimer

import android.content.res.AssetFileDescriptor
import android.media.*
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var countDownTimer: CountDownTimer =
        object : CountDownTimer(180000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var minute = millisUntilFinished / 1000 / 60
                var second = millisUntilFinished / 1000 % 60
                findViewById<TextView>(R.id.textViewCountDown).text =
                    "%02d:%02d".format(minute, second)
            }
            override fun onFinish() {}
        }

    //private var forestBear: AssetFileDescriptor  = assets.openFd("Forest_Bear.m4a")

    private var player: MediaPlayer = MediaPlayer()

    private var playing: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var forestBear: AssetFileDescriptor  = assets.openFd("Forest_Bear.m4a")
        player.setDataSource(forestBear)
        volumeControlStream = AudioManager.STREAM_MUSIC
        player.prepare()

        findViewById<Button>(R.id.buttonStartPause).setOnClickListener {
            countDownTimer.start()
            if (playing) {
                playing = false
                player.pause()
            }
            else {
                player.start()
                playing = true
            }
        }
        findViewById<Button>(R.id.buttonReset).setOnClickListener {
            countDownTimer.cancel()
            playing = false
            player.reset()
            var forestBear: AssetFileDescriptor  = assets.openFd("Forest_Bear.m4a")
            player.setDataSource(forestBear)
            player.prepare()
        }
    }
}