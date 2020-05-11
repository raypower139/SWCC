package ie.swcc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.core.Context
import com.squareup.picasso.Picasso
import ie.swcc.R
import ie.swcc.fragments.MenuFragment
import ie.swcc.models.blog.BlogModel
import ie.swcc.models.strava.ClimbModel
import kotlinx.android.synthetic.main.card_blog.view.*
import kotlinx.android.synthetic.main.card_blog.view.blogDate
import kotlinx.android.synthetic.main.card_blog.view.blogText
import kotlinx.android.synthetic.main.card_blog.view.imageIcon
import kotlinx.android.synthetic.main.card_blog.view.title
import kotlinx.android.synthetic.main.card_climbs.view.*


interface ClimbListener {
    fun onClimbClick(donation: ClimbModel)
}

class ClimbAdapter constructor(var blogPosts: ArrayList<ClimbModel>,
                              private val listener: ClimbListener, blogAll: Boolean)
    : RecyclerView.Adapter<ClimbAdapter.MainHolder>() {

    val blogAll = blogAll

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.card_climbs,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val blog = blogPosts[holder.adapterPosition]
        holder.bind(blog, listener, blogAll)
    }

    override fun getItemCount(): Int = blogPosts.size

    fun Count(): Int = blogPosts.count {it.SeskinHill == true}

    fun removeAt(position: Int) {
        blogPosts.removeAt(position)
        notifyItemRemoved(position)
    }

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(climb: ClimbModel, listener: ClimbListener, blogAll: Boolean) {
            itemView.tag = climb
            itemView.name.text = climb.Name
            itemView.lastUpdated.text = climb.LastUpdated

            val booleanFields = ArrayList<Boolean>()
            booleanFields.add(climb.MahonFalls)
            booleanFields.add(climb.SeskinHill)
            booleanFields.add(climb.MtLeinster)

            val count = booleanFields.count { it }

            itemView.countClimbs.text = count.toString()



            if (!blogAll)
                itemView.setOnClickListener { listener.onClimbClick(climb) }
        }
    }
}
