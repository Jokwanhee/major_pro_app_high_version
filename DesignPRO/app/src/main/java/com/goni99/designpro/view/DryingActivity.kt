package com.goni99.designpro.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.goni99.designpro.R
import com.goni99.designpro.databinding.ActivityDryingBinding
import com.goni99.designpro.network.MyMqtt
import org.eclipse.paho.client.mqttv3.MqttMessage

class DryingActivity:AppCompatActivity() {

    val binding by lazy {
        ActivityDryingBinding.inflate(layoutInflater)
    }

    val subTopic = "iot/#"
    val serverUri = "tcp://192.168.35.16:1883"
    var myMqtt: MyMqtt? = null


    // iot/servo -> dry up
    // iot/servo -> dry down


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        myMqtt = MyMqtt(this, serverUri)
        myMqtt?.mySetCallback(::onReceived)
        myMqtt?.connect(arrayOf(subTopic))

        with(binding){
            dryUpButton.setOnClickListener {
                myMqtt?.publish("iot/servo", "dry up")
            }
            dryDownButton.setOnClickListener {
                myMqtt?.publish("iot/servo", "dry down")
            }
        }

    }

    fun onReceived(topic:String, message: MqttMessage) {
        val msg = String(message.payload)
        Log.d("mymqtt", "onReceived topic : $topic, msg : $msg")
    }
}