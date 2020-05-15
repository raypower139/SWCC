package ie.swcc.models.strava

import ie.swcc.models.strava.athleteStats.Totals
import ie.swcc.models.strava.mySegmentEfforts.MyEffortsModel
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

