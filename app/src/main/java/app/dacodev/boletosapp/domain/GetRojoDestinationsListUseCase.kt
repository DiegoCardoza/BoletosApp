package app.dacodev.boletosapp.domain

import app.dacodev.boletosapp.data.repository.TicketRepoImpl

class GetRojoDestinationsListUseCase(private val repo: TicketRepoImpl) {

    operator fun invoke(): ArrayList<String> = repo.getRojoDeLosAltosDestinationsList()
}