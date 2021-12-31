package app.dacodev.boletosapp

import android.app.Application
import app.dacodev.boletosapp.core.TicketPreferences

class BoletosApp : Application() {

    companion object {
        lateinit var ticketPreferences: TicketPreferences
    }

    override fun onCreate() {
        super.onCreate()

        ticketPreferences = TicketPreferences(applicationContext)
    }

}