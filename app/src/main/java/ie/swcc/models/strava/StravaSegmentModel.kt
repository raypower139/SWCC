package ie.swcc.models.strava


import com.google.gson.annotations.SerializedName

data class StravaSegmentModel(
    @SerializedName("effort_count")
    val effortCount: Int,
    @SerializedName("entry_count")
    val entryCount: Int,
    @SerializedName("kom_type")
    val komType: String,
    val entries: List<Entry>
)