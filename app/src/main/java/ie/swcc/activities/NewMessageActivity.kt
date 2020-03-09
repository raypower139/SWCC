package ie.swcc.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import ie.swcc.R
import ie.swcc.models.UserPhotoModel
import ie.swcc.activities.Login.*
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.activity_new_message.*
import kotlinx.android.synthetic.main.card_donation.view.*
import kotlinx.android.synthetic.main.card_user.view.*

class NewMessageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_message)
        supportActionBar?.title = "Select User"


fetchusers()

    }

    private fun fetchusers(){
        val ref = FirebaseDatabase.getInstance().getReference("/user-photos")
        ref.addListenerForSingleValueEvent(object : ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                val adapter = GroupAdapter<GroupieViewHolder>()
                p0.children.forEach{
                    Log.d("NewMessage", it.toString())
                    val user = it.getValue(UserPhotoModel::class.java)
                    adapter.add(UserItem(user!!))
                    }
                recyclerView_NewMessage.adapter = adapter
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        })
    }
}

class UserItem(val user: UserPhotoModel): Item<GroupieViewHolder>(){
    override fun bind(viewHolder: GroupieViewHolder, position:Int){
        viewHolder.itemView.message_user_name.text = user.uid
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

