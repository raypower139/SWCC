package ie.swcc.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

import ie.swcc.R
import ie.swcc.main.SWCCApp
import ie.swcc.models.BlogModel
import ie.swcc.utils.createLoader
import ie.swcc.utils.hideLoader
import ie.swcc.utils.showLoader
import kotlinx.android.synthetic.main.fragment_edit.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class EditFragment : Fragment(), AnkoLogger {

    lateinit var app: SWCCApp
    lateinit var loader : AlertDialog
    lateinit var root: View
    var editPost: BlogModel? = null

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
        root.editPosttype.setText(editPost!!.posttype)
        root.editMessage.setText(editPost!!.message)
        root.editUpvotes.setText(editPost!!.upvotes.toString())

        root.editUpdateButton.setOnClickListener {
            showLoader(loader, "Updating Post on Server...")
            updatePostData()
            updatePost(editPost!!.uid, editPost!!)
            updateUserPost(app.auth.currentUser!!.uid,
                               editPost!!.uid, editPost!!)
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
        editPost!!.message = root.editMessage.text.toString()
        editPost!!.upvotes = root.editUpvotes.text.toString().toInt()
    }

    fun updateUserPost(userId: String, uid: String?, post: BlogModel) {
        app.database.child("user-posts").child(userId).child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.setValue(post)
                        activity!!.supportFragmentManager.beginTransaction()
                        .replace(R.id.homeFrame, ReportFragment.newInstance())
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
}
