package ie.swcc.models.strava.mySegmentEfforts


import com.google.gson.annotations.SerializedName

data class Achievement(
    @SerializedName("type_id")
    val typeId: Int,
    val type: String,
    val rank: Int
)