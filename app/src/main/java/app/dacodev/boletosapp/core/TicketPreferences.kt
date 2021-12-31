package app.dacodev.boletosapp.core

import android.content.Context
import android.content.SharedPreferences
import app.dacodev.boletosapp.core.PreferencesConstants.BUS_BRAND
import app.dacodev.boletosapp.core.PreferencesConstants.BUS_NUMBER
import app.dacodev.boletosapp.core.PreferencesConstants.DESTINATION_NUMBER
import app.dacodev.boletosapp.core.PreferencesConstants.NAME
import app.dacodev.boletosapp.core.PreferencesConstants.NAME_PREFERENCES
import app.dacodev.boletosapp.core.PreferencesConstants.SEAT
import app.dacodev.boletosapp.core.PreferencesConstants.TICKET_TYPE
import app.dacodev.boletosapp.core.PreferencesConstants.TOTAL_AMOUNT

class TicketPreferences(context: Context) {

    private val storage: SharedPreferences = context.getSharedPreferences(NAME_PREFERENCES, 0)

    fun getTicketData(): HashMap<String, *> {
        val name = storage.getString(NAME, "") as String
        val busBrand = storage.getString(BUS_BRAND,"") as String
        val seat = storage.getString(SEAT, "") as String
        val ticketType = storage.getInt(TICKET_TYPE, 0)
        val busNumber = storage.getString(BUS_NUMBER, "") as String
        val destinationNumber = storage.getInt(DESTINATION_NUMBER, 0)
        val totalAmount = storage.getString(TOTAL_AMOUNT, "") as String
        return hashMapOf<String, Any>(
            NAME to name,
            BUS_BRAND to busBrand,
            SEAT to seat,
            TICKET_TYPE to ticketType,
            BUS_NUMBER to busNumber,
            DESTINATION_NUMBER to destinationNumber,
            TOTAL_AMOUNT to totalAmount
        )
    }

    fun updateTicketData(
        name: String,
        seat: String,
        ticketType: Int,
        busNumber: String,
        destinationNumber: Int,
        totalAmount: String
    ) {
        storage.edit().putString(NAME, name).apply()
        storage.edit().putString(SEAT, seat).apply()
        storage.edit().putInt(TICKET_TYPE, ticketType).apply()
        storage.edit().putString(BUS_NUMBER, busNumber).apply()
        storage.edit().putInt(DESTINATION_NUMBER, destinationNumber).apply()
        storage.edit().putString(TOTAL_AMOUNT, totalAmount).apply()
    }

}