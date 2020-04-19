package ie.swcc.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import ie.swcc.R
import ie.swcc.models.strava.StravaStatsModel
import ie.swcc.models.strava.mySegmentEfforts.MyEffortsModel
import kotlinx.android.synthetic.main.card_strava_activities.view.*
import kotlinx.android.synthetic.main.card_strava_my_efforts.view.*
import kotlinx.android.synthetic.main.fragment_strava_my_efforts.view.*
import java.text.DecimalFormat
import java.time.LocalDate
import java.util.concurrent.TimeUnit

class MyEffortsAdapter constructor(private var activities: List<MyEffortsModel>)
    : RecyclerView.Adapter<MyEffortsAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_strava_my_efforts,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val activity = activities.sortedByDescending {it.startDate}[holder.adapterPosition]
        holder.bind(activity)
    }

    override fun getItemCount(): Int = activities.count()

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(activity: MyEffortsModel) {
            val minutes = TimeUnit.SECONDS.toMinutes(activity.movingTime.toLong()).toString()
            val seconds = TimeUnit.SECONDS.toSeconds(activity.movingTime.toLong()%60).toString()
            val effortTime = minutes.plus("m ").plus(seconds).plus("s")

            itemView.strava_my_efforts_movingTime.text = effortTime
            itemView.strava_my_efforts_date.text = activity.startDate
            itemView.strava_my_efforts_averageWatts.text = activity.averageWatts.toString()

    }
    }
}


