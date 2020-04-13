package ie.swcc.models.strava


import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class StravaStatsModel(
    @SerializedName("resource_state")
    val resourceState: Int,
    val athlete: Athlete,
    val name: String,
    val distance: Double,
    @SerializedName("moving_time")
    val movingTime: Int,
    @SerializedName("elapsed_time")
    val elapsedTime: Int,
    @SerializedName("total_elevation_gain")
    val totalElevationGain: Double,
    val type: String,
    @SerializedName("workout_type")
    val workoutType: Any
)