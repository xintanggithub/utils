package com.tson.utils.lib.util.time

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Message
import java.util.*

/**
 * Date 2019/6/3 7:46 PM
 *
 * @author tangxin
 */
class TimerUtils {

    companion object {

        var timer: Timer? = null
        var task: MyTimerTask? = null
        var time: Long = 1000
        lateinit var callBack: TimerCallBack

        fun instance(t: Long, c: TimerCallBack) {
            time = t
            callBack = c
        }

        fun start() {
            callBack.start()
            if (null == timer) {
                timer = Timer(true)
            }
            if (null == task) {
                task = MyTimerTask()
            }
            timer?.schedule(task, 1000, time)
        }

        var myHandler: Handler = @SuppressLint("HandlerLeak")
        object : Handler() {
            override fun handleMessage(msg: Message) {
                when (msg.what) {
                    2 -> {
                        callBack.doNotify()
                    }
                }
                super.handleMessage(msg)
            }
        }

        fun stop() {
            callBack.stop()
            timer?.cancel()
            timer = null
            task?.cancel()
            task = null
        }
    }

    class MyTimerTask : TimerTask() {
        override fun run() {
            val message = Message()
            message.what = 2
            myHandler.sendMessage(message)
        }
    }

}

