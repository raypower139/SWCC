package ie.swcc.models.strava

import android.util.Log
import ie.swcc.R
import ie.swcc.api.StatsWrapper
import ie.swcc.api.StravaWrapper
import ie.swcc.fragments.strava.StravaStats
import ie.swcc.models.strava.athleteStats.AthleteStatsModel
import ie.swcc.models.strava.athleteStats.Totals
import ie.swcc.models.strava.athleteStats.YtdRideTotals
import ie.swcc.models.strava.athleteStats.YtdRunTotals
import ie.swcc.models.strava.mySegmentEfforts.MyEffortsModel
import kotlinx.android.synthetic.main.card_strava_my_efforts.*
import kotlin.collections.ArrayList

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class StravaMemStore : StravaStore {

        var members = ArrayList<StravaModel>()
        var activities = ArrayList<StravaStatsModel>()
        var segmentEfforts = ArrayList<Entry>()
        var myEfforts = ArrayList<MyEffortsModel>()
        var myStats = ArrayList<Totals>()




    override fun findAll(): List<StravaModel> {
            return members
        }
    override fun findAllStats(): List<StravaStatsModel> {
            return activities
        }
    override fun findAllSegmentEfforts(): List<Entry> {
            return segmentEfforts
        }
    override fun findAllMyEfforts(): List<MyEffortsModel> {
        return myEfforts
    }
    override fun findMyStats(): List<Totals> {
        return myStats
    }

    }

