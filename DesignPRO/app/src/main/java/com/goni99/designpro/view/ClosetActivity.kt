package com.goni99.designpro.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.goni99.designpro.R
import com.goni99.designpro.databinding.ActivityClosetBinding
import com.goni99.designpro.network.MyMqtt
import org.eclipse.paho.client.mqttv3.MqttMessage

class ClosetActivity:AppCompatActivity() {

    val binding by lazy {
        ActivityClosetBinding.inflate(layoutInflater)
    }

    val subTopic = "iot/#"
    val serverUri = "tcp://192.168.0.5:1883"
    var myMqtt: MyMqtt? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        myMqtt = MyMqtt(this, serverUri)
        myMqtt?.mySetCallback(::onReceived)
        myMqtt?.connect(arrayOf(subTopic))


        with(binding){
            leftButton.setOnClickListener {
                myMqtt?.publish("iot/stepmotor", "left")
            }
            rightButton.setOnClickListener {
                myMqtt?.publish("iot/stepmotor", "right")
            }
            stopButton.setOnClickListener {
                myMqtt?.publish("iot/stepmotor", "stop")

            }
        }

    }

    fun onReceived(topic:String, message: MqttMessage) {
        val msg = String(message.payload)
        Log.d("mymqtt", "onReceived topic : $topic, msg : $msg")
    }
}