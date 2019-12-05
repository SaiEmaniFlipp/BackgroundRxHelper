package com.example.rxbackground

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_next.*

class NextActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_next)

        button_refresh.setOnClickListener {
            fetchJobStatus()
        }

        fetchJobStatus()
    }

    private fun fetchJobStatus() {
        if (BackgroundTaskHelper.isCompleted()) {
            if (BackgroundTaskHelper.getError() != null) {
                text_view_status.text = "Error"
            } else {
                text_view_status.text = "Completed"
            }
        } else {
            text_view_status.text = "Processing"
        }
    }
}