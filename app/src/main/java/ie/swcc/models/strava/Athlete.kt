package ie.swcc.models.strava


import com.google.gson.annotations.SerializedName

data class Athlete(
    @SerializedName("resource_state")
    val resourceState: Int,
    val firstname: String,
    val lastname: String
)