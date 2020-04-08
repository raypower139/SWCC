package ie.swcc.fragments.blog


import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.core.Tag
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import ie.swcc.main.SWCCApp

import ie.swcc.R
import ie.swcc.activities.chat.ChatActivity.Companion.TAG
import ie.swcc.models.blog.BlogModel
import ie.swcc.utils.*
import kotlinx.android.synthetic.main.fragment_add_blogpost.*
import kotlinx.android.synthetic.main.fragment_add_blogpost.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.HashMap


class ProfileFragment : Fragment(), AnkoLogger {

    lateinit var app: SWCCApp
    lateinit var loader : AlertDialog
    lateinit var eventListener : ValueEventListener
    val IMAGE_REQUEST = 2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as SWCCApp

        val user = FirebaseAuth.getInstance().currentUser
        if(user!=null){
            Log.d(TAG, "onCreate" + user.displayName)

        }

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

        //setButtonListener(root)

        return root;

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


