package ie.swcc.fragments.blog


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

import ie.swcc.R
import ie.swcc.main.SWCCApp
import ie.swcc.models.blog.BlogModel
import ie.swcc.utils.*
import kotlinx.android.synthetic.main.fragment_edit.*
import kotlinx.android.synthetic.main.fragment_edit.view.*
import kotlinx.android.synthetic.main.fragment_edit.view.editBody
import kotlinx.android.synthetic.main.fragment_edit.view.editTitle
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class EditFragment : Fragment(), AnkoLogger {

    lateinit var app: SWCCApp
    lateinit var loader : AlertDialog
    lateinit var root: View
    var editPost: BlogModel? = null
    val IMAGE_REQUEST = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as SWCCApp

        arguments?.let {
            editPost = it.getParcelable("editpost")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_edit, container, false)
        activity?.title = getString(R.string.action_edit)
        loader = createLoader(activity!!)
        root.editTitle.setText(editPost!!.title)
        root.editBody.setText(editPost!!.body)


        if(!editPost!!.image.isEmpty()) {
            Picasso.get().load(editPost!!.image.toUri())
                .resize(600, 400)
                .into(root.editBlogImage)
        }
        else
            root.editBlogImage.setImageResource(R.mipmap.ic_launcher_homer_round)

        // Set the Radio Button to the saved value
        if(editPost!!.posttype=="News"){
        root.editNews.setChecked(true)}
        else{root.editSpins.setChecked(true)}



        root.editUpdateButton.setOnClickListener {
            showLoader(loader, "Updating Post on Server...")
            updatePostData()
            updatePost(editPost!!.uid, editPost!!)
            updateUserPost(app.auth.currentUser!!.uid,
                               editPost!!.uid, editPost!!)
        }

        root.editBlogImage.setOnClickListener { Log.d("Photo","Working")
            showEditBlogImagePicker(this,3)
        }

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(post: BlogModel) =
            EditFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("editpost",post)
                }
            }
    }

    fun updatePostData() {
        editPost!!.title = root.editTitle.text.toString()
        editPost!!.body = root.editBody.text.toString()
        editPost!!.image = app.image.toString()
        editPost!!.posttype = if(root.editPostType.checkedRadioButtonId == R.id.Spins) "Spins" else "News"

        val zonedToday = ZonedDateTime.now(ZoneId.of("Europe/Dublin")).toLocalDate()
        val zonedFormattedToday = zonedToday.format(
            DateTimeFormatter.ofLocalizedDate(
                FormatStyle.FULL))
        val date = zonedFormattedToday
        editPost!!.date = date
    }

    fun updateUserPost(userId: String, uid: String?, post: BlogModel) {
        app.database.child("user-posts").child(userId).child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.setValue(post)
                        activity!!.supportFragmentManager.beginTransaction()
                        .replace(R.id.homeFrame,
                            ReportFragment.newInstance()
                        )
                        .addToBackStack(null)
                        .commit()
                        hideLoader(loader)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Update User Post error : ${error.message}")
                    }
                })
    }

    fun updatePost(uid: String?, post: BlogModel) {
        app.database.child("posts").child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.setValue(post)
                        hideLoader(loader)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Update Post error : ${error.message}")
                    }
                })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            3 -> {
                if (data != null) {
                    //writeBlogImageRef(app,readBlogImageUri(resultCode, data).toString())
                    Picasso.get().load(readEditBlogImageUri(resultCode, data).toString())
                        .resize(600, 400)
                        .into(editBlogImage, object : Callback {
                            override fun onSuccess() {
                                // Drawable is ready
                                uploadEditBlogImageView(app,editBlogImage)
                            }
                            override fun onError(e: Exception) {}
                        })
                }
            }
        }
    }
}
