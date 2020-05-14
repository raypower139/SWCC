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
import kotlinx.android.synthetic.main.fragment_strava_menu.*
import kotlinx.android.synthetic.main.fragment_strava_menu.view.*
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.HashMap


class StravaMenu : Fragment() {

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
            StravaMenu().apply {
                arguments = Bundle().apply {}
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_strava_menu, container, false)
        loader = createLoader(activity!!)
        activity?.title = "Strava Menu"

        root.strava_activities_list.setOnClickListener {
            val newGamefragment = StravaActivities()
            val fragmentTransaction: FragmentTransaction =
                activity!!.supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.homeFrame, newGamefragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        root.addClimbs.setOnClickListener {
            val newGamefragment = StravaStats()
            val fragmentTransaction: FragmentTransaction =
                activity!!.supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.homeFrame, newGamefragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        }
        root.button_strava_view_climbs.setOnClickListener {
            val newGamefragment = ReportAllClimbsFragment()
            val fragmentTransaction: FragmentTransaction =
                activity!!.supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.homeFrame, newGamefragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        }




        root.buttonGroupOne.setOnClickListener {
           app.groupId = "498435"
            app.groupName = "Group One"
            println("Changed to Group One")
            Members()
        }

        root.buttonGroupTwo.setOnClickListener {
            app.groupId = "498437"
            app.groupName = "Group Two"
            println("Changed to Group Two")
            Members()
        }

        root.buttonGroupThree.setOnClickListener {
            app.groupId = "498440"
            app.groupName = "Group Three"
            println("Changed to Group Three")
            Members()
        }

        root.buttonGroupFour.setOnClickListener {
            app.groupId = "498452"
            app.groupName = "Group Four"
            println("Changed to Group Four")
            Members()
        }

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


        // Leaderboard Buttons
        root.strava_segment_button.setOnClickListener {
            app.segmentId = "623750"
            app.segmentName = "Mahon Falls"
            println("Changed to Mahon Falls")
            Leaderboard()
        }

        root.strava_segment_button_SeskinHill.setOnClickListener {
            app.segmentId = "623748"
            println("Changed to SeskinHill")
            app.segmentName = "Seskin Hill"
            Leaderboard()
        }

        root.strava_segment_button_Mt_Leinster.setOnClickListener {
            app.segmentId = "4374283"
            println("Changed to Mt.Leinster")
            app.segmentName = "Mt.Leinster"
            Leaderboard()
        }

//        root.saveStravaButton.setOnClickListener {
//            val MahonFalls = root.checkBox_MahonFalls.isChecked
//            val SeskinHill = root.checkBox_SeskinHill.isChecked
//            val MtLeinster = root.checkBox_Mt_Leinster.isChecked
//            val LastUpdated = ZonedDateTime.now(ZoneId.of("Europe/Dublin")).toLocalDate().toString()
//            writeNewClimb(
//                ClimbModel(
//                    MahonFalls = MahonFalls,
//                    SeskinHill = SeskinHill,
//                    MtLeinster = MtLeinster,
//                    LastUpdated = LastUpdated
//                )
//            )
//        }

        return root
    }


    fun Members() {
        val newGamefragment = StravaFragment()
        val fragmentTransaction: FragmentTransaction =
            activity!!.supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.homeFrame, newGamefragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
    fun Leaderboard() {
        val newGamefragment = StravaSegment()
        val fragmentTransaction: FragmentTransaction =
            activity!!.supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.homeFrame, newGamefragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
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






