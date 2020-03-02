package ie.swcc.utils

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import ie.swcc.R
import ie.swcc.main.SWCCApp
import ie.swcc.models.UserPhotoModel
import java.io.ByteArrayOutputStream
import java.io.IOException

fun createLoader(activity: FragmentActivity) : AlertDialog {
    val loaderBuilder = AlertDialog.Builder(activity)
        .setCancelable(true) // 'false' if you want user to wait
        .setView(R.layout.loading)
    var loader = loaderBuilder.create()
    loader.setTitle(R.string.app_name)
    loader.setIcon(R.drawable.logo)

    return loader
}

fun showLoader(loader: AlertDialog, message: String) {
    if (!loader.isShowing()) {
        if (message != null) loader.setTitle(message)
        loader.show()
    }
}

fun hideLoader(loader: AlertDialog) {
    if (loader.isShowing())
        loader.dismiss()
}

fun serviceUnavailableMessage(activity: FragmentActivity) {
    Toast.makeText(
        activity,
        "Strava Service Unavailable. Try again later",
        Toast.LENGTH_LONG
    ).show()
}

fun serviceAvailableMessage(activity: FragmentActivity) {
    Toast.makeText(
        activity,
        "Strava Contacted Successfully",
        Toast.LENGTH_LONG
    ).show()
}

fun uploadImageView(app: SWCCApp, imageView: ImageView) {
    // Get the data from an ImageView as bytes
    val uid = app.auth.currentUser!!.uid
    val imageRef = app.storage.child("photos").child("${uid}.jpg")
    val bitmap = (imageView.drawable as BitmapDrawable).bitmap
    val baos = ByteArrayOutputStream()

    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
    val data = baos.toByteArray()

    var uploadTask = imageRef.putBytes(data)
}

fun writeImageRef(app: SWCCApp, imageRef: String) {
    val userId = app.auth.currentUser!!.uid
    val values = UserPhotoModel(userId,imageRef).toMap()
    val childUpdates = HashMap<String, Any>()

    childUpdates["/user-photos/$userId"] = values
    app.database.updateChildren(childUpdates)
}

fun readImageUri(resultCode: Int, data: Intent?): Uri? {
    var uri: Uri? = null
    if (resultCode == Activity.RESULT_OK && data != null && data.data != null) {
        try { uri = data.data }
        catch (e: IOException) {
            e.printStackTrace()
        }
    }
    return uri
}

