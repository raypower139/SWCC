package ie.swcc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.swcc.R
import ie.swcc.models.strava.StravaStatsModel
import ie.swcc.models.strava.mySegmentEfforts.MyEffortsModel
import kotlinx.android.synthetic.main.card_strava_activities.view.*
import kotlinx.android.synthetic.main.card_strava_my_efforts.view.*
import java.text.DecimalFormat

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
        val activity = activities[holder.adapterPosition]
        holder.bind(activity)
    }

    override fun getItemCount(): Int = activities.count()

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {


        fun bind(activity: MyEffortsModel) {

            itemView.strava_my_efforts_firstname.text = activity.movingTime.toString()
            itemView.strava_my_efforts_lastname.text = activity.startDate.toString()


        }
    }
}