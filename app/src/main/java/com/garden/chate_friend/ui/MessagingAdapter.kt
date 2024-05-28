import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.garden.chate_friend.R
import com.garden.chate_friend.data.Message
import com.garden.chate_friend.utils.Constants.RECEIVE_ID
import com.garden.chate_friend.utils.Constants.SEND_ID
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MessagingAdapter : RecyclerView.Adapter<MessagingAdapter.MessageViewHolder>() {

    var messagesList = mutableListOf<Message>()

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                messagesList.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return MessageViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.message_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return messagesList.size
    }

    fun insertMessage(message: Message) {
        GlobalScope.launch(Dispatchers.Main) { // Cambio importante aquí
            messagesList.add(message)
            notifyItemInserted(messagesList.size)
            if (message.id == SEND_ID) {
                simulateAutoReply()
            }
        }
    }


    fun simulateAutoReply() {
        val autoReplyMessage = Message("Respuesta automática", RECEIVE_ID, getCurrentTime())
        GlobalScope.launch {
            kotlinx.coroutines.delay(2000)
            insertMessage(autoReplyMessage)
        }
    }

    fun getCurrentTime(): String {
        val dateFormat = SimpleDateFormat("hh:mm a", Locale.getDefault())
        return dateFormat.format(Date())
    }
/*
    fun sendMessageToAPI(message: String) {

    } */


    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val currentMessage = messagesList[position]

        when (currentMessage.id) {
            SEND_ID -> {
                holder.itemView.findViewById<TextView>(R.id.tv_message).apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.itemView.findViewById<TextView>(R.id.tv_bot_message).visibility = View.GONE
            }

            RECEIVE_ID -> {
                holder.itemView.findViewById<TextView>(R.id.tv_bot_message).apply {
                    text = currentMessage.message
                    visibility = View.VISIBLE
                }
                holder.itemView.findViewById<TextView>(R.id.tv_message).visibility = View.GONE
            }
        }
    }
}
