package ie.swcc.api

import com.google.gson.GsonBuilder
import ie.swcc.models.strava.Entry
import ie.swcc.models.strava.StravaModel
import ie.swcc.models.strava.StravaSegmentModel
import ie.swcc.models.strava.StravaStatsModel
import ie.swcc.models.strava.athleteStats.AthleteStatsModel
import ie.swcc.models.strava.mySegmentEfforts.MyEffortsModel
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface StravaService {
    @GET("clubs/{groupId}/members")
    fun getall(@Path("groupId") groupId: String,@Query("access_token") access_token: String, @Query("per_page") int: Int ): Call<List<StravaModel>>

    @GET("clubs/{groupId}/activities")
    fun getall2(@Path("groupId") groupId: String,@Query("access_token") access_token: String, @Query("per_page") int: Int) : Call<List<StravaStatsModel>>

    @GET("segments/{segmentId}/all_efforts")
    fun getAllMyEfforts(@Path("segmentId") segmentId: String,@Query("access_token") access_token: String): Call<List<MyEffortsModel>>

    @GET("segments/{segmentId}/leaderboard")
    fun getallSegmentEfforts(@Path("segmentId") segmentId: String,@Query("access_token") access_token: String, @Query("club_id") club_id: String,@Query("per_page") int: Int): Call<StravaWrapper>

    @GET("athletes/606634/stats?access_token=916daa0d6e45f84854c299d10103290aba401411")
    fun getMyStats(): Call<StatsWrapper>




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