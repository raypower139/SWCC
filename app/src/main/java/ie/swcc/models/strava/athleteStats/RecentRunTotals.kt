package ie.swcc.models.strava.athleteStats


import com.google.gson.annotations.SerializedName

data class RecentRunTotals(
    val count: Int,
    val distance: Int,
    @SerializedName("moving_time")
    val movingTime: Int,
    @SerializedName("elapsed_time")
    val elapsedTime: Int,
    @SerializedName("elevation_gain")
    val elevationGain: Int,
    @SerializedName("achievement_count")
    val achievementCount: Int
)