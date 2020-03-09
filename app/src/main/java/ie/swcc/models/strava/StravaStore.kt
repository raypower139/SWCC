package ie.swcc.models.strava;

import ie.swcc.models.strava.StravaModel

interface StravaStore {
    fun findAll() : List<StravaModel?>
    //fun findById(id: Long) : DonationModel?
    //fun create(donation: DonationModel)
    //fun update(donation: DonationModel)
    //fun delete(donation: DonationModel)
}