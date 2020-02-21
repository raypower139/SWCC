package ie.wit.models;

interface StravaStore {
    fun findAll() : List<StravaModel?>
    //fun findById(id: Long) : DonationModel?
    //fun create(donation: DonationModel)
    //fun update(donation: DonationModel)
    //fun delete(donation: DonationModel)
}