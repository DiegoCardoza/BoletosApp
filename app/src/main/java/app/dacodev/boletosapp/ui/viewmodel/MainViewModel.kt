package app.dacodev.boletosapp.ui.viewmodel

import android.content.ClipData
import android.widget.CompoundButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import app.dacodev.boletosapp.BoletosApp.Companion.ticketPreferences
import app.dacodev.boletosapp.R
import app.dacodev.boletosapp.data.model.TicketModel
import app.dacodev.boletosapp.data.model.TicketType
import app.dacodev.boletosapp.data.provider.TicketProvider
import app.dacodev.boletosapp.data.repository.TicketRepoImpl
import app.dacodev.boletosapp.domain.GetFronteraDestinationsListUseCase
import com.google.android.material.radiobutton.MaterialRadioButton

class MainViewModel : ViewModel() {

    private val _destinationsList = MutableLiveData<ArrayList<String>>()
    val destinationsList: LiveData<ArrayList<String>> get() = _destinationsList
    private val _clipData = MutableLiveData<ClipData>()
    val clipData: LiveData<ClipData> get() = _clipData
    private val _ticketData = MutableLiveData<HashMap<String, *>>()
    val ticketData: LiveData<HashMap<String, *>> get() = _ticketData
    private val _totalAmountText = MutableLiveData<String>()
    val totalAmountText: LiveData<String> get() = _totalAmountText
    private val _ticketType = MutableLiveData<Int>()
    val ticketType: LiveData<Int> get() = _ticketType
    private val getDestinationsListUseCase = GetFronteraDestinationsListUseCase(
        TicketRepoImpl(
            TicketProvider()
        )
    )
    private val ticketPrefs = ticketPreferences

    fun onCreate() {
        getDestinationsList()
        getTicketData()
    }

    private fun getTicketData() {
        val hashMapTicket = ticketPrefs.getTicketData()
        _ticketData.postValue(hashMapTicket)
    }

    fun setTicketData(
        name: String,
        seat: String,
        ticketType: Int,
        busNumber: String,
        destinationNumber: Int,
        totalAmount: String
    ) {
        ticketPrefs.updateTicketData(
            name,
            seat,
            ticketType,
            busNumber,
            destinationNumber,
            totalAmount
        )
    }

    private fun getDestinationsList() {
        val destinations = getDestinationsListUseCase()
        _destinationsList.postValue(destinations)
    }

    fun createTicket(ticket: TicketModel) {
        val ticketType = when (ticket.ticketType) {
            TicketType.ADULT -> {
                "ADULTO"
            }
            TicketType.KID -> {
                "NIÑO"
            }
            TicketType.STUDENT -> {
                "ESTUDIANTE"
            }
            else -> {
                "INAPAM"
            }
        }
        val html = "<div style=\"text-align:center\"><b>*** MARCA COMERCIAL***</b></div><div style=\"text-align:center\"><b>TRANSPORTES FRONTERA</b></div><span style=\"font-size:80%;\">SUCURSAL: </span><span style=\"font-size:80%;\"><b>RÍO GRANDE</b></span><span style=\"font-size:80%;\"><div></div>NOMBRE: </span><span style=\"font-size:80%;\"><b>${ticket.name}</b></span><span style=\"font-size:80%;\"><br/>ASIENTO: <b>${ticket.seat}</b><br/>CORRIDA: </span><span style=\"font-size:80%;\"><b>121047</b></span><span style=\"font-size:80%;\"> <br/>SERVICIO: </span><span style=\"font-size:80%;\"><b>ECONÓMICO</b></span><span style=\"font-size:80%;\"><br/>TIPO DE BOLETO: <b>$ticketType</b><br/>AUTOBÚS: </span><span style=\"font-size:80%;\"><b>${ticket.busNumber}</b></span><span style=\"font-size:80%;\"><div></div>ORIGEN: </span><span style=\"font-size:80%;\"><b>RÍO GRANDE ZAC</b></span><span style=\"font-size:80%;\"><br/>DESTINO: </span><span style=\"font-size:80%;\"><b>${ticket.destiny}</b></span><span style=\"font-size:80%;\"><br/>HORARIO DE SALIDA: </span><span style=\"font-size:80%;\"><b>10:00 PM</b></span><span style=\"font-size:80%;\"><br/>SALIDA: </span><span style=\"font-size:80%;\"><b>${ticket.date}</b></span><span style=\"font-size:80%;\"><div></div>IMPORTE TOTAL: </span><b><span style=\"font-size:80%;\">\$${ticket.totalAmount}</span></b><div></div><span style=\"text\">ES VALIDO UNICAMENTE PARA LA HORA FECHA ANTES DEL HORARIO DE SU VIAJE AV PONIENTE 140 No 859 COL INDUSTRIAL VALLEJO DEL AZCAPOTZALCO CP 02300 - CDMX VIGENTES EN LA CDMX POR PIEZA Y ORIGEN/DESTINO IMPRESOS, CUALQUIER CAMBIO EN LAS CONDICIONES DEL SERVICIO DEBE REALIZARSE CUANDO MENOS 2 HRS ANTES DE LA HORA FIJADA PARA REALIZAR EL VIAJE. TÉRMINOS Y CONDICIONES </span><span style=\"font-size:70%;\">WWW.ESTRELLABLANCA.COM.MX</span><span style=\"font-size:70em;\"><br/>DOCUMENTE SU EQUIPAJE</span>"
        val clip = ClipData.newHtmlText("ticket", "", html)
        _clipData.postValue(clip)
    }

    fun updateItemClickTotalAmountText(
        position: Int,
        rbAdult: MaterialRadioButton
    ) {
        when (position) {
            0 -> _totalAmountText.postValue("")
            1 -> _totalAmountText.postValue(if (rbAdult.isChecked) "745" else "372.5")
            2 -> _totalAmountText.postValue(if (rbAdult.isChecked) "650" else "325")
            3 -> _totalAmountText.postValue(if (rbAdult.isChecked) "600" else "300")
            4 -> _totalAmountText.postValue(if (rbAdult.isChecked) "600" else "300")
        }
    }

    fun updateCheckedChangeTotalAmountText(
        button: CompoundButton,
        isChecked: Boolean,
        position: Int
    ) {
        when (button.id) {
            R.id.rbAdult -> {
                onCheckedChangedAdult(isChecked, position)
                _ticketType.postValue(if (isChecked) 0 else ticketType.value)
            }
            R.id.rbKid -> {
                onCheckedChangedKidOrINAPAM(isChecked, position)
                _ticketType.postValue(if (isChecked) 1 else ticketType.value)
            }
            R.id.rbINAPAM -> {
                onCheckedChangedKidOrINAPAM(isChecked, position)
                _ticketType.postValue(if (isChecked) 2 else ticketType.value)
            }
        }
    }

    private fun onCheckedChangedAdult(isChecked: Boolean, position: Int) {
        if (isChecked) {
            when (position) {
                0 -> {
                    _totalAmountText.postValue("")
                }
                1 -> {
                    _totalAmountText.postValue("745")
                }
                2 -> {
                    _totalAmountText.postValue("650")
                }
                3 -> {
                    _totalAmountText.postValue("600")
                }
                4 -> {
                    _totalAmountText.postValue("600")
                }
            }
        }
    }

    private fun onCheckedChangedKidOrINAPAM(isChecked: Boolean, position: Int) {
        if (isChecked) {
            when (position) {
                0 -> {
                    _totalAmountText.postValue("")
                }
                1 -> {
                    _totalAmountText.postValue("372.5")
                }
                2 -> {
                    _totalAmountText.postValue("325")
                }
                3 -> {
                    _totalAmountText.postValue("300")
                }
                4 -> {
                    _totalAmountText.postValue("300")
                }
            }
        }
    }

}