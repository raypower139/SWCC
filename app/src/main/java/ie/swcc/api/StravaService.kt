package ie.swcc.api

import com.google.gson.GsonBuilder
import ie.swcc.models.strava.StravaModel
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface StravaService {
    @GET("clubs/498435/members?access_token=d39a37b2e32436ce46395755f36ed757f04057d9&per_page=200")
    fun getall(): Call<List<StravaModel>>

    @GET("/athletes/606634/stats")
    fun getall2(): Call<List<StatsModel>>

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