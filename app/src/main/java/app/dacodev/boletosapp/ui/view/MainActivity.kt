package app.dacodev.boletosapp.ui.view

import android.annotation.SuppressLint
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import app.dacodev.boletosapp.R
import app.dacodev.boletosapp.databinding.ActivityMainBinding
import app.dacodev.boletosapp.data.model.TicketModel
import app.dacodev.boletosapp.data.model.TicketType
import app.dacodev.boletosapp.ui.viewmodel.MainViewModel
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), CompoundButton.OnCheckedChangeListener,
    View.OnClickListener, AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private var ticketType = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()
    }

    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        viewModel.updateCheckedChangeTotalAmountText(
            buttonView!!,
            isChecked,
            binding.spDestination.selectedItemPosition
        )
    }

    override fun onClick(view: View?) {
        when (view!!) {
            binding.btnCreateTicket -> createTicket()
        }
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        viewModel.updateItemClickTotalAmountText(position, binding.rbAdult)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}


    override fun onStop() {
        viewModel.setTicketData(
            name = binding.etName.text?.toString() ?: "",
            seat = binding.etSeat.text?.toString() ?: "",
            ticketType = ticketType,
            busNumber = binding.etBusNumber.text?.toString() ?: "",
            destinationNumber = binding.spDestination.selectedItemPosition,
            totalAmount = binding.etTotalAmount.text?.toString() ?: ""
        )
        super.onStop()
    }

    private fun initUI() {
        addObservers()
        addListeners()
    }

    private fun addObservers() {
        viewModel.ticketData.observe(this) {
            binding.etName.text = Editable.Factory().newEditable(it["name"].toString())
            binding.etSeat.text = Editable.Factory().newEditable(it["seat"].toString())
            when (it["ticketType"] as Int) {
                0 -> {
                    binding.rbAdult.isChecked = true
                    binding.rbKid.isChecked = false
                    binding.rbINAPAM.isChecked = false
                }
                1 -> {
                    binding.rbAdult.isChecked = false
                    binding.rbKid.isChecked = true
                    binding.rbINAPAM.isChecked = false
                }
                2 -> {
                    binding.rbAdult.isChecked = false
                    binding.rbKid.isChecked = false
                    binding.rbINAPAM.isChecked = true
                }
            }
            binding.etBusNumber.text = Editable.Factory().newEditable(it["busNumber"].toString())
            binding.spDestination.setSelection(it["destinationNumber"] as Int, true)
            binding.etTotalAmount.text =
                Editable.Factory().newEditable(it["totalAmount"].toString())
        }
        viewModel.destinationsList.observe(this) {
            binding.spDestination.adapter = ArrayAdapter(
                this@MainActivity,
                R.layout.item_spinner_destination,
                it
            )
            binding.spDestination.onItemSelectedListener = this
        }
        viewModel.totalAmountText.observe(this) {
            binding.etTotalAmount.text = Editable.Factory().newEditable(it ?: "")
        }
        viewModel.ticketType.observe(this) {
            ticketType = it
        }
        viewModel.clipData.observe(this) {
            val clipBoard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipBoard.setPrimaryClip(it)

            Toast.makeText(this, "Boleto creado y copiado", Toast.LENGTH_SHORT).show()
        }
        viewModel.onCreate()
    }

    private fun addListeners() {
        binding.rbAdult.setOnCheckedChangeListener(this)
        binding.rbKid.setOnCheckedChangeListener(this)
        binding.rbINAPAM.setOnCheckedChangeListener(this)
        binding.btnCreateTicket.setOnClickListener(this)
    }

    @SuppressLint("SimpleDateFormat")
    private fun createTicket() {
        if (!binding.etName.text.isNullOrEmpty() &&
            !binding.etSeat.text.isNullOrEmpty() &&
            !binding.etBusNumber.text.isNullOrEmpty() &&
            binding.spDestination.selectedItemPosition != 0 &&
            !binding.etTotalAmount.text.isNullOrEmpty()
        ) {
            val ticket = TicketModel(
                name = binding.etName.text!!.toString().uppercase(),
                seat = binding.etSeat.text!!.toString().toInt(),
                ticketType = when {
                    binding.rbAdult.isChecked -> {
                        TicketType.ADULT
                    }
                    binding.rbKid.isChecked -> {
                        TicketType.KID
                    }
                    else -> {
                        TicketType.INAPAM
                    }
                },
                busNumber = binding.etBusNumber.text!!.toString().toInt(),
                destiny = binding.spDestination.selectedItem.toString(),
                totalAmount = binding.etTotalAmount.text!!.toString(),
                date = SimpleDateFormat("dd/MM/yyyy").format(Date())
            )
            viewModel.createTicket(ticket)
        } else {
            Toast.makeText(this@MainActivity, "Falta de llenar campos", Toast.LENGTH_SHORT)
                .show()
        }
    }

}