package ie.swcc.fragments


import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import ie.swcc.main.SWCCApp

import ie.swcc.R
import ie.swcc.models.BlogModel
import ie.swcc.utils.*
import kotlinx.android.synthetic.main.fragment_add_blogpost.*
import kotlinx.android.synthetic.main.fragment_add_blogpost.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import java.lang.String.format
import java.util.HashMap


class BlogFragment : Fragment(), AnkoLogger {

    lateinit var app: SWCCApp
    lateinit var loader : AlertDialog
    lateinit var eventListener : ValueEventListener
    val IMAGE_REQUEST = 1
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
            val posttype = if(layout.postMethod.checkedRadioButtonId == R.id.Spins) "Spins" else "News"
                writeNewDonation(BlogModel(title = title, posttype = posttype, body = body, profilepic = app.userImage.toString(),
                                               email = app.auth.currentUser?.email))
            }
        }


    override fun onPause() {
        super.onPause()
        app.database.child("user-posts")
                    .child(app.auth.currentUser!!.uid)
                    //.removeEventListener(eventListener)
    }

    fun writeNewDonation(donation: BlogModel) {
        // Create new donation at /donations & /donations/$uid
        showLoader(loader, "Adding Post to Firebase")
        info("Firebase DB Reference : $app.database")
        val uid = app.auth.currentUser!!.uid
        val key = app.database.child("posts").push().key
        if (key == null) {
            info("Firebase Error : Key Empty")
            return
        }
        donation.uid = key
        val donationValues = donation.toMap()

        val childUpdates = HashMap<String, Any>()
        childUpdates["/posts/$key"] = donationValues
        childUpdates["/user-posts/$uid/$key"] = donationValues

        app.database.updateChildren(childUpdates)
        hideLoader(loader)
    }






}
