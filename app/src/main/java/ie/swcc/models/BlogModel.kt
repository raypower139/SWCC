package ie.swcc.models

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
data class BlogModel(
    var uid: String? = "",
    var title: String = "",
    var body: String = "",
    var posttype: String = "N/A",
    var message: String = "a message",
    var upvotes: Int = 0,
    var email: String? = "joe@bloggs.com")
                        : Parcelable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "uid" to uid,
            "title" to title,
            "body" to body,
            "posttype" to posttype,
            "message" to message,
            "upvotes" to upvotes,
            "email" to email
        )
    }
}


