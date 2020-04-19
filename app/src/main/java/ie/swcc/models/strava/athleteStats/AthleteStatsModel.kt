package ie.swcc.models.strava.athleteStats


import com.google.gson.annotations.SerializedName

data class AthleteStatsModel(
    @SerializedName("recent_run_totals")
    val recentRunTotals: String,
    @SerializedName("all_run_totals")
    val allRunTotals: String,
    @SerializedName("recent_swim_totals")
    val recentSwimTotals: String,
    @SerializedName("biggest_ride_distance")
    val biggestRideDistance: Double,
    @SerializedName("ytd_swim_totals")
    val ytdSwimTotals: String,
    @SerializedName("all_swim_totals")
    val allSwimTotals: String,
    @SerializedName("recent_ride_totals")
    val recentRideTotals: RecentRideTotals,
    @SerializedName("biggest_climb_elevation_gain")
    val biggestClimbElevationGain: Double,
    @SerializedName("ytd_ride_totals")
    val ytdRideTotals: String,
    @SerializedName("all_ride_totals")
    val allRideTotals: String,
    @SerializedName("ytd_run_totals")
    val ytdRunTotals: String
)