package ie.swcc.models.strava


import com.google.gson.annotations.SerializedName

data class Entry(
    @SerializedName("athlete_name")
    val athleteName: String,
    @SerializedName("elapsed_time")
    val elapsedTime: Int,
    @SerializedName("moving_time")
    val movingTime: Int,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("start_date_local")
    val startDateLocal: String,
    val rank: Int
)

