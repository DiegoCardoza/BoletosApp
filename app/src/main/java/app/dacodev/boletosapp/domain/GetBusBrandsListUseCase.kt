package app.dacodev.boletosapp.domain

import app.dacodev.boletosapp.data.repository.TicketRepoImpl

class GetBusBrandsListUseCase(private val repo: TicketRepoImpl) {

    operator fun invoke(): ArrayList<String> = repo.getBusBrandsList()

}