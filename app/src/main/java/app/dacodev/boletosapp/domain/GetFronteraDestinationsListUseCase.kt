package app.dacodev.boletosapp.domain

import app.dacodev.boletosapp.data.repository.TicketRepoImpl

class GetFronteraDestinationsListUseCase(private val repo: TicketRepoImpl) {

    operator fun invoke(): ArrayList<String> = repo.getFronteraDestinationsList()
}