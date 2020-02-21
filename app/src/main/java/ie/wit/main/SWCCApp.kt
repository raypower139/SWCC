package ie.wit.main

import android.app.Application
import ie.wit.api.StravaService
import ie.wit.models.StravaMemStore
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