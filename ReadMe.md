## send message to worker with handler and looper

- send messages in main
  ````kotlin
    fun sendMessage(){
        myWorker = MyWorker()
        myWorker.start()
        val message = Message.obtain()
        message.arg1 = 1
        // message.data = Bundle().apply{ putInt() }
        myWorker.handler.sendMessage(message)
    }
  ````

- handle messages in worker
    ````kotlin
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
    ````