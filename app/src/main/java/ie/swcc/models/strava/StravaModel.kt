package ie.swcc.models.strava

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class StravaModel(

    @SerializedName("resource_state")
val resource_state: String,
                         @SerializedName("firstname")
                         val firstname: String,
                         @SerializedName("lastname")
                         val lastname: String,
                         @SerializedName("membership")
                         val membership: String,
                         @SerializedName("admin")
                         val admin: Boolean,
                         @SerializedName("owner")
                         val owner: Boolean


) : Parcelable


