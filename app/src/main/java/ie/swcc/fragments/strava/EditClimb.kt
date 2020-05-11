package ie.swcc.fragments.strava


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
import ie.swcc.main.SWCCApp
import ie.swcc.models.strava.ClimbModel
import ie.swcc.utils.createLoader
import ie.swcc.utils.hideLoader
import ie.swcc.utils.showLoader
import kotlinx.android.synthetic.main.fragment_edit_climb.*
import kotlinx.android.synthetic.main.fragment_edit_climb.view.*
import kotlinx.android.synthetic.main.fragment_edit_climb.view.my_efforts_button2_MahonFalls
import kotlinx.android.synthetic.main.fragment_edit_climb.view.my_efforts_button2_Mt_Leinster
import kotlinx.android.synthetic.main.fragment_edit_climb.view.my_efforts_button2_SeskinHill
import kotlinx.android.synthetic.main.fragment_edit_climb.view.updateStravaButton
import kotlinx.android.synthetic.main.fragment_strava_menu.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle


class EditClimb : Fragment(), AnkoLogger {

    lateinit var app: SWCCApp
    lateinit var loader : AlertDialog
    lateinit var root: View
    var editPost: ClimbModel? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as SWCCApp

        arguments?.let {
            editPost = it.getParcelable("editclimb")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_edit_climb, container, false)
        activity?.title = getString(R.string.action_edit)
        loader = createLoader(activity!!)

        root.edit_checkBox_MahonFalls.isChecked = editPost!!.MahonFalls
        root.edit_checkBox_Mt_Leinster.isChecked = editPost!!.MtLeinster
        root.edit_checkBox_SeskinHill.isChecked = editPost!!.SeskinHill


        root.updateStravaButton.setOnClickListener {
            showLoader(loader, "Updating Post on Server...")
            updatePostData()
            updatePost(editPost!!.uid, editPost!!)
//            updateUserPost(app.auth.currentUser!!.uid,
//                               editPost!!.uid, editPost!!)
        }

        // Buttons for Strava Segment MYEFFORTS
        root.my_efforts_button2_MahonFalls.setOnClickListener {
            app.segmentId = "623750"
            app.segmentName = "Mahon Falls"
            println("Changed to Mahon Falls")
            val newGamefragment = MyEfforts()
            val fragmentTransaction: FragmentTransaction =
                activity!!.supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.homeFrame, newGamefragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        root.my_efforts_button2_SeskinHill.setOnClickListener {
            app.segmentId = "623748"
            println("Changed to SeskinHill")
            app.segmentName = "Seskin Hill"
            val newGamefragment = MyEfforts()
            val fragmentTransaction: FragmentTransaction =
                activity!!.supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.homeFrame, newGamefragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        root.my_efforts_button2_Mt_Leinster.setOnClickListener {
            app.segmentId = "4374283"
            println("Changed to Mt.Leinster")
            app.segmentName = "Mt.Leinster"
            val newGamefragment = MyEfforts()
            val fragmentTransaction: FragmentTransaction =
                activity!!.supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.homeFrame, newGamefragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }



        root.updateStravaButton.setVisibility(View.INVISIBLE) //To set visible
        if(app.auth.currentUser!!.displayName == editPost!!.Name) {
            root.updateStravaButton.setVisibility(View.VISIBLE) //To set visible
        }

        return root
    }

    companion object {
        @JvmStatic
        fun newInstance(climb: ClimbModel) =
            EditClimb().apply {
                arguments = Bundle().apply {
                    putParcelable("editclimb",climb)
                }
            }
    }

    fun updatePostData() {
        editPost!!.MahonFalls = root.edit_checkBox_MahonFalls.isChecked
        editPost!!.MtLeinster = root.edit_checkBox_Mt_Leinster.isChecked
        editPost!!.SeskinHill = root.edit_checkBox_SeskinHill.isChecked

        val zonedToday = ZonedDateTime.now(ZoneId.of("Europe/Dublin")).toLocalDate()
        val zonedFormattedToday = zonedToday.format(
            DateTimeFormatter.ofLocalizedDate(
                FormatStyle.FULL))
        val date = zonedFormattedToday
        editPost!!.LastUpdated = date
    }

//    fun updateUserPost(userId: String, uid: String?, post: ClimbModel) {
//        app.database.child("user-climbs").child(userId).child(uid!!)
//            .addListenerForSingleValueEvent(
//                object : ValueEventListener {
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        snapshot.ref.setValue(post)
//                        activity!!.supportFragmentManager.beginTransaction()
//                        .replace(R.id.homeFrame,
//                            ReportAllClimbsFragment.newInstance()
//                        )
//                        .addToBackStack(null)
//                        .commit()
//                        hideLoader(loader)
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//                        info("Firebase Update User Post error : ${error.message}")
//                    }
//                })
//    }

    fun updatePost(uid: String?, climb: ClimbModel) {
        app.database.child("climbs").child(uid!!)
            .addListenerForSingleValueEvent(
                object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.ref.setValue(climb)
                        hideLoader(loader)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        info("Firebase Update Post error : ${error.message}")
                    }
                })
    }

}
