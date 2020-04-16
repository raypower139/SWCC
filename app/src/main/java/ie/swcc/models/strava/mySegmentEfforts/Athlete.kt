package ie.swcc.models.strava.mySegmentEfforts


import com.google.gson.annotations.SerializedName

data class Athlete(
    val id: Int,
    @SerializedName("resource_state")
    val resourceState: Int
)