package ie.swcc.main

import android.app.Application
import android.net.Uri
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.StorageReference
import ie.swcc.api.StravaService
import ie.swcc.api.StravaWrapper
import ie.swcc.models.strava.StravaMemStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info


class SWCCApp : Application(),AnkoLogger {

    lateinit var stravaStore: StravaMemStore
    lateinit var stravaService: StravaService
    lateinit var googleSignInClient: GoogleSignInClient
    lateinit var userImage: Uri
    lateinit var image: Uri
    var groupId = ""
    var groupName = ""
    var segmentId = ""
    var segmentName = ""
    var access_token = "1711739604d9b867080fb12f7262ccc3ee115f8b"


    // [START declare_auth]
    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference
    lateinit var storage: StorageReference
    // [END declare_auth]


    override fun onCreate() {
        super.onCreate()
        stravaStore = StravaMemStore()
        info("SWCC App started")
        stravaService = StravaService.create()
        info("Strava Service Created")
    }
}