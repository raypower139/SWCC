package ie.wit.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager

import ie.wit.R
import ie.wit.adapters.StravaAdapter
import ie.wit.main.SWCCApp
import ie.wit.models.StravaModel
import ie.wit.utils.*
import kotlinx.android.synthetic.main.fragment_report.view.*
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
        var root = inflater.inflate(R.layout.fragment_report, container, false)
        activity?.title = getString(R.string.action_report)
        loader = createLoader(activity!!)

        root.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        root.recyclerView.adapter = StravaAdapter(app.stravaStore.findAll())

        return root
    }



    override fun onResponse(call: Call<List<StravaModel>>,
                            response: Response<List<StravaModel>>
    ) {
        serviceAvailableMessage(activity!!)
        info("Retrofit JSON = $response.raw()")
        app.stravaStore.members = response.body() as ArrayList<StravaModel>
        //updateUI()
        hideLoader(loader)
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
        showLoader(loader, "Downloading Donations List")
        var callGetAll = app.stravaService.getall()
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


