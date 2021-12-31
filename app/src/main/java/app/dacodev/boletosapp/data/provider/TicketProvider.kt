package app.dacodev.boletosapp.data.provider

class TicketProvider {

    private val busBrandsList = arrayListOf(
        "FRONTERA",
        "ROJO DE LOS ALTOS"
    )

    private val fronteraDestinations = arrayListOf(
        "Seleccionar destino",
        "MONTERREY NL",
        "SANTA CATARINA NL",
        "SALTILLO COA",
        "RAMOS ARIZPE COA"
    )

    private val rojoDeLosAltosDestinations = arrayListOf(
        "Seleccionar destino",
        "JUAN ALDAMA",
        "TORREÓN",
        "CD. JIMÉNEZ",
        "CD. CAMARGO",
        "CHIHUAHUA",
        "PISTOLAS MENESES",
        "CD. JUÁREZ"
    )

    fun getBusBrandsList(): ArrayList<String> {
        return busBrandsList
    }

    fun getFronteraDestinationList(): ArrayList<String> {
        return fronteraDestinations
    }

    fun getRojoDeLosAltosDestinationList(): ArrayList<String> {
        return rojoDeLosAltosDestinations
    }

}