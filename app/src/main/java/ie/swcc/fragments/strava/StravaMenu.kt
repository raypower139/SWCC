package ie.swcc.fragments.strava

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import ie.swcc.R
import ie.swcc.main.SWCCApp
import ie.swcc.models.strava.StravaModel
import ie.swcc.utils.createLoader
import ie.swcc.utils.showLoader
import kotlinx.android.synthetic.main.fragment_strava_menu.view.*
import retrofit2.Call
import javax.security.auth.callback.Callback


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
        activity?.title = getString(R.string.welcome_title)


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

        root.strava_segment_button.setOnClickListener {
            val newGamefragment = StravaSegment()
            val fragmentTransaction: FragmentTransaction =
                activity!!.supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.homeFrame, newGamefragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        }

        root.my_efforts_button.setOnClickListener {
            val newGamefragment = MyEfforts()
            val fragmentTransaction: FragmentTransaction =
                activity!!.supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.homeFrame, newGamefragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

        }

        root.buttonGroupOne.setOnClickListener {
           app.groupId = "498435"
            println("Changed to Group One")

        }

        root.buttonGroupTwo.setOnClickListener {
            app.groupId = "498437"
            println("Changed to Group Two")
        }

        root.buttonGroupThree.setOnClickListener {
            app.groupId = "498440"
            println("Changed to Group Three")
        }

        root.buttonGroupFour.setOnClickListener {
            app.groupId = "498452"
            println("Changed to Group Four")
        }



        return root
    }
}






