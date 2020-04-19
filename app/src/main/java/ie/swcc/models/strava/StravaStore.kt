package ie.swcc.models.strava;

import ie.swcc.api.StatsWrapper
import ie.swcc.models.strava.athleteStats.AthleteStatsModel
import ie.swcc.models.strava.athleteStats.Totals
import ie.swcc.models.strava.athleteStats.YtdRideTotals

import ie.swcc.models.strava.mySegmentEfforts.MyEffortsModel

interface StravaStore {
    fun findAll() : List<StravaModel?>
    fun findAllStats() : List<StravaStatsModel?>
    fun findAllSegmentEfforts() : List<Entry?>
    fun findAllMyEfforts() : List<MyEffortsModel?>
    fun findMyStats() : List<Totals?>

}