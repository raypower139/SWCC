package ie.swcc.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.swcc.R
import ie.swcc.api.StatsWrapper
import ie.swcc.api.StravaWrapper
import ie.swcc.fragments.strava.StravaStats
import ie.swcc.models.strava.StravaModel
import ie.swcc.models.strava.athleteStats.AthleteStatsModel
import ie.swcc.models.strava.athleteStats.Totals
import ie.swcc.models.strava.athleteStats.YtdRideTotals
import kotlinx.android.synthetic.main.card_strava_member.view.*
import kotlinx.android.synthetic.main.card_strava_stats.view.*

class StravaStatsAdapter constructor(private var stats: List<Totals>)
    : RecyclerView.Adapter<StravaStatsAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        return MainHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.card_strava_stats,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val stats = stats[holder.adapterPosition]
        holder.bind(stats)
    }

    override fun getItemCount(): Int = stats.size

    class MainHolder constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(stats: Totals) {
            itemView.TotalKMS.text = stats.count.toString()


        }
    }
}