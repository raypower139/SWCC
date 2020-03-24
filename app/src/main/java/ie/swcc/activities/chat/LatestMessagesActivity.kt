package ie.swcc.activities.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupDataObserver
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import ie.swcc.R
import ie.swcc.activities.Home
import ie.swcc.models.UserModel
import ie.swcc.models.chat.ChatMessageModel
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_latest_messages.*
import kotlinx.android.synthetic.main.latest_message_row.view.*
import org.jetbrains.anko.startActivity

class LatestMessagesActivity : AppCompatActivity() {

    companion object{
        var currentUser: UserModel? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_latest_messages)

        recyclerview_latest_messages.adapter = adapter
        // Display a vertical line between rows
        recyclerview_latest_messages.addItemDecoration(DividerItemDecoration(this,DividerItemDecoration.VERTICAL))

        listenForLatestMessages()
        fetchCurrenntUser()
        // Check to see if User is logged in
        verifyUserIsLoggedIn()
    }

    class LatestMessageRow(val chatMessage:ChatMessageModel): Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        // Display the Message Text
        viewHolder.itemView.textview_latest_message.text = chatMessage.text
        // Figure out which user to display
        val chatPartnerId: String
        if(chatMessage.fromId == FirebaseAuth.getInstance().uid){
            chatPartnerId = chatMessage.toId
        }else{
            chatPartnerId = chatMessage.fromId!!
        }

        // Retrieve the correct users details from user-photo
        val ref = FirebaseDatabase.getInstance().getReference("/user-photos/$chatPartnerId")
        ref.addListenerForSingleValueEvent(object:ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
               // Get the User Details
                val user = p0.getValue(UserModel::class.java)
                // Display the User's Name
                viewHolder.itemView.username_textview_latest_message.text = user?.uid
                // Load the user image into the imageview and crop it
                val targetImageView = viewHolder.itemView.imageview_latest_message
                Picasso.get().load(user?.profilepic)
                    .transform(CropCircleTransformation())
                    .into(targetImageView)
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

    override fun getLayout(): Int {
        return R.layout.latest_message_row
    }
}
    // Hashmap to store the latest messages
    val latestMessagesMap = HashMap<String, ChatMessageModel>()

    // Refresh the Lastest Messages Recycler when an update occurs with all data from HashMap
    private fun refreshRecyclerViewMessages(){
        adapter.clear()
        latestMessagesMap.values.forEach{
            adapter.add(LatestMessageRow(it))
        }
    }

    // Latest Messages on the opening chat page
    private fun listenForLatestMessages(){
        val fromId = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId")
        ref.addChildEventListener(object: ChildEventListener{

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessageModel:: class.java)?: return
                latestMessagesMap[p0.key!!] = chatMessage
                refreshRecyclerViewMessages()
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {
                val chatMessage = p0.getValue(ChatMessageModel:: class.java)?: return
                latestMessagesMap[p0.key!!] = chatMessage
                refreshRecyclerViewMessages()
            }

            override fun onChildRemoved(p0: DataSnapshot) {}
            override fun onCancelled(p0: DatabaseError) {}
            override fun onChildMoved(p0: DataSnapshot, p1: String?) {}
        })


    }

    val adapter = GroupAdapter<GroupieViewHolder>()


    private fun fetchCurrenntUser(){
        val uid = FirebaseAuth.getInstance().uid
        val ref = FirebaseDatabase.getInstance().getReference("/user-photos/$uid")
        ref.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                currentUser = p0.getValue(UserModel::class.java)
                Log.d("LatestMessages", "Current User ${currentUser?.uid}")

            }
            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }

    private fun verifyUserIsLoggedIn() {
        val uid = FirebaseAuth.getInstance().uid
        if (uid == null) {
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_new_message -> startActivity<NewMessageActivity>()
        }


        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_nav_chat, menu)
        return super.onCreateOptionsMenu(menu)
    }
}

