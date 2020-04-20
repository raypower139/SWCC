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
            itemView.blogText.text = blog.body
            itemView.blogDate.text = blog.date

            if(!blogAll)
            itemView.setOnClickListener { listener.onBlogPostClick(blog) }

            if(!blog.image.isEmpty()) {
                Picasso.get().load(blog.image.toUri())
                    .resize(240, 180)
                    .into(itemView.imageIcon) }

            else
                itemView.imageIcon.setImageResource(R.mipmap.ic_launcher_homer_round)
        }
    }




}
