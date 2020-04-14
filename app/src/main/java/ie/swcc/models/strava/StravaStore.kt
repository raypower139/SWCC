package ie.swcc.models.strava;

interface StravaStore {
    fun findAll() : List<StravaModel?>
    fun findAllStats() : List<StravaStatsModel?>
    fun findAllSegmentEfforts() : List<StravaSegmentModel?>
    //fun findById(id: Long) : DonationModel?
    //fun create(donation: DonationModel)
    //fun update(donation: DonationModel)
    //fun delete(donation: DonationModel)
}