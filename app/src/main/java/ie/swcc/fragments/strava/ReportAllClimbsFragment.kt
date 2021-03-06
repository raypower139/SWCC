package ie.swcc.fragments.strava

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import ie.swcc.R
import ie.swcc.adapters.ClimbAdapter
import ie.swcc.adapters.ClimbListener
import ie.swcc.main.SWCCApp
import ie.swcc.models.strava.ClimbModel
import ie.swcc.utils.SwipeToEditCallback
import ie.swcc.utils.createLoader
import ie.swcc.utils.hideLoader
import ie.swcc.utils.showLoader
import kotlinx.android.synthetic.main.fragment_blogreport.view.swiperefresh
import kotlinx.android.synthetic.main.fragment_climbreport.view.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


open class ReportAllClimbsFragment : Fragment(), AnkoLogger,
    ClimbListener {

    lateinit var app: SWCCApp
    lateinit var loader : AlertDialog
    lateinit var root: View
    var yes = 0
    var no = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = activity?.application as SWCCApp


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_climbreport, container, false)
        activity?.title = "MRA Challenge Results"


        root.recyclerViewClimbs.setLayoutManager(LinearLayoutManager(activity))
        setSwipeRefresh()

        val swipeEditHandler = object : SwipeToEditCallback(requireActivity()) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                onClimbClick(viewHolder.itemView.tag as ClimbModel)
            }
        }
        val itemTouchEditHelper = ItemTouchHelper(swipeEditHandler)
        itemTouchEditHelper.attachToRecyclerView(root.recyclerViewClimbs)

        return root
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            ReportAllClimbsFragment().apply {
                arguments = Bundle().apply { }
            }
    }

    open fun setSwipeRefresh() {
        root.swiperefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                root.swiperefresh.isRefreshing = true
                getAllUsersPosts()
            }
        })
    }

    fun checkSwipeRefresh() {
        if (root.swiperefresh.isRefreshing) root.swiperefresh.isRefreshing = false
    }

    override fun onClimbClick(climb: ClimbModel) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.homeFrame, EditClimb.newInstance(climb))
            .addToBackStack(null)
            .commit()
    }

    override fun onResume() {
        super.onResume()
        getAllUsersPosts()
    }

    fun getAllUsersPosts() {
        loader = createLoader(activity!!)
        showLoader(loader, "Downloading Posts from Firebase")
        val donationsList = ArrayList<ClimbModel>()
        app.database.child("climbs")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    info("Firebase Post error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    hideLoader(loader)
                    val children = snapshot.children
                    children.forEach {
                        val donation = it.
                            getValue<ClimbModel>(
                                ClimbModel::class.java)

                        donationsList.add(donation!!)
                        root.recyclerViewClimbs.adapter =
                            ClimbAdapter(donationsList, this@ReportAllClimbsFragment, true)
                        root.recyclerViewClimbs.adapter?.notifyDataSetChanged()
                        checkSwipeRefresh()

                        app.database.child("climbs")
                            .removeEventListener(this)

                    }
                    }
                    })

                }
}

