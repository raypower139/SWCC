package ie.swcc.api

import com.google.gson.GsonBuilder
import ie.swcc.models.strava.Entry
import ie.swcc.models.strava.StravaModel
import ie.swcc.models.strava.StravaSegmentModel
import ie.swcc.models.strava.StravaStatsModel
import ie.swcc.models.strava.mySegmentEfforts.MyEffortsModel
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface StravaService {
    @GET("clubs/{groupId}/members?access_token=a9b21ac4178c456369d387e68ede3a4ea9c80192&per_page=30")
    fun getall(@Path("groupId") groupId: String): Call<List<StravaModel>>

    @GET("clubs/498435/activities?access_token=a9b21ac4178c456369d387e68ede3a4ea9c80192&per_page=50")
    fun getall2(): Call<List<StravaStatsModel>>

    @GET("segments/623750/all_efforts?access_token=a9b21ac4178c456369d387e68ede3a4ea9c80192")
    fun getAllMyEfforts(): Call<List<MyEffortsModel>>

    @GET("segments/623750/leaderboard?access_token=a9b21ac4178c456369d387e68ede3a4ea9c80192&club_id=498435&per_page=50")
    fun getallSegmentEfforts(): Call<StravaWrapper>

    companion object {

        val serviceURL = "https://www.strava.com/api/v3/"

        fun create() : StravaService {


            val gson = GsonBuilder().setLenient().create()



            val okHttpClient = OkHttpClient.Builder()
                .build()

            val retrofit = Retrofit.Builder()
                .baseUrl(serviceURL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHttpClient)
                .build()
            return retrofit.create(StravaService::class.java)
        }
    }
}