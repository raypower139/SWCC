package ie.swcc.main

import android.app.Application
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import ie.swcc.api.StravaService
import ie.swcc.models.StravaMemStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class SWCCApp : Application(),AnkoLogger {

    lateinit var stravaStore: StravaMemStore
    lateinit var stravaService: StravaService


    // [START declare_auth]
    lateinit var auth: FirebaseAuth
    lateinit var database: DatabaseReference
    // [END declare_auth]


    override fun onCreate() {
        super.onCreate()
        stravaStore = StravaMemStore()
        info("SWCC App started")
        stravaService = StravaService.create()
        info("Strava Service Created")
    }
}