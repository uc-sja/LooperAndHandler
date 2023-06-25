package com.example.looperandhandler

import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import java.lang.Thread.sleep
import com.example.looperandhandler.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private var continuousWorkerThread: ContinuousWorkerThread? = null

    private lateinit var tvHandleMessage: TextView

    private lateinit var handler: Handler


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate: thread name main " + Thread.currentThread().name)

        tvHandleMessage = findViewById(R.id.handleMessage)

        handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                tvHandleMessage.text = msg.obj.toString()
            }
        }

        callContinuousWorkers()

    }

    private fun callContinuousWorkers() {
        continuousWorkerThread = ContinuousWorkerThread()
        continuousWorkerThread?.addTasks {
            sleep(3000)
            Log.d(TAG, "callContinuousWorkers: 1 " + Thread.currentThread().name)
            val msg = Message.obtain()
            msg.obj = "callContinuousWorkers 1"
            handler.sendMessage(msg)
        }?.addTasks {

            sleep(4000)
                Log.d(TAG, "callContinuousWorkers: 2 "+ Thread.currentThread().name)
                val msg = Message.obtain()
                msg.obj = "callContinuousWorkers 2"
                handler.sendMessage(msg)



        }?.addTasks {
            sleep(5000)

            Log.d(TAG, "callContinuousWorkers: 3 "+ Thread.currentThread().name)

            val msg = Message.obtain()
            msg.obj = "callContinuousWorkers 3"
            handler.sendMessage(msg)


        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "run onDestroy: ")
        continuousWorkerThread?.quit()
    }
}