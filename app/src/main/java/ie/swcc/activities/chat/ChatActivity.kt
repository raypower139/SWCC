package ie.swcc.activities.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import ie.swcc.R
import ie.swcc.models.UserModel
import ie.swcc.models.chat.ChatMessageModel
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_chat.*
import kotlinx.android.synthetic.main.card_user.view.*
import kotlinx.android.synthetic.main.chat_from_row.view.*
import kotlinx.android.synthetic.main.chat_to_row.view.*

class ChatActivity : AppCompatActivity() {

    companion object{
        val TAG = "ChatLog"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val user = intent.getParcelableExtra<UserModel>(NewMessageActivity.USER_KEY)
        supportActionBar?.title= user.uid
        setUpDummyData()

      send_button_chat.setOnClickListener{ Log.d(TAG, "Attempt to send message")
      performSendMessage()}

    }



    private fun performSendMessage(){
        //Send message to firebase
        val text = editText_chat.text.toString()
        val fromId = FirebaseAuth.getInstance().uid
        val user = intent.getParcelableExtra<UserModel>(NewMessageActivity.USER_KEY)
        val toId = user.uid
        val reference = FirebaseDatabase.getInstance().getReference("/messages").push()

        val chatMessage = ChatMessageModel(reference.key!!, text, fromId, toId!!, System.currentTimeMillis()/1000
        )
        reference.setValue(chatMessage)
            .addOnSuccessListener { Log.d(TAG, "Saved our Chat Message: ${reference.key}") }
    }

    private fun setUpDummyData(){
        val adapter = GroupAdapter<GroupieViewHolder>()
        adapter.add(ChatFromItem("From Message"))
        adapter.add(ChatToItem("To Message"))
        adapter.add(ChatFromItem("From Message"))
        adapter.add(ChatToItem("To Message"))
        recyclerview_chat.adapter = adapter
    }
}




class ChatFromItem(val text: String): Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position:Int){
        viewHolder.itemView.textview_from_row.text = "From Message"
    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}

class ChatToItem(val text: String): Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position:Int){
        viewHolder.itemView.textview_to_row.text = "This is the to text row Message"
    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }
}