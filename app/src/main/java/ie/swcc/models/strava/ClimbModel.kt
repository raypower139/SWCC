package ie.swcc.models.strava

import android.net.Uri
import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
data class ClimbModel(
    var uid: String? = "",
    var Name: String? = "",
    var MahonFalls: Boolean= false,
    var SeskinHill: Boolean = false,
    var MtLeinster: Boolean = false,
    var SlieveCoillte: Boolean = false,
    var Vee: Boolean= false,
    var PowersEast: Boolean = false,
    var MountainRoad: Boolean = false,
    var SlieveNamBan: Boolean = false,
    var PowersWest: Boolean = false,
    var Tickincor: Boolean = false,
    var LastUpdated: String = ""

    ) : Parcelable
{


    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "Name" to Name,
            "MahonFalls" to MahonFalls,
            "SeskinHill" to SeskinHill,
            "MtLeinster" to MtLeinster,
            "SlieveCoillte" to SlieveCoillte,
            "Vee" to Vee,
            "PowersEast" to PowersEast,
            "MountainRoad" to MountainRoad,
            "SlieveNamBan" to SlieveNamBan,
            "PowersWest" to PowersWest,
            "Tickincor" to Tickincor,
            "LastUpdated" to LastUpdated

        )
    }
}


