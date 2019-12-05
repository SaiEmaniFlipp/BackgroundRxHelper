package com.example.rxbackground

import android.app.Application
import android.content.Context
import android.widget.Toast
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers

object BackgroundTaskHelper {

    private var completable: Completable? = null
    private var isCompleted: Boolean = false
    private var error: Exception? = null

    fun startJob(context: Context) {
        if (completable == null) {
            completable = Completable.create { emitter ->
                Thread.sleep(10000)
                emitter.onComplete()
            }

            completable!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object: DisposableCompletableObserver() {
                    override fun onComplete() {
                        isCompleted = true
                        completable = null
                    }

                    override fun onError(e: Throwable) {
                        isCompleted = true
                        error = Exception(e.message)
                    }
                })

            Toast.makeText(context, "Task is running....", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Task has been started....", Toast.LENGTH_LONG).show()
        }
    }

    fun isCompleted() = isCompleted

    fun getError() = error
}