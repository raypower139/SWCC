package ie.swcc.models.strava.mySegmentEfforts


import com.google.gson.annotations.SerializedName

data class MyEffortsModel(
    val id: Long,
    @SerializedName("resource_state")
    val resourceState: Int,
    val name: String,
    val activity: Activity,
    val athlete: Athlete,
    @SerializedName("elapsed_time")
    val elapsedTime: Int,
    @SerializedName("moving_time")
    val movingTime: Int,
    @SerializedName("start_date")
    val startDate: String,
    @SerializedName("start_date_local")
    val startDateLocal: String,
    val distance: Double,
    @SerializedName("start_index")
    val startIndex: Int,
    @SerializedName("end_index")
    val endIndex: Int,
    @SerializedName("device_watts")
    val deviceWatts: Boolean,
    @SerializedName("average_watts")
    val averageWatts: Double,
    val segment: Segment,
    @SerializedName("pr_rank")
    val prRank: Int?,
    val achievements: List<Achievement>,
    @SerializedName("kom_rank")
    val komRank: Any?
)