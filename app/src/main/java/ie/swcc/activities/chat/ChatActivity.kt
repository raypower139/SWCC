package ie.swcc.activities.chat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
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

    val adapter = GroupAdapter<GroupieViewHolder>()
    var toUser: UserModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        recyclerview_chat.adapter = adapter
        toUser = intent.getParcelableExtra<UserModel>(NewMessageActivity.USER_KEY)
        supportActionBar?.title= toUser?.name

        listenForMessages()

      send_button_chat.setOnClickListener{ Log.d(TAG, "Attempt to send message")
      performSendMessage()}

    }

private fun listenForMessages(){
    val fromId = FirebaseAuth.getInstance().uid
    val toId = toUser?.uid
    val ref = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId")
    ref.addChildEventListener(object: ChildEventListener{

        override fun onChildAdded(p0: DataSnapshot, p1: String?) {
            val chatMessage = p0.getValue(ChatMessageModel::class.java)

            if (chatMessage != null) {
                Log.d(TAG, chatMessage.text)

            if(chatMessage.fromId == FirebaseAuth.getInstance().uid){
                val currentUser = LatestMessagesActivity.currentUser
                adapter.add(ChatFromItem(chatMessage.text, currentUser!!))
                } else {
                adapter.add(ChatToItem(chatMessage.text, toUser!!))
            }
            }
            recyclerview_chat.scrollToPosition((adapter.itemCount -1))
        }

        override fun onCancelled(p0: DatabaseError) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onChildChanged(p0: DataSnapshot, p1: String?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onChildMoved(p0: DataSnapshot, p1: String?) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

        override fun onChildRemoved(p0: DataSnapshot) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }

    })
}

    private fun performSendMessage(){
        //Send message to firebase
        val text = editText_chat.text.toString()
        val fromId = FirebaseAuth.getInstance().uid
        val user = intent.getParcelableExtra<UserModel>(NewMessageActivity.USER_KEY)
        val toId = user.uid
        val reference = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId").push()
        val toReference = FirebaseDatabase.getInstance().getReference("/user-messages/$toId/$fromId").push()
        val chatMessage = ChatMessageModel(reference.key!!, text, fromId, toId!!, System.currentTimeMillis()/1000
        )
        reference.setValue(chatMessage)
            .addOnSuccessListener { Log.d(TAG, "Saved our Chat Message: ${reference.key}")
                // Clear Text View after you send
                editText_chat.text.clear()
                // Move view to last message
            recyclerview_chat.scrollToPosition(adapter.itemCount -1)
            }
        toReference.setValue(chatMessage)
            .addOnSuccessListener { Log.d(TAG, "To Chat Message: ${reference.key}") }

        val latestMessageRef = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId/${toId}")
        latestMessageRef.setValue(chatMessage)

        val latestMessageToRef = FirebaseDatabase.getInstance().getReference("/latest-messages/$toId/${fromId}")
        latestMessageToRef.setValue(chatMessage)
    }


}




class ChatFromItem(val text: String, val user: UserModel): Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position:Int){
        viewHolder.itemView.textview_from_row.text = text
        val uri = user.profilepic
        val targetImageView = viewHolder.itemView.chatFromImage
        Picasso.get().load(uri)
            .transform(CropCircleTransformation())
            .into(targetImageView)
    }

    override fun getLayout(): Int {
        return R.layout.chat_from_row
    }
}

class ChatToItem(val text: String, val user: UserModel): Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position:Int){
        viewHolder.itemView.textview_to_row.text = text
        //Load User Image
        val uri = user.profilepic
        val targetImageView = viewHolder.itemView.chatToImage
        Picasso.get().load(uri)
            .transform(CropCircleTransformation())
            .into(targetImageView)
    }

    override fun getLayout(): Int {
        return R.layout.chat_to_row
    }
}