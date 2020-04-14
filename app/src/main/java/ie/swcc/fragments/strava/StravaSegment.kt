package ie.swcc.fragments.strava


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager

import ie.swcc.R
import ie.swcc.adapters.StravaSegmentAdapter
import ie.swcc.main.SWCCApp
import ie.swcc.models.strava.Entry
import ie.swcc.models.strava.StravaSegmentModel
import ie.swcc.utils.*
import kotlinx.android.synthetic.main.fragment_strava_segments.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StravaSegment : Fragment(), Callback<List<StravaSegmentModel>>, AnkoLogger {

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
        var root = inflater.inflate(R.layout.fragment_strava_segments, container, false)
        activity?.title = getString(R.string.action_strava_segments)
        loader = createLoader(activity!!)

        root.recyclerView3.setLayoutManager(LinearLayoutManager(activity))
        root.recyclerView3.adapter = StravaSegmentAdapter(app.stravaStore.findAllSegmentEfforts())

        return root
    }



    override fun onResponse(call: Call<List<StravaSegmentModel>>,
                            response: Response<List<StravaSegmentModel>>
    ) {
        serviceAvailableMessage(activity!!)
        info("Retrofit JSON = $response.raw()")
        app.stravaStore.segmentEfforts = response.body() as ArrayList<StravaSegmentModel>
        //updateUI()
        hideLoader(loader)
    }

    override fun onFailure(call: Call<List<StravaSegmentModel>>, t: Throwable) {
        info("Retrofit Error : $t.message")
        serviceUnavailableMessage(activity!!)
        hideLoader(loader)
    }


    override fun onResume() {
        super.onResume()
        getAllSegmentEfforts()
    }
    fun getAllSegmentEfforts() {
        showLoader(loader, "Downloading Segment Efforts")
        var callGetAll = app.stravaService.getallSegmentEfforts()
        callGetAll.enqueue(this)

    }




    companion object {
        @JvmStatic
        fun newInstance() =
            StravaSegment().apply {
                arguments = Bundle().apply { }
            }
    }




}


