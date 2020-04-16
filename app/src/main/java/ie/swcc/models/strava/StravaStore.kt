package ie.swcc.models.strava;

import ie.swcc.api.StravaWrapper

import ie.swcc.models.strava.mySegmentEfforts.MyEffortsModel

interface StravaStore {
    fun findAll() : List<StravaModel?>
    fun findAllStats() : List<StravaStatsModel?>
    fun findAllSegmentEfforts() : List<Entry?>
    fun findAllMyEfforts() : List<MyEffortsModel?>
}