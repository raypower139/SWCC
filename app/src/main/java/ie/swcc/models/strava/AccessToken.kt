package ie.swcc.models.strava

data class AccessToken(
    val access_token: String,
    val athlete: AthleteXX,
    val expires_at: Int,
    val expires_in: Int,
    val refresh_token: String,
    val token_type: String


)

