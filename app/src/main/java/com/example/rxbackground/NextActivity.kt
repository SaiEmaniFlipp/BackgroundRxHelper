package com.example.rxbackground

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_next.*

class NextActivity : AppCompatActivity(), BackgroundTaskHelper.IBackgroundListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next)

        text_view_status.text = "Processing"

        BackgroundTaskHelper.addListener(this)
    }

    override fun onDestroy() {
        BackgroundTaskHelper.removeListener(this)
        super.onDestroy()
    }

    override fun onTaskCompleted() {
        text_view_status.text = "Completed"
    }

    override fun onTaskError(e: Throwable) {
        text_view_status.text = "Error"
    }
}