package com.example.looperandhandler

import android.util.Log
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.atomic.AtomicBoolean

private const val TAG = "ContinuousWorkerThread"
class ContinuousWorkerThread(): Thread(){
    private val flag: AtomicBoolean = AtomicBoolean(true)
    private val taskList = ConcurrentLinkedQueue<Runnable>()
     init {
        start()
         Log.d(TAG, ":onCreate:  "+this.name)
    }

    override fun run() {
        while (flag.get()) {
            taskList.poll()?.let{
                Log.d(TAG, "callContinuousWorkers: taskexists ")
                it.run()
            }
        }
        Log.d(TAG, "run: thread terminated")
    }

    fun addTasks( runnable: Runnable): ContinuousWorkerThread {
        taskList.add(runnable)
        return this
    }


    fun quit(){
        flag.set(false)
    }
}