package ie.swcc.models.blog

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
data class BlogModel(
    var uid: String? = "",
    var image: String = "",
    var title: String = "",
    var body: String = "",
    var date: String = "",
    var posttype: String = "N/A",
    var profilepic: String = "",
    var email: String? = "joe@bloggs.com")
                        : Parcelable
{


    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "image" to image,
            "title" to title,
            "body" to body,
            "date" to date,
            "posttype" to posttype,
            "profilepic" to profilepic,
            "email" to email
        )
    }
}


