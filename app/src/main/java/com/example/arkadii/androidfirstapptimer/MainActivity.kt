package com.example.arkadii.androidfirstapptimer

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.os.PersistableBundle


class MainActivity : AppCompatActivity() {

    private var timerTime: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        onRestoreInstanceState(savedInstanceState)
        setContentView(R.layout.activity_main)

        object : CountDownTimer(2000, 1000) {
            override fun onFinish() {
                timerTime = 0
            }

            override fun onTick(millisUntilFinished: Long) {
                timerTime++
                if (timerTime == 2) {
                    val intent = Intent(this@MainActivity, TimerActivity::class.java)
                    this@MainActivity.finish()
                    startActivity(intent)
                }
            }
        }.start()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        outState?.putInt(this.getString(R.string.main_timer), timerTime)
        super.onSaveInstanceState(outState, outPersistentState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState != null) {
            timerTime = savedInstanceState.getInt(this.getString(R.string.main_timer))
        }
    }
}
