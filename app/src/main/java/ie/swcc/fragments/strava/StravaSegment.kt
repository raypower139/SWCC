package ie.swcc.fragments.strava


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ie.swcc.R
import ie.swcc.adapters.StravaSegmentAdapter
import ie.swcc.api.StravaWrapper
import ie.swcc.main.SWCCApp
import ie.swcc.utils.*
import kotlinx.android.synthetic.main.fragment_strava_segments.*
import kotlinx.android.synthetic.main.fragment_strava_segments.view.*
import kotlinx.android.synthetic.main.fragment_strava_segments.view.recyclerView3
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class StravaSegment() : Fragment(), Callback<StravaWrapper>, AnkoLogger {

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
        activity?.title = app.segmentName
        loader = createLoader(requireActivity())
        root.recyclerView3.setLayoutManager(LinearLayoutManager(activity))
        return root
    }

    override fun onResponse(
        call: Call<StravaWrapper>,
        response: Response<StravaWrapper>
    ) {
        serviceAvailableMessage(requireActivity())
        info("Retrofit JSON = $response.raw()")
        app.stravaStore.segmentEfforts = response.body()!!.entries!!
        hideLoader(loader)
        recyclerView3.adapter = StravaSegmentAdapter(app.stravaStore.findAllSegmentEfforts())
    }

    override fun onFailure(call: Call<StravaWrapper>, t: Throwable) {
        info("Retrofit Error : $t.message")
        serviceUnavailableMessage(requireActivity())
        hideLoader(loader)
    }

    fun getAllSegmentEfforts() {
        showLoader(loader, "Downloading Segment Efforts")
        var callGetAll = app.stravaService.getallSegmentEfforts(app.segmentId,app.access_token,app.groupId,50)
        callGetAll.enqueue(this)

    }

    override fun onResume() {
        super.onResume()
        getAllSegmentEfforts()
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            StravaSegment().apply {
                arguments = Bundle().apply { }
            }
    }
}


