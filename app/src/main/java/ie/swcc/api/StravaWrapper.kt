package ie.swcc.api

import ie.swcc.models.strava.Entry
import ie.swcc.models.strava.StravaModel
import ie.swcc.models.strava.StravaSegmentModel
import ie.swcc.models.strava.StravaStatsModel


class StravaWrapper {


    var effort_count: Int? = null
    var entry_count: Int? = null
    var kom_type: String? = null
    lateinit var entries: ArrayList<Entry>
}