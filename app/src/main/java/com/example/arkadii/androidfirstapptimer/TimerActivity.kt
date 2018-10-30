package com.example.arkadii.androidfirstapptimer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.os.PersistableBundle
import android.util.Log
import android.widget.TextView


class TimerActivity : AppCompatActivity() {

    private var timerTime: Int = 0
    var result_string: String = ""

    lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        onRestoreInstanceState(savedInstanceState)
        setContentView(R.layout.activity_timer)

        val button = this.findViewById<View>(R.id.button) as Button
        button.text = "START"
        button.setOnClickListener {
            if (button.text != "START") {
                startTimer()
                button.text = "START"
            } else {
                canselTimer()
                button.text = "STOP"
            }
        }

        val text = this.findViewById<View>(R.id.textView) as TextView
        text.text = "один"
        val units = this.resources.getStringArray(R.array.units)
        val tens = this.resources.getStringArray(R.array.tens)
        val hundreds = this.resources.getStringArray(R.array.hundreds)
        
        timer = timerObject(units, tens, hundreds)
    }

    private fun startTimer() {
        timer.start()
    }

    private fun canselTimer() {
        timerTime = 0
        timer.cancel()
    }

    private fun timerObject(
        units: Array<String>,
        tens: Array<String>,
        hundreds: Array<String>): CountDownTimer {
        return object : CountDownTimer(1000000, 1000) {
            override fun onFinish() {
                timerTime = 0
            }

            override fun onTick(millisUntilFinished: Long) {
                result_string = ""
                timerTime++
                Log.d("TimerActivity", timerTime.toString())
                when {
                    timerTime / 100 != 0 -> {
                        result_string += hundreds[timerTime / 100 - 1]
                    }
                    timerTime / 10 % 10 > 1 -> {
                        result_string += tens[timerTime / 10 % 10 - 1]
                    }
                    timerTime %10 != 0 -> {
                        result_string += units[timerTime %10 - 1]
                    }
                }

                if (timerTime == 1000) {
                    result_string = this@TimerActivity.getString(R.string.thousand)
                }

                val textView = this@TimerActivity.findViewById<View>(R.id.textView) as TextView
                textView.text = result_string
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        outState?.putInt(this.getString(R.string.timer), timerTime)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            timerTime = savedInstanceState.getInt(this.getString(R.string.timer))
        }
    }

}
