package com.fcenesiz.looper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import com.fcenesiz.looper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    companion object{
        public val TAG: String = MainActivity::class.simpleName.toString()
    }

    lateinit var binding: ActivityMainBinding
    lateinit var myWorker: MyWorker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myWorker = MyWorker()
        myWorker.start()

        binding.buttonSend.setOnClickListener {
            sendMessage()
        }
    }

    fun sendMessage(){
        val message = Message.obtain()
        message.arg1 = 1
        // message.data = Bundle().apply{ putInt() }
        myWorker.handler.sendMessage(message)
    }

    inner class MyWorker : Thread() {
        lateinit var handler: Handler
        override fun run() {
            Looper.prepare()
            handler = object : Handler(Looper.myLooper()!!){
                override fun handleMessage(msg: Message) {
                    Log.i(TAG, "handleMessage: ${msg.arg1}, Thread:${currentThread().name}")
                }
            }
            Looper.loop()
        }
    }

}