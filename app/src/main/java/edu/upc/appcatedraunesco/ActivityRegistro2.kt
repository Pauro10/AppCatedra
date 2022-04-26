package edu.upc.appcatedraunesco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import edu.upc.appcatedraunesco.databinding.ActivityRegistro2Binding

class ActivityRegistro2 : AppCompatActivity() {

    private lateinit var bindingRegistro2: ActivityRegistro2Binding

    // User variables
    private lateinit var tFechaNacimiento: String
    private lateinit var tGenero: String
    private lateinit var tNombreCompleto: String
    private lateinit var tNif: String
    private lateinit var tEmail: String
    private lateinit var tPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingRegistro2 = DataBindingUtil.setContentView(this, R.layout.activity_registro2)

        // Informació Usuario
        this.tNombreCompleto = intent.getStringExtra("EXTRA_SESSION_NOMBRECOMPLETO").toString()
        this.tNif = intent.getStringExtra("EXTRA_SESSION_DNI").toString()
        this.tEmail = intent.getStringExtra("EXTRA_SESSION_CORREO").toString()
        this.tPassword = intent.getStringExtra("EXTRA_SESSION_PASSWORD").toString()

        // LISTENERS

        // Pantalla registro siguiente
        this.bindingRegistro2.btnSiguiente.setOnClickListener {

            it.snack("$tNombreCompleto($tNif),email:$tEmail --> $tPassword")

            // Validate DatePicker
            val selectedDay: Int = this.bindingRegistro2.dpNacimiento.dayOfMonth
            val selectedMonth: Int = this.bindingRegistro2.dpNacimiento.month + 1
            val selectedYear: Int = this.bindingRegistro2.dpNacimiento.year
            this.tFechaNacimiento = "$selectedDay/$selectedMonth/$selectedYear"

            // Validació RadioButtons
            when {
                this.bindingRegistro2.rbFemale.isChecked -> {
                    this.tGenero = "F"
                }
                this.bindingRegistro2.rbMale.isChecked -> {
                    this.tGenero = "M"
                }
                else -> {
                    this.tGenero = "O"
                }
            }
            cambiarPantalla(it, 2)
        }

        // Pantalla Login
        this.bindingRegistro2.txtIdentificar.setOnClickListener {
            cambiarPantalla(it, 1)
        }

        // Pantalla Back
        this.bindingRegistro2.btnVolver.setOnClickListener {
            cambiarPantalla(it, 0)
        }

        fullScreenMode()
    }

    private fun cambiarPantalla(view: View, option: Int) {
        var intent = Intent()

        when (option) {
            0 -> {
                // Pantalla Back
                intent = Intent(applicationContext, ActivityLogin::class.java)
            }
            1 -> {
                // Pantalla Login
                intent = Intent(applicationContext, ActivityLogin::class.java)
            }
            2 -> {
                // Pantalla registro 3
                intent = Intent(applicationContext, ActivityRegistro3::class.java).apply {
                    this.putExtra("EXTRA_SESSION_NOMBRECOMPLETO", tNombreCompleto)
                    this.putExtra("EXTRA_SESSION_CORREO", tEmail)
                    this.putExtra("EXTRA_SESSION_PASSWORD", tPassword)
                    this.putExtra("EXTRA_SESSION_DNI", tNif)
                    this.putExtra("EXTRA_SESSION_GENDER", tGenero)
                    this.putExtra("EXTRA_SESSION_FECHANACIMIENTO", tFechaNacimiento)
                }
            }
        }

        startActivity(intent)
        if (option == 0) {
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        } else {
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        finish()
    }

    private fun fullScreenMode() {
        // Amagar actionBar i mode fullScreen
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}