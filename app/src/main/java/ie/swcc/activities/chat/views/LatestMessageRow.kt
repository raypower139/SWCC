package ie.swcc.activities.chat.views

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import ie.swcc.R
import ie.swcc.models.UserModel
import ie.swcc.models.chat.ChatMessageModel
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.latest_message_row.view.*

class LatestMessageRow(val chatMessage: ChatMessageModel): Item<GroupieViewHolder>(){
    var chatPartnerUser: UserModel? = null

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
        ref.addListenerForSingleValueEvent(object: ValueEventListener {

            override fun onDataChange(p0: DataSnapshot) {
                // Get the User Details
                chatPartnerUser = p0.getValue(UserModel::class.java)
                // Display the User's Name
                viewHolder.itemView.username_textview_latest_message.text = chatPartnerUser?.uid
                // Load the user image into the imageview and crop it
                val targetImageView = viewHolder.itemView.imageview_latest_message
                Picasso.get().load(chatPartnerUser?.profilepic)
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