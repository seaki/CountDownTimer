package jp.sastudio.countdowntimer

import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    private var countDownTimer: CountDownTimer =
        object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                var minute = millisUntilFinished / 1000 / 60
                var second = millisUntilFinished / 1000 % 60
                findViewById<TextView>(R.id.textViewCountDown).text =
                    "%02d:%02d".format(minute, second)
            }
            override fun onFinish() {}
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.buttonStartPause).setOnClickListener {
            countDownTimer.start()
        }
        findViewById<Button>(R.id.buttonReset).setOnClickListener {
            countDownTimer.cancel()
        }
    }
}