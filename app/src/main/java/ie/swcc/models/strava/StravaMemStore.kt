package ie.swcc.models.strava

import android.util.Log

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class StravaMemStore : StravaStore {

        var members = ArrayList<StravaModel>()
        var activities = ArrayList<StravaStatsModel>()
        var segmentEfforts = ArrayList<StravaSegmentModel>()

        override fun findAll(): List<StravaModel> {
            return members
        }
        override fun findAllStats(): List<StravaStatsModel> {
            return activities
        }
        override fun findAllSegmentEfforts(): List<StravaSegmentModel> {
             return segmentEfforts
    }

        fun logAll() {
            Log.v("Donate","** Donations List **")
            members.forEach { Log.v("Donate","${it}") }
        }
    }

