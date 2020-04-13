package ie.swcc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.swcc.R
import ie.swcc.models.strava.StravaModel
import ie.swcc.models.strava.StravaStatsModel
import kotlinx.android.synthetic.main.card_strava_member.view.*
import kotlinx.android.synthetic.main.card_strava_member.view.imageIcon
import kotlinx.android.synthetic.main.card_strava_member2.view.*

class StravaAdapter2 constructor(private var activities: List<StravaStatsModel>)
    : RecyclerView.Adapter<StravaAdapter2.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_strava_member2,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val donation = activities[holder.adapterPosition]
        holder.bind(donation)
    }

    override fun getItemCount(): Int = activities.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(member: StravaStatsModel) {
            itemView.biggestRide.text = member.distance.toString()
            //itemView.paymentmethod.text = member.lastname.toString()
            itemView.imageIcon.setImageResource(R.drawable.logo)
        }
    }
}