package ie.swcc.fragments.blog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import ie.swcc.adapters.BlogListener

import ie.swcc.R
import ie.swcc.adapters.BlogAdapter
import ie.swcc.models.blog.BlogModel
import ie.swcc.utils.*
import kotlinx.android.synthetic.main.fragment_blogreport.view.*
import kotlinx.android.synthetic.main.fragment_blogreport.view.recyclerView
import org.jetbrains.anko.info

class ReportAllFragment : ReportFragment(),
    BlogListener {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_blogreport, container, false)
        activity?.title = getString(R.string.menu_bloglist_all)

        root.recyclerView.setLayoutManager(LinearLayoutManager(activity))
        setSwipeRefresh()

        return root
    }
    companion object {
        @JvmStatic
        fun newInstance() =
            ReportAllFragment().apply {
                arguments = Bundle().apply { }
            }
    }

    override fun setSwipeRefresh() {
        root.swiperefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                root.swiperefresh.isRefreshing = true
                getAllUsersPosts()
            }
        })
    }




    override fun onResume() {
        super.onResume()
        getAllUsersPosts()
    }

    fun getAllUsersPosts() {
        loader = createLoader(activity!!)
        showLoader(loader, "Downloading Posts from Firebase")
        val donationsList = ArrayList<BlogModel>()
        app.database.child("posts")
            .addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    info("Firebase Post error : ${error.message}")
                }

                override fun onDataChange(snapshot: DataSnapshot) {
                    hideLoader(loader)
                    val children = snapshot.children
                    children.forEach {
                        val donation = it.
                            getValue<BlogModel>(
                                BlogModel::class.java)

                        donationsList.add(donation!!)
                        root.recyclerView.adapter =
                            BlogAdapter(donationsList, this@ReportAllFragment, true)
                        root.recyclerView.adapter?.notifyDataSetChanged()
                        checkSwipeRefresh()

                        app.database.child("posts")
                            .removeEventListener(this)
                    }
                }
            })
    }
}
