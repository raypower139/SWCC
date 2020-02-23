package ie.swcc.models

import android.util.Log

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class StravaMemStore : StravaStore {

        var members = ArrayList<StravaModel>()

        override fun findAll(): List<StravaModel> {
            return members
        }



        fun logAll() {
            Log.v("Donate","** Donations List **")
            members.forEach { Log.v("Donate","${it}") }
        }
    }
