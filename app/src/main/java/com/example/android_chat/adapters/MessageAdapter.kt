package com.example.android_chat.adapters

import android.content.Context
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android_chat.R
import com.example.android_chat.models.Message
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messageList : ArrayList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    val ITEM_RECEIVE = 1
    val ITEM_SENT = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ITEM_RECEIVE){
            val view : View = LayoutInflater.from(context).inflate(R.layout.receive_layout, parent, false)
            return RecieveViewHolder(view)
        }else {
            val view : View = LayoutInflater.from(context).inflate(R.layout.send_layout, parent, false)
            return SendViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val currentMessage = messageList[position]
        if(holder.javaClass == SendViewHolder::class.java) {
            val viewHolder = holder as SendViewHolder
            viewHolder.sentMessage.text = currentMessage.message
        }else {
            val viewHolder = holder as RecieveViewHolder
            viewHolder.receiveMessage.text = currentMessage.message

        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        return if(FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)){
            ITEM_SENT
        }else {
            ITEM_RECEIVE
        }
    }

    override fun getItemCount(): Int = messageList.size


    class SendViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val sentMessage = itemView.findViewById<TextView>(R.id.text_sent_message)
    }

    class RecieveViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val receiveMessage = itemView.findViewById<TextView>(R.id.text_receive_message)
    }

}