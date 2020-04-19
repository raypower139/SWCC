package ie.swcc.fragments.strava


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ie.swcc.R
import ie.swcc.adapters.StravaStatsAdapter
import ie.swcc.api.StatsWrapper
import ie.swcc.api.StravaWrapper
import ie.swcc.main.SWCCApp
import ie.swcc.models.strava.athleteStats.AthleteStatsModel
import ie.swcc.models.strava.athleteStats.YtdRideTotals
import ie.swcc.utils.*
import kotlinx.android.synthetic.main.fragment_strava_stats.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class StravaStats() : Fragment(), Callback<StatsWrapper>, AnkoLogger {

    lateinit var app: SWCCApp
    lateinit var loader : AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as SWCCApp
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var root = inflater.inflate(R.layout.fragment_strava_stats, container, false)
        activity?.title = getString(R.string.action_strava_segments)
        loader = createLoader(activity!!)
        root.recyclerView5.setLayoutManager(LinearLayoutManager(activity))
        root.recyclerView5.adapter = StravaStatsAdapter(app.stravaStore.findMyStats())

        return root
    }

    override fun onResponse(
        call: Call<StatsWrapper>,
        response: Response<StatsWrapper>
    ) {
        serviceAvailableMessage(activity!!)
        info("Retrofit JSON = $response.raw()")
        app.stravaStore.myStats = response.body()?.ytdRideTotals!!
        hideLoader(loader)
    }

    override fun onFailure(call: Call<StatsWrapper>, t: Throwable) {
        info("Retrofit Error : $t.message")
        serviceUnavailableMessage(activity!!)
        hideLoader(loader)
    }


    fun getMyStats() {
        showLoader(loader, "Downloading Segment Efforts")
        var callGetAll = app.stravaService.getMyStats()
        callGetAll.enqueue(this)

    }

    override fun onResume() {
        super.onResume()
        getMyStats()
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            StravaStats().apply {
                arguments = Bundle().apply { }
            }

    }
}




