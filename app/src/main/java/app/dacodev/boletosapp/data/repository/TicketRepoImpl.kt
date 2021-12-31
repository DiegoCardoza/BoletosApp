package app.dacodev.boletosapp.data.repository

import app.dacodev.boletosapp.data.provider.TicketProvider

class TicketRepoImpl(
    private val provider: TicketProvider
) : TicketRepo {
    override fun getBusBrandsList(): ArrayList<String> {
        return provider.getBusBrandsList()
    }

    override fun getFronteraDestinationsList(): ArrayList<String> {
        return provider.getFronteraDestinationList()
    }

    override fun getRojoDeLosAltosDestinationsList(): ArrayList<String> {
        return provider.getRojoDeLosAltosDestinationList()
    }
}