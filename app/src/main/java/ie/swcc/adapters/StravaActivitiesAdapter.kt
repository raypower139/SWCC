package ie.swcc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.swcc.R
import ie.swcc.models.strava.StravaStatsModel
import kotlinx.android.synthetic.main.card_strava_activities.view.*
import java.text.DecimalFormat

class StravaActivitiesAdapter constructor(private var activities: List<StravaStatsModel>)
    : RecyclerView.Adapter<StravaActivitiesAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_strava_activities,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val activity = activities[holder.adapterPosition]
        holder.bind(activity)
    }

    override fun getItemCount(): Int = activities.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(activity: StravaStatsModel) {
            val dec = DecimalFormat("###.0")
            val distance = dec.format(activity.distance/1000)

            var elevation = activity.totalElevationGain
            var time = activity.movingTime/60

            itemView.strava_activity_distance.text = distance.toString()
            itemView.strava_activity_firstname.text = activity.athlete.firstname.toString()
            itemView.strava_activity_lastname.text = activity.athlete.lastname.toString()
            itemView.strava_activity_time.text = time.toString()
            itemView.strava_activity_elevation.text = elevation.toString()
            itemView.strava_activity_name.text = activity.name.toString()

        }
    }
}