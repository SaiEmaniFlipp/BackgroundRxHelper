package com.example.rxbackground

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

    private var listeners = mutableListOf<IBackgroundListener>()

    fun startJob(context: Context) {
        if (completable == null) {
            isCompleted = false
            error = null

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
                        if (listeners.isNotEmpty()) {
                            listeners.forEach { it.onTaskCompleted() }
                        }
                    }

                    override fun onError(e: Throwable) {
                        isCompleted = true
                        error = Exception(e.message)
                        if (listeners.isNotEmpty()) {
                            listeners.forEach { it.onTaskError(e) }
                        }
                    }
                })

            Toast.makeText(context, "Task is running....", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(context, "Task has been started....", Toast.LENGTH_LONG).show()
        }
    }

    fun addListener(listener: IBackgroundListener) {
        listeners.add(listener)
    }

    fun removeListener(listener: IBackgroundListener) {
        listeners.remove(listener)
    }

    interface IBackgroundListener {
        fun onTaskCompleted()
        fun onTaskError(e: Throwable)
    }
}