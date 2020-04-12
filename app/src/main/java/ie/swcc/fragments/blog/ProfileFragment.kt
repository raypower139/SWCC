package ie.swcc.fragments.blog


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.*
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import ie.swcc.main.SWCCApp
import ie.swcc.R
import ie.swcc.activities.chat.ChatActivity.Companion.TAG
import ie.swcc.activities.chat.LatestMessagesActivity.Companion.currentUser
import ie.swcc.adapters.BlogAdapter
import ie.swcc.models.UserModel
import ie.swcc.models.blog.BlogModel
import ie.swcc.utils.*
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.fragment_blogreport.view.*
import kotlinx.android.synthetic.main.fragment_edit.view.*
import kotlinx.android.synthetic.main.home.*
import kotlinx.android.synthetic.main.nav_header_home.*
import kotlinx.android.synthetic.main.nav_header_home.view.*
import kotlinx.android.synthetic.main.profile.*
import kotlinx.android.synthetic.main.profile.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class ProfileFragment : Fragment(), AnkoLogger {

    lateinit var root: View
    var editProfile: UserModel? = null
    lateinit var app: SWCCApp
    lateinit var loader: AlertDialog
    val IMAGE_REQUEST = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as SWCCApp


    }

    companion object {
        @JvmStatic
        fun newInstance() =
            ProfileFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.profile, container, false)
        loader = createLoader(activity!!)
        activity?.title = getString(R.string.action_post)


        val userId = app.auth.currentUser!!.uid
        val userProfileRef = app.database.child("user-photos").child(userId!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    info("Firebase Post error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    hideLoader(loader)

                    val user = snapshot.getValue<UserModel>(UserModel::class.java)
                    val chatUser = FirebaseAuth.getInstance().currentUser

                    if (app.auth.currentUser?.photoUrl != null) {
                        root.editProfileName.setText(app.auth.currentUser?.displayName)
                        //root.editProfileName.setSelection(user!!.displayName.length)
                        Picasso.get().load(app.auth.currentUser?.photoUrl)
                            .resize(600, 400)
                            .into(root.editProfileImage)
                    } else {
                        root.editProfileName.setText(user!!.name)
                        Picasso.get().load(user!!.profilepic.toUri())
                            .resize(600, 400)
                            .into(root.editProfileImage)
                        //root.editProfileImage.setImageResource(R.mipmap.ic_launcher_homer_round)}
                    }

                        app.database.child("user-photos").child(userId)
                            .removeEventListener(this)
                    }

            })

        root.editNameButton.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser

            val profileUpdates = UserProfileChangeRequest.Builder()
                .setDisplayName(editProfileName.text.toString())
                .build()

            user?.updateProfile(profileUpdates)
                ?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "User profile updated.")
                        updateProfile(app, app.userImage.toString(), editProfileName.toString())
                    }
                }
            updateProfile(app, app.userImage.toString(), editProfileName.toString())

        }

        root.resetPasswordButton.setOnClickListener { sendPasswordReset() }

        root.deleteUserButton.setOnClickListener {
           val user = FirebaseAuth.getInstance().currentUser
            user?.delete()
           ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "User account deleted.")
               }
            } }
        //root.editProfileImage.setOnClickListener { showProfileImagePicker(this, 2) }

    root.updateProfileImage.setOnClickListener {

        showProfileImagePicker(this, 2)
        //showLoader(loader, "Updating Profile on Server...")


    }

    return root;

}








    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            2 -> {
                if (data != null) {
                    writeImageRef(app, readImageUri(resultCode, data).toString())
                    Picasso.get().load(readImageUri(resultCode, data).toString())
                        .resize(600, 400)
                        .into(editProfileImage, object : Callback {
                            override fun onSuccess() {
                                // Drawable is ready
                                uploadProfileImageView(app, editProfileImage)

                                // [START_EXCLUDE]
                                hideLoader(loader)
                                // [END_EXCLUDE]
                            }

                            override fun onError(e: Exception) {}
                        })
                }
            }
        }
    }

    private fun sendPasswordReset() {
        // [START send_password_reset]
        val auth = FirebaseAuth.getInstance()
        val emailAddress = app.auth.currentUser!!.email.toString()

        auth.sendPasswordResetEmail(emailAddress)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "Email sent.")
                }
            }
        // [END send_password_reset]
    }


}



