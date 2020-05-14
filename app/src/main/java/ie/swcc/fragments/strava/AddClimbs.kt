package ie.swcc.fragments.strava

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import ie.swcc.R
import ie.swcc.main.SWCCApp
import ie.swcc.models.strava.ClimbModel
import ie.swcc.utils.createLoader
import ie.swcc.utils.hideLoader
import ie.swcc.utils.showLoader
import kotlinx.android.synthetic.main.fragment_add_climbs.view.*
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.android.synthetic.main.fragment_strava_menu.*
import kotlinx.android.synthetic.main.fragment_strava_menu.view.*
import kotlinx.android.synthetic.main.fragment_strava_menu.view.my_efforts_button_MahonFalls
import kotlinx.android.synthetic.main.fragment_strava_menu.view.my_efforts_button_Mt_Leinster
import kotlinx.android.synthetic.main.fragment_strava_menu.view.my_efforts_button_SeskinHill

import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.HashMap


class AddClimbs : Fragment() {

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
            AddClimbs().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_add_climbs, container, false)
        loader = createLoader(activity!!)
        activity?.title = "MRA Challenge"


       // Buttons for Strava Segment MYEFFORTS
        root.my_efforts_button_MahonFalls.setOnClickListener {
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

        root.my_efforts_button_SeskinHill.setOnClickListener {
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

        root.my_efforts_button_Mt_Leinster.setOnClickListener {
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

        root.saveStravaButton.setOnClickListener {
            val Name = app.auth.currentUser!!.displayName
            val MahonFalls = root.checkBox_MahonFalls.isChecked
            val SeskinHill = root.checkBox_SeskinHill.isChecked
            val MtLeinster = root.checkBox_Mt_Leinster.isChecked
            val LastUpdated = ZonedDateTime.now(ZoneId.of("Europe/Dublin")).toLocalDate().toString()
            writeNewClimb(
                ClimbModel(
                    Name = Name,
                    MahonFalls = MahonFalls,
                    SeskinHill = SeskinHill,
                    MtLeinster = MtLeinster,
                    LastUpdated = LastUpdated
                )
            )
        }

        return root
    }

    override fun onPause() {
        super.onPause()
        app.database.child("climbs")
            .child(app.auth.currentUser!!.uid)
        //.removeEventListener(eventListener)
    }

    fun writeNewClimb(climb: ClimbModel) {
        // Create new record of the users climbs at /climbs
        showLoader(loader, "Adding Climbs to Firebase")
        //info("Firebase DB Reference : $app.database")
        val uid = app.auth.currentUser!!.uid
        val key = app.database.child("climbs").push().key
        if (key == null) {
            //info("Firebase Error : Key Empty")
            return
        }
        climb.uid = key
        val climbValues = climb.toMap()

        val childUpdates = HashMap<String, Any>()
        childUpdates["/climbs/$key"] = climbValues

        app.database.updateChildren(childUpdates)
        hideLoader(loader)
    }


}






