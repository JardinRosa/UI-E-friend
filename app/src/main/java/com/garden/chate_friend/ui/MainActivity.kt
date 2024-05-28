package com.garden.chate_friend.ui

import MessagingAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.garden.chate_friend.R
import com.garden.chate_friend.data.Message
import com.garden.chate_friend.utils.Constants.SEND_ID

class MainActivity : AppCompatActivity() {

    private lateinit var messagingAdapter: MessagingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        messagingAdapter = MessagingAdapter()

        val rvMessages = findViewById<RecyclerView>(R.id.rv_messages)
        rvMessages.adapter = messagingAdapter
        rvMessages.layoutManager = LinearLayoutManager(this) // Asignar un LinearLayoutManager al RecyclerView

        val btnEnviar = findViewById<Button>(R.id.btn_voice)
        btnEnviar.setOnClickListener {
            print("Se envio un mensaje")
            val etMessage = findViewById<EditText>(R.id.et_message)
            val messageText = etMessage.text.toString()

            val message = Message(messageText, SEND_ID, "00:00")

            messagingAdapter.insertMessage(message)

            etMessage.text.clear()
            print("Fin")

            messagingAdapter.simulateAutoReply()
        }
    }
}