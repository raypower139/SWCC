package ie.swcc.activities.chat

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.net.toUri
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import ie.swcc.R
import ie.swcc.activities.Home
import ie.swcc.activities.chat.LatestMessagesActivity.Companion.currentUser
import ie.swcc.main.SWCCApp
import ie.swcc.models.UserModel
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_new_message.*
import kotlinx.android.synthetic.main.card_user.view.*



class NewMessageActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)
        supportActionBar?.title = "Select User"

        fetchusers()

    }

    companion object{
        val USER_KEY = "USER_KEY"
    }

    private fun fetchusers(){
        val ref = FirebaseDatabase.getInstance().getReference("/user-photos")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<GroupieViewHolder>()
                p0.children.forEach{
                    Log.d("NewMessage", it.toString())
                    val user = it.getValue(UserModel::class.java)
                    adapter.add(UserItem(user!!))
                    }
                // Listener for Users List
                adapter.setOnItemClickListener{item, view ->

                    val userItem = item as UserItem
                    val intent = Intent(view.context, ChatActivity::class.java)

                    intent.putExtra(USER_KEY, userItem.user)
                    startActivity(intent)
                    finish()
                }

                recyclerView_NewMessage.adapter = adapter
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
}

class UserItem(val user: UserModel): Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position:Int){

        viewHolder.itemView.message_user_name.text = user.name
        if(!user.profilepic.isEmpty()) {
            Picasso.get().load(user.profilepic.toUri())
                //.resize(180, 180)
                .transform(CropCircleTransformation())
                .into(viewHolder.itemView.message_imageIcon)
        }
        else
            viewHolder.itemView.message_imageIcon.setImageResource(R.mipmap.ic_launcher_homer_round)

    }

    override fun getLayout(): Int {
        return R.layout.card_user
    }
}

