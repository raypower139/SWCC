package ie.swcc.models.strava

import android.util.Log
import ie.swcc.api.StravaWrapper
import ie.swcc.fragments.strava.MyEfforts
import ie.swcc.models.strava.mySegmentEfforts.MyEffortsModel

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class StravaMemStore : StravaStore {

        var members = ArrayList<StravaModel>()
        var activities = ArrayList<StravaStatsModel>()
        var segmentEfforts = ArrayList<Entry>()
        var myEfforts = ArrayList<MyEffortsModel>()

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



        fun logAll() {
            Log.v("Donate","** Donations List **")
            members.forEach { Log.v("Donate","${it}") }
        }
    }

