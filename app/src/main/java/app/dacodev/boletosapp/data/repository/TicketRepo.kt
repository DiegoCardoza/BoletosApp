package app.dacodev.boletosapp.data.repository

interface TicketRepo {

    fun getBusBrandsList(): ArrayList<String>

    fun getFronteraDestinationsList(): ArrayList<String>

    fun getRojoDeLosAltosDestinationsList(): ArrayList<String>

}