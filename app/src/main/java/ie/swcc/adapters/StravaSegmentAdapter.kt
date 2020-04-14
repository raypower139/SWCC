package ie.swcc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.swcc.R
import ie.swcc.models.strava.Entry
import ie.swcc.models.strava.StravaSegmentModel
import kotlinx.android.synthetic.main.card_strava_segment.view.*

class StravaSegmentAdapter constructor(private var segmentEfforts: List<StravaSegmentModel>)
    : RecyclerView.Adapter<StravaSegmentAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_strava_segment,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val activity = segmentEfforts[holder.adapterPosition]
        holder.bind(activity)
    }

    override fun getItemCount(): Int = segmentEfforts.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(segmentEffort: StravaSegmentModel) {

             var effort = segmentEffort.entries

                itemView.strava_segment_athlete_name.text = effort[1].athleteName
                itemView.strava_segment_effort_time.text = effort[1].elapsedTime.toString()



           // itemView.strava_activity_firstname.text = activity.athlete.firstname.toString()
            //itemView.strava_activity_lastname.text = activity.athlete.lastname.toString()
            //itemView.strava_activity_time.text = time.toString()
           // itemView.strava_activity_elevation.text = elevation.toString()
            //itemView.strava_activity_name.text = activity.name.toString()
        }
    }
}