package ie.swcc.models.strava.mySegmentEfforts


import com.google.gson.annotations.SerializedName

data class Activity(
    val id: Long,
    @SerializedName("resource_state")
    val resourceState: Int
)