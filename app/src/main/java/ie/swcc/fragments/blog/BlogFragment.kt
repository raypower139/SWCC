package ie.swcc.fragments.blog


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import ie.swcc.main.SWCCApp

import ie.swcc.R
import ie.swcc.models.blog.BlogModel
import ie.swcc.utils.*
import kotlinx.android.synthetic.main.fragment_add_blogpost.*
import kotlinx.android.synthetic.main.fragment_add_blogpost.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.HashMap


class BlogFragment : Fragment(), AnkoLogger {

    lateinit var app: SWCCApp
    lateinit var loader : AlertDialog
    lateinit var eventListener : ValueEventListener
    val IMAGE_REQUEST = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as SWCCApp


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_add_blogpost, container, false)
        loader = createLoader(activity!!)
        activity?.title = getString(R.string.action_post)

        setButtonListener(root)

        return root;

    }



    companion object {
        @JvmStatic
        fun newInstance() =
            BlogFragment().apply {
                arguments = Bundle().apply {}
            }
    }




    fun setButtonListener( layout: View) {
        layout.postButton.setOnClickListener {
            val title = layout.editTitle.text.toString()
            val body = layout.editBody.text.toString()
            val zonedToday = ZonedDateTime.now(ZoneId.of("Europe/Dublin")).toLocalDate()
            val zonedFormattedToday = zonedToday.format(DateTimeFormatter.ofLocalizedDate(
                FormatStyle.FULL))
            val date = zonedFormattedToday
            val posttype = if(layout.postMethod.checkedRadioButtonId == R.id.Spins) "Spins" else "News"
                writeNewBlogPost(
                    BlogModel(
                        title = title,
                        posttype = posttype,
                        body = body,
                        date = date,
                        image = app.image.toString(),
                        profilepic = app.userImage.toString(),
                        email = app.auth.currentUser?.email
                    )
                )
            }

        layout.blogImage.setOnClickListener { Log.d("Photo","Working")
            showBlogImagePicker(this,2)}
        }


    override fun onPause() {
        super.onPause()
        app.database.child("user-posts")
                    .child(app.auth.currentUser!!.uid)
                    //.removeEventListener(eventListener)
    }

    fun writeNewBlogPost(blog: BlogModel) {
        // Create new blog post at /posts & /user-posts/$uid
        showLoader(loader, "Adding Post to Firebase")
        info("Firebase DB Reference : $app.database")
        val uid = app.auth.currentUser!!.uid
        val key = app.database.child("posts").push().key
        if (key == null) {
            info("Firebase Error : Key Empty")
            return
        }
        blog.uid = key
        val blogValues = blog.toMap()

        val childUpdates = HashMap<String, Any>()
        childUpdates["/posts/$key"] = blogValues
        childUpdates["/user-posts/$uid/$key"] = blogValues

        app.database.updateChildren(childUpdates)
        hideLoader(loader)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            2 -> {
                if (data != null) {
                    //writeBlogImageRef(app,readBlogImageUri(resultCode, data).toString())
                    Picasso.get().load(readBlogImageUri(resultCode, data).toString())
                        .resize(600, 400)
                        .into(blogImage, object : Callback {
                            override fun onSuccess() {
                                // Drawable is ready
                                uploadBlogImageView(app,blogImage)
                            }
                            override fun onError(e: Exception) {}
                        })
                }
            }
        }
    }



}


