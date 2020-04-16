package ie.swcc.models.strava.mySegmentEfforts


import com.google.gson.annotations.SerializedName

data class Segment(
    val id: Int,
    @SerializedName("resource_state")
    val resourceState: Int,
    val name: String,
    @SerializedName("activity_type")
    val activityType: String,
    val distance: Double,
    @SerializedName("average_grade")
    val averageGrade: Double,
    @SerializedName("maximum_grade")
    val maximumGrade: Double,
    @SerializedName("elevation_high")
    val elevationHigh: Double,
    @SerializedName("elevation_low")
    val elevationLow: Double,
    @SerializedName("start_latlng")
    val startLatlng: List<Double>,
    @SerializedName("end_latlng")
    val endLatlng: List<Double>,
    @SerializedName("start_latitude")
    val startLatitude: Double,
    @SerializedName("start_longitude")
    val startLongitude: Double,
    @SerializedName("end_latitude")
    val endLatitude: Double,
    @SerializedName("end_longitude")
    val endLongitude: Double,
    @SerializedName("climb_category")
    val climbCategory: Int,
    val city: Any,
    val state: String,
    val country: String,
    val `private`: Boolean,
    val hazardous: Boolean,
    val starred: Boolean
)