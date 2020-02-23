package ie.swcc.main

import android.app.Application
import ie.swcc.api.StravaService
import ie.swcc.models.StravaMemStore
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info

class SWCCApp : Application(),AnkoLogger {

    lateinit var stravaStore: StravaMemStore
    lateinit var stravaService: StravaService

    override fun onCreate() {
        super.onCreate()
        stravaStore = StravaMemStore()
        info("SWCC App started")
        stravaService = StravaService.create()
        info("Strava Service Created")
    }
}