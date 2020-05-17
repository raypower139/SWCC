package ie.swcc.fragments.strava

import android.content.Intent
import android.content.Intent.getIntent
import android.content.Intent.parseUri
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import ie.swcc.R
import ie.swcc.activities.Home
import ie.swcc.main.SWCCApp
import ie.swcc.models.strava.ClimbModel
import ie.swcc.utils.createLoader
import ie.swcc.utils.hideLoader
import ie.swcc.utils.showLoader
import kotlinx.android.synthetic.main.fragment_strava_menu.view.*
import java.util.*


class StravaMenu : Fragment() {

    lateinit var app: SWCCApp
    lateinit var root: View
    lateinit var loader: AlertDialog





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as SWCCApp

//        val intentUri = Uri.parse("https://www.strava.com/oauth/mobile/authorize")
//            .buildUpon()
//            .appendQueryParameter("client_id", "37817")
//            .appendQueryParameter("redirect_uri", "https://swcc.ie/callback")
//            .appendQueryParameter("response_type", "code")
//            .appendQueryParameter("approval_prompt", "auto")
//            .appendQueryParameter("scope", "activity:read")
//            .build()
//
//        val intent = Intent(Intent.ACTION_VIEW, intentUri)
//        startActivity(intent)

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

        root.imageView2.setOnClickListener{
                    val intentUri = Uri.parse("https://www.strava.com/oauth/mobile/authorize")
            .buildUpon()
            .appendQueryParameter("client_id", "37817")
            .appendQueryParameter("redirect_uri", "https://swcc.ie/callback")
            .appendQueryParameter("response_type", "code")
            .appendQueryParameter("approval_prompt", "auto")
            .appendQueryParameter("scope", "activity:read")
            .build()

        val intent = Intent(Intent.ACTION_VIEW, intentUri)
        startActivity(intent)
        }


        root.strava_activities_list.setOnClickListener {
            val newGamefragment = StravaActivities()
            val fragmentTransaction: FragmentTransaction =
                activity!!.supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.homeFrame, newGamefragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }
        root.addClimbs.setOnClickListener {
            val newGamefragment = AddClimbs()
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
            Efforts()
        }
        root.my_efforts_button_SeskinHill.setOnClickListener {
            app.segmentId = "623748"
            println("Changed to SeskinHill")
            app.segmentName = "Seskin Hill"
            Efforts()
        }
        root.my_efforts_button_Mt_Leinster.setOnClickListener {
            app.segmentId = "4374283"
            println("Changed to Mt.Leinster")
            app.segmentName = "Mt.Leinster"
            Efforts()
        }
        root.my_efforts_button_SlieveCoillte.setOnClickListener {
            app.segmentId = "916722"
            app.segmentName = "Slieve Coillte"
            println("Changed to Slieve Coillte")
            Efforts()
        }
        root.my_efforts_button_Vee.setOnClickListener {
            app.segmentId = "17174357"
            println("Changed to The Vee")
            app.segmentName = "The Vee"
            Efforts()
        }
        root.my_efforts_button_Powers_East.setOnClickListener {
            app.segmentId = "623749"
            println("Changed to Powers The Pot (From East)")
            app.segmentName = "Powers The Pot (East)"
            Efforts()
        }
        root.my_efforts_button_Mountain_Road.setOnClickListener {
            app.segmentId = "656892"
            app.segmentName = "Mountain Road"
            println("Changed to Mountain Road")
            Efforts()
        }
        root.my_efforts_button_Slieve_Na_mBan.setOnClickListener {
            app.segmentId = "656136"
            println("Changed to Slieve Na mBan")
            app.segmentName = "Slieve Na mBan"
            Efforts()
        }
        root.my_efforts_button_Powers_West.setOnClickListener {
            app.segmentId = "623749"
            println("Changed to Powers The Pot (West)")
            app.segmentName = "Powers The Pot (West)"
            Efforts()
        }
        root.my_efforts_button_Tickincor.setOnClickListener {
            app.segmentId = "656146"
            println("Changed to Tickincor")
            app.segmentName = "Tickincor"
            Efforts()
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
        root.strava_segment_SlieveCoillte.setOnClickListener {
            app.segmentId = "916722"
            println("Changed to Slieve Coillte")
            app.segmentName = "Slieve Coillte"
            Leaderboard()
        }
        root.strava_segment_button_Vee.setOnClickListener {
            app.segmentId = "17174357"
            println("Changed to The Vee")
            app.segmentName = "The Vee"
            Leaderboard()
        }
        root.strava_segment_button_Powers_East.setOnClickListener {
            app.segmentId = "623749"
            app.segmentName = "Powers The Pot East"
            println("Changed to Powers The Pot East")
            Leaderboard()
        }
        root.strava_segment_button_Mountain_Road.setOnClickListener {
            app.segmentId = "656892"
            println("Changed to Mountain Road")
            app.segmentName = "Mountain Road"
            Leaderboard()
        }
        root.strava_segment_Slieve_Na_mBan.setOnClickListener {
            app.segmentId = "656136"
            println("Changed to Slieve Na mBan")
            app.segmentName = "Slieve Na mBan"
            Leaderboard()
        }
        root.strava_segment_button_Powers_West.setOnClickListener {
            app.segmentId = "623749"
            println("Changed to Powers The Pot (West)")
            app.segmentName = "Powers The Pot (West)"
            Leaderboard()
        }
        root.strava_segment_button_Tickincor.setOnClickListener {
            app.segmentId = "656146"
            println("Changed to Tickincor")
            app.segmentName = "Tickincor"
            Leaderboard()
        }



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
    fun Efforts() {
        val newGamefragment = MyEfforts()
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


    override fun onResume() {
        super.onResume()
        val uri = activity?.intent?.data
        print(uri)
    }


}






