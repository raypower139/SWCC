package ie.swcc.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import ie.swcc.R
import ie.swcc.activities.chat.ChatActivity
import ie.swcc.activities.chat.LatestMessagesActivity
import ie.swcc.fragments.blog.BlogFragment
import ie.swcc.fragments.blog.ProfileFragment
import ie.swcc.fragments.blog.ReportAllFragment
import ie.swcc.fragments.strava.StravaFragment
import ie.swcc.main.SWCCApp
import ie.swcc.models.UserModel
import ie.swcc.utils.createLoader
import ie.swcc.utils.hideLoader
import kotlinx.android.synthetic.main.fragment_menu.view.*
import kotlinx.android.synthetic.main.profile.view.*
import org.jetbrains.anko.startActivity


class MenuFragment : Fragment() {

    lateinit var app: SWCCApp
    lateinit var root: View
    lateinit var loader: AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as SWCCApp

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MenuFragment().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_menu, container, false)
        loader = createLoader(activity!!)
        activity?.title = getString(R.string.welcome_title)

        root.newsImage.setOnClickListener {
            val newGamefragment = ReportAllFragment()
            val fragmentTransaction: FragmentTransaction =
                activity!!.supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.homeFrame, newGamefragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        root.chatImage.setOnClickListener {
            val intent = Intent (getActivity(), LatestMessagesActivity::class.java)
            getActivity()!!.startActivity(intent)
        }

        root.stravaImage.setOnClickListener {
            val newGamefragment = StravaFragment()
            val fragmentTransaction: FragmentTransaction =
                activity!!.supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.homeFrame, newGamefragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        root.profileImage.setOnClickListener {
            val newGamefragment = ProfileFragment()
            val fragmentTransaction: FragmentTransaction =
                activity!!.supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.homeFrame, newGamefragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        val userId = app.auth.currentUser!!.uid
        val userProfileRef = app.database.child("user-photos").child(userId!!)
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    //info("Firebase Post error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    hideLoader(loader)

                    val user = snapshot.getValue<UserModel>(UserModel::class.java)
                    root.welcome_name.setText(app.auth.currentUser?.displayName)


                    app.database.child("user-photos").child(userId)
                        .removeEventListener(this)
                }

            })
        return root;
    }
}



