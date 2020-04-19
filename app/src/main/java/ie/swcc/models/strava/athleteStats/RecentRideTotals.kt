package ie.swcc.models.strava.athleteStats


import com.google.gson.annotations.SerializedName

data class RecentRideTotals(
    val distance: Double,
    @SerializedName("achievement_count")
    val achievementCount: Int,
    val count: Int,
    @SerializedName("elapsed_time")
    val elapsedTime: Int,
    @SerializedName("elevation_gain")
    val elevationGain: Double,
    @SerializedName("moving_time")
    val movingTime: Int
)