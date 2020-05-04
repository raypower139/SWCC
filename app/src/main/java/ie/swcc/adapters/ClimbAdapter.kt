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

    fun removeAt(position: Int) {
        blogPosts.removeAt(position)
        notifyItemRemoved(position)
    }

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(blog: ClimbModel, listener: ClimbListener, blogAll: Boolean) {
            itemView.tag = blog
            itemView.title.text = blog.LastUpdated
            itemView.blogText.text = blog.LastUpdated
            itemView.blogDate.text = blog.MtLeinster.toString()

            if (!blogAll)
                itemView.setOnClickListener { listener.onClimbClick(blog) }

//            if(!blog.image.isEmpty()) {
//                Picasso.get().load(blog.image.toUri())
//                    .resize(240, 180)
//                    .into(itemView.imageIcon) }
//
//            else
            itemView.imageIcon.setImageResource(R.mipmap.ic_launcher_homer_round)
//        }
        }


    }
}
