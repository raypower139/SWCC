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
import kotlinx.android.synthetic.main.fragment_viewblog.*
import kotlinx.android.synthetic.main.fragment_edit.view.*
import kotlinx.android.synthetic.main.fragment_edit.view.editBody
import kotlinx.android.synthetic.main.fragment_edit.view.editNews
import kotlinx.android.synthetic.main.fragment_edit.view.editPostType
import kotlinx.android.synthetic.main.fragment_edit.view.editSpins
import kotlinx.android.synthetic.main.fragment_edit.view.editTitle
import kotlinx.android.synthetic.main.fragment_edit.view.editUpdateButton
import kotlinx.android.synthetic.main.fragment_viewblog.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class ViewBlogFragment : Fragment(), AnkoLogger {

    lateinit var app: SWCCApp
    lateinit var loader : AlertDialog
    lateinit var root: View
    var viewPost: BlogModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as SWCCApp

        arguments?.let {
            viewPost = it.getParcelable("viewpost")
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_viewblog, container, false)
        activity?.title = getString(R.string.action_edit)
        loader = createLoader(activity!!)
        root.viewBlogTitle.setText(viewPost!!.title)
        root.viewBlogBody.setText(viewPost!!.body)


        if(!viewPost!!.image.isEmpty()) {
            Picasso.get().load(viewPost!!.image.toUri())
                .resize(600, 400)
                .into(root.viewBlogImage)
        }
        else
            root.viewBlogImage.setImageResource(R.mipmap.ic_launcher_homer_foreground)


        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(post: BlogModel) =
            ViewBlogFragment().apply {
                arguments = Bundle().apply {
                    putParcelable("viewpost",post)
                }
            }
    }

}
