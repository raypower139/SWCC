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
import ie.swcc.utils.createLoader
import kotlinx.android.synthetic.main.fragment_strava_menu.*
import kotlinx.android.synthetic.main.fragment_strava_menu.view.*


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


        root.strava_members_list.setOnClickListener {
            val newGamefragment = StravaFragment()
            val fragmentTransaction: FragmentTransaction =
                activity!!.supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.homeFrame, newGamefragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        }

        root.strava_activities_list.setOnClickListener {
            val newGamefragment = StravaActivities()
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
            strava_menu_choose_group_text.setText("Group 1 Selected")
            strava_menu_choose_group_text.setTextColor(Color.parseColor("#bf0000"))
        }

        root.buttonGroupTwo.setOnClickListener {
            app.groupId = "498437"
            app.groupName = "Group Two"
            println("Changed to Group Two")
            strava_menu_choose_group_text.setText("Group 2 Selected")
            strava_menu_choose_group_text.setTextColor(Color.parseColor("#bf0000"))
        }

        root.buttonGroupThree.setOnClickListener {
            app.groupId = "498440"
            app.groupName = "Group Three"
            println("Changed to Group Three")
            strava_menu_choose_group_text.setText("Group 3 Selected")
            strava_menu_choose_group_text.setTextColor(Color.parseColor("#bf0000"))
        }

        root.buttonGroupFour.setOnClickListener {
            app.groupId = "498452"
            app.groupName = "Group Four"
            println("Changed to Group Four")
            strava_menu_choose_group_text.setText("Group 4 Selected")
            strava_menu_choose_group_text.setTextColor(Color.parseColor("#bf0000"))
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
            val newGamefragment = StravaSegment()
            val fragmentTransaction: FragmentTransaction =
                activity!!.supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.homeFrame, newGamefragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        root.strava_segment_button_SeskinHill.setOnClickListener {
            app.segmentId = "623748"
            println("Changed to SeskinHill")
            app.segmentName = "Seskin Hill"
            val newGamefragment = StravaSegment()
            val fragmentTransaction: FragmentTransaction =
                activity!!.supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.homeFrame, newGamefragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }

        root.strava_segment_button_Mt_Leinster.setOnClickListener {
            app.segmentId = "4374283"
            println("Changed to Mt.Leinster")
            app.segmentName = "Mt.Leinster"
            val newGamefragment = StravaSegment()
            val fragmentTransaction: FragmentTransaction =
                activity!!.supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.homeFrame, newGamefragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()
        }


        return root
    }


}






