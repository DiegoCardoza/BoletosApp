package app.dacodev.boletosapp.data.model

data class TicketModel(
    var name: String? = null,
    var seat: Int = 0,
    var ticketType: TicketType = TicketType.ADULT,
    var busNumber: Int = 0,
    var destiny: String? = null,
    var totalAmount: String? = null,
    var date: String? = null
)