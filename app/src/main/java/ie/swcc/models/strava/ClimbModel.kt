package ie.swcc.models.strava

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
data class ClimbModel(
    var uid: String? = "",
    var MahonFalls: Boolean,
    var SeskinHill: Boolean,
    var MtLeinster: Boolean,
    var LastUpdated: String = ""
    ) : Parcelable
{


    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "MahonFalls" to MahonFalls,
            "SeskinHill" to SeskinHill,
            "MtLeinster" to MtLeinster,
            "LastUpdated" to LastUpdated
        )
    }
}


