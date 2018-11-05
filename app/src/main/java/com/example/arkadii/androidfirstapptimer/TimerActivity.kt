package com.example.arkadii.androidfirstapptimer

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.TextView


class TimerActivity : AppCompatActivity() {

    var timerTime: Int = 0
    var currentNumber: String = ""
    var buttonCapture: String = ""

    private lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        val button = this.findViewById<View>(R.id.button) as Button
        button.text = "START"
        button.setOnClickListener {
            if (button.text == "START") {
                timer.start()
                buttonCapture = "STOP"
                button.text = buttonCapture
            } else {
                timer.cancel()
                timerTime = 0
                buttonCapture = "START"
                button.text = buttonCapture
            }
        }

        val text = this.findViewById<View>(R.id.textView) as TextView
        text.text = currentNumber
        val units = this.resources.getStringArray(R.array.units)
        val firstTens = this.resources.getStringArray(R.array.firstTens)
        val tens = this.resources.getStringArray(R.array.tens)
        val hundreds = this.resources.getStringArray(R.array.hundreds)
        timer = timerObject(units, firstTens, tens, hundreds)
        if (savedInstanceState != null) {
            timerTime = savedInstanceState.getString(this.getString(R.string.timerTime), "0").toInt()
            currentNumber = savedInstanceState.getString(this.getString(R.string.currentNumber), currentNumber)
            buttonCapture = savedInstanceState.getString(this.getString(R.string.buttonCapture), buttonCapture)

            if (timerTime != 0) {
                timer.start()
                buttonCapture = "STOP"
                button.text = buttonCapture
            }
        }
    }

    private fun timerObject(
        units: Array<String>,
        firstTens: Array<String>,
        tens: Array<String>,
        hundreds: Array<String>): CountDownTimer {
        return object : CountDownTimer(1001000, 1000) {
            override fun onFinish() {
                this.cancel()
                timerTime = 0
                val button = this@TimerActivity.findViewById<View>(R.id.button) as Button
                buttonCapture = "START"
                button.text = buttonCapture
            }

            override fun onTick(millisUntilFinished: Long) {
                var resultString = ""
                timerTime++

                if (timerTime == 1000) {
                    resultString = this@TimerActivity.getString(R.string.thousand)
                    onFinish()
                }

                if (timerTime / 100 != 0) {
                    resultString += hundreds[timerTime / 100 - 1]
                }
                if (timerTime / 10 % 10 > 1) {
                    if (!resultString.isEmpty()) {
                        resultString += " "
                    }
                    resultString += tens[timerTime / 10 % 10 - 1]
                }
                if (timerTime / 10 % 10 == 1) {
                    if (!resultString.isEmpty()) {
                        resultString += " "
                    }
                    resultString += if (timerTime % 10 == 0) {
                        tens[0]
                    } else {
                        firstTens[timerTime % 10 - 1]
                    }
                }
                if (timerTime % 10 != 0) {
                    if (!resultString.isEmpty()) {
                        resultString += " "
                    }
                    if (timerTime / 10 % 10 != 1) {
                        resultString += units[timerTime % 10 - 1]
                    }
                }

                val textView = this@TimerActivity.findViewById<View>(R.id.textView) as TextView
                textView.text = resultString
                currentNumber = resultString
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putString(this.getString(R.string.timerTime), timerTime.toString())
        outState?.putString(this.getString(R.string.currentNumber), currentNumber)
        outState?.putString(this.getString(R.string.buttonCapture), buttonCapture)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            timerTime = savedInstanceState.getString(this.getString(R.string.timerTime), "0").toInt()
            currentNumber = savedInstanceState.getString(this.getString(R.string.currentNumber), currentNumber)
            buttonCapture = savedInstanceState.getString(this.getString(R.string.buttonCapture), buttonCapture)
        }
    }

    override fun onStop() {
        super.onStop()
        timer.cancel()
    }

}
