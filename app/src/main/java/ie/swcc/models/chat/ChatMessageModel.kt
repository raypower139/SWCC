package ie.swcc.models.chat

import android.os.Parcelable
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import kotlinx.android.parcel.Parcelize

@IgnoreExtraProperties
@Parcelize
data class ChatMessageModel(
    var id: String?,
    var text: String,
    var fromId: String?,
    var toId: String,
    var timestamp: Long)
    : Parcelable
{
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "text" to text,
            "fromId" to fromId,
            "toId" to toId,
            "timestamp" to timestamp
        )
    }
}
