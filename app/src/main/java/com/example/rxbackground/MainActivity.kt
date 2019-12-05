package com.example.rxbackground

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BackgroundTaskHelper.IBackgroundListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button_next.setOnClickListener {
            val intent = Intent(this, NextActivity::class.java)
            startActivity(intent)
        }

        button_start_job.setOnClickListener {
            BackgroundTaskHelper.startJob(this)
        }

        BackgroundTaskHelper.addListener(this)
    }

    override fun onDestroy() {
        BackgroundTaskHelper.removeListener(this)
        super.onDestroy()
    }

    override fun onTaskCompleted() {
        /* no op */
    }

    override fun onTaskError(e: Throwable) {
        /* no op */
    }
}
