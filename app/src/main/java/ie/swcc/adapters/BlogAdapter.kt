package ie.swcc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.swcc.R
import ie.swcc.models.blog.BlogModel
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import kotlinx.android.synthetic.main.card_blog.view.*


interface BlogListener {
    fun onBlogPostClick(donation: BlogModel)
}

class BlogAdapter constructor(var blogPosts: ArrayList<BlogModel>,
                              private val listener: BlogListener, blogAll: Boolean)
    : RecyclerView.Adapter<BlogAdapter.MainHolder>() {

    val blogAll = blogAll

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent?.context).inflate(
                R.layout.card_blog,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val blog = blogPosts[holder.adapterPosition]
        holder.bind(blog,listener,blogAll)
    }

    override fun getItemCount(): Int = blogPosts.size

    fun removeAt(position: Int) {
        blogPosts.removeAt(position)
        notifyItemRemoved(position)
    }

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(blog: BlogModel, listener: BlogListener, blogAll: Boolean) {
            itemView.tag = blog
            itemView.title.text = blog.title
            //itemView.post.text = donation.posttype.toString()
            //itemView.imageIcon.setImageResource(R.drawable.logo)
            if(!blogAll)
            itemView.setOnClickListener { listener.onBlogPostClick(blog) }

            if(!blog.profilepic.isEmpty()) {
                Picasso.get().load(blog.profilepic.toUri())
                    //.resize(180, 180)
                    .transform(CropCircleTransformation())
                    .into(itemView.imageIcon)
            }
            else
                itemView.imageIcon.setImageResource(R.mipmap.ic_launcher_homer_round)
        }


        }
    }
