package ie.swcc.models.strava.athleteStats


import com.google.gson.annotations.SerializedName

data class Totals(
    val count: Int,
    val distance: Double,
    @SerializedName("moving_time")
    val movingTime: Long,
    @SerializedName("elapsed_time")
    val elapsedTime: Long,
    @SerializedName("elevation_gain")
    val elevationGain: Double
)