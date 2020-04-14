package ie.swcc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.swcc.R
import ie.swcc.models.strava.StravaModel
import kotlinx.android.synthetic.main.card_strava_member.view.*

class StravaAdapter constructor(private var members: List<StravaModel>)
    : RecyclerView.Adapter<StravaAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_strava_member,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val donation = members.sortedBy {!it.admin}[holder.adapterPosition]
        holder.bind(donation)
    }

    override fun getItemCount(): Int = members.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(member: StravaModel) {
            itemView.strava_member_firstname.text = member.firstname.toString()
            itemView.strava_member_initial.text = member.lastname.toString()
            itemView.imageIcon.setImageResource(R.drawable.logo)
            var admin = member.admin.toString()
            if(admin == "true"){
                itemView.strava_member_membership.text = "Admin"
            }
        }
    }
}