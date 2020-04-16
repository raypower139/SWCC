package ie.swcc.fragments.strava


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ie.swcc.R
import ie.swcc.adapters.MyEffortsAdapter
import ie.swcc.adapters.StravaSegmentAdapter
import ie.swcc.api.StravaWrapper
import ie.swcc.main.SWCCApp
import ie.swcc.models.strava.Entry
import ie.swcc.models.strava.StravaSegmentModel
import ie.swcc.models.strava.mySegmentEfforts.MyEffortsModel
import ie.swcc.utils.*
import kotlinx.android.synthetic.main.fragment_strava_my_efforts.view.*
import kotlinx.android.synthetic.main.fragment_strava_segments.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyEfforts() : Fragment(), Callback<List<MyEffortsModel>>, AnkoLogger {

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
        var root = inflater.inflate(R.layout.fragment_strava_my_efforts, container, false)
        activity?.title = getString(R.string.action_strava_segments)
        loader = createLoader(activity!!)

        root.recyclerView4.setLayoutManager(LinearLayoutManager(activity))
        root.recyclerView4.adapter = MyEffortsAdapter(app.stravaStore.findAllMyEfforts())

        return root
    }



    override fun onResponse(
        call: Call<List<MyEffortsModel>>,
        response: Response<List<MyEffortsModel>>
    ) {
        serviceAvailableMessage(activity!!)
        info("Retrofit JSON = $response.raw()")
        app.stravaStore.myEfforts = response.body() as ArrayList<MyEffortsModel>
        hideLoader(loader)
    }

    override fun onFailure(call: Call<List<MyEffortsModel>>, t: Throwable) {
        info("Retrofit Error : $t.message")
        serviceUnavailableMessage(activity!!)
        hideLoader(loader)
    }

    override fun onResume() {
        super.onResume()
        getAllMyEfforts()
    }
    fun getAllMyEfforts() {
        showLoader(loader, "Downloading My Efforts")
        var callGetAll = app.stravaService.getAllMyEfforts()
        callGetAll.enqueue(this)

    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MyEfforts().apply {
                arguments = Bundle().apply { }
            }
    }
}


