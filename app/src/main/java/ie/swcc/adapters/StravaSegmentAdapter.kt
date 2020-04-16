package ie.swcc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.persistableBundleOf
import androidx.recyclerview.widget.RecyclerView
import ie.swcc.R
import ie.swcc.api.StravaWrapper
import ie.swcc.models.strava.Entry
import ie.swcc.models.strava.StravaSegmentModel
import kotlinx.android.synthetic.main.card_strava_segment.view.*

class StravaSegmentAdapter constructor(private var segmentEfforts: List<Entry>)
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
    override fun getItemCount(): Int = segmentEfforts.size
    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val effort = segmentEfforts[holder.adapterPosition]
        holder.bind(effort)
    }

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(efforts: Entry) {


                itemView.strava_segment_athlete_name.text = efforts.athleteName
                itemView.strava_segment_effort_time.text = efforts.elapsedTime.toString()


        }


    }
}