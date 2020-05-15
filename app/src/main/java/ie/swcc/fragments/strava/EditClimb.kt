package ie.swcc.fragments.strava


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import ie.swcc.R
import ie.swcc.main.SWCCApp
import ie.swcc.models.strava.ClimbModel
import ie.swcc.utils.createLoader
import ie.swcc.utils.hideLoader
import ie.swcc.utils.showLoader
import kotlinx.android.synthetic.main.fragment_edit_climb.view.*
import kotlinx.android.synthetic.main.fragment_edit_climb.view.updateStravaButton
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.time.ZoneId
import java.time.ZonedDateTime



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

        root.checkBox_edit_MahonFalls.isChecked = editPost!!.MahonFalls
        root.checkBox_edit_Mt_Leinster.isChecked = editPost!!.MtLeinster
        root.checkBox_edit_SeskinHill.isChecked = editPost!!.SeskinHill
        root.checkBox_edit_Slieve_Coillte.isChecked = editPost!!.SlieveCoillte
        root.checkBox_edit_Vee.isChecked = editPost!!.Vee
        root.checkBox_edit_Powers_East.isChecked = editPost!!.PowersEast
        root.checkBox_edit_Mountain_Road.isChecked = editPost!!.MountainRoad
        root.checkBox_edit_Slieve_Na_mBan.isChecked = editPost!!.SlieveNamBan
        root.checkBox_edit_Powers_West.isChecked = editPost!!.PowersWest
        root.checkBox_edit_Tickincor.isChecked = editPost!!.Tickincor


        root.updateStravaButton.setOnClickListener {
            showLoader(loader, "Updating Post on Server...")
            updatePostData()
            updatePost(editPost!!.uid, editPost!!)
        }

        root.updateStravaButton.setVisibility(View.INVISIBLE) //To set visible
        if(app.auth.currentUser!!.displayName == editPost!!.Name) {
            root.updateStravaButton.setVisibility(View.VISIBLE) //To set visible
            root.strava_menu_choose_group_text.setText("Welcome")
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
        editPost!!.MahonFalls = root.checkBox_edit_MahonFalls.isChecked
        editPost!!.MtLeinster = root.checkBox_edit_Mt_Leinster.isChecked
        editPost!!.SeskinHill = root.checkBox_edit_SeskinHill.isChecked
        editPost!!.MahonFalls = root.checkBox_edit_MahonFalls.isChecked
        editPost!!.MtLeinster = root.checkBox_edit_Mt_Leinster.isChecked
        editPost!!.SeskinHill = root.checkBox_edit_SeskinHill.isChecked
        editPost!!.MahonFalls = root.checkBox_edit_MahonFalls.isChecked
        editPost!!.MtLeinster = root.checkBox_edit_Mt_Leinster.isChecked
        editPost!!.SeskinHill = root.checkBox_edit_SeskinHill.isChecked
        editPost!!.SeskinHill = root.checkBox_edit_SeskinHill.isChecked

        val zonedToday = ZonedDateTime.now(ZoneId.of("Europe/Dublin")).toLocalDate().toString()
        editPost!!.LastUpdated = zonedToday
    }

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
