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
    var posttype: String = "N/A",
    var message: String = "a message",
    var upvotes: Int = 0,
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
            "posttype" to posttype,
            "message" to message,
            "upvotes" to upvotes,
            "profilepic" to profilepic,
            "email" to email
        )
    }
}


