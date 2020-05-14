package ie.swcc.fragments.strava


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import ie.swcc.R
import ie.swcc.adapters.StravaAdapter
import ie.swcc.main.SWCCApp
import ie.swcc.models.strava.StravaModel
import ie.swcc.utils.*
import kotlinx.android.synthetic.main.fragment_strava_report.*
import kotlinx.android.synthetic.main.fragment_strava_report.view.*
import kotlinx.android.synthetic.main.fragment_strava_report.view.recyclerView
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class StravaFragment : Fragment(), Callback<List<StravaModel>>, AnkoLogger {

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
        var root = inflater.inflate(R.layout.fragment_strava_report, container, false)
        activity?.title = app.groupName
        loader = createLoader(activity!!)

        root.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        return root
    }

    override fun onResponse(call: Call<List<StravaModel>>,
                            response: Response<List<StravaModel>>
    ) {
        serviceAvailableMessage(activity!!)
        info("Retrofit JSON = $response.raw()")
        app.stravaStore.members = response.body() as ArrayList<StravaModel>
        hideLoader(loader)
        recyclerView.adapter = StravaAdapter(app.stravaStore.findAll())

    }

    override fun onFailure(call: Call<List<StravaModel>>, t: Throwable) {
        info("Retrofit Error : $t.message")
        serviceUnavailableMessage(activity!!)
        hideLoader(loader)
    }

    override fun onResume() {
        super.onResume()
        getAllMembers()
    }
    fun getAllMembers() {
        showLoader(loader, "Downloading Strava List")
        var callGetAll = app.stravaService.getall(app.groupId,app.access_token,30)
        callGetAll.enqueue(this)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            StravaFragment().apply {
                arguments = Bundle().apply { }
            }
    }
}


