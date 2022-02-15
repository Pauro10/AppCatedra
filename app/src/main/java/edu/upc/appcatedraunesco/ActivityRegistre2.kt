package edu.upc.appcatedraunesco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import edu.upc.appcatedraunesco.databinding.ActivityRegistre2Binding

class ActivityRegistre2 : AppCompatActivity() {

    private lateinit var bindingRegistre2: ActivityRegistre2Binding

    // User variables
    private lateinit var tDataNaixement: String
    private lateinit var tGender: String
    private lateinit var tNomComplert: String
    private lateinit var tNif: String
    private lateinit var tEmail: String
    private lateinit var tPassword: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingRegistre2 = DataBindingUtil.setContentView(this, R.layout.activity_registre2)

        // Informació Usuari
        this.tNomComplert  = intent.getStringExtra("EXTRA_SESSION_NOMCOMPLERT").toString()
        this.tNif  = intent.getStringExtra("EXTRA_SESSION_DNI").toString()
        this.tEmail     = intent.getStringExtra("EXTRA_SESSION_CORREU").toString()
        this.tPassword  = intent.getStringExtra("EXTRA_SESSION_PASSWORD").toString()

        // LISTENERS

        // Pantalla registre següent
        this.bindingRegistre2.btnSeguent.setOnClickListener {

            it.snack("$tNomComplert($tNif),email:$tEmail --> $tPassword")

            // Validate DatePicker
            val selectedDay: Int = this.bindingRegistre2.dpNacimiento.dayOfMonth
            val selectedMonth: Int = this.bindingRegistre2.dpNacimiento.month + 1
            val selectedYear: Int = this.bindingRegistre2.dpNacimiento.year
            this.tDataNaixement = "$selectedDay/$selectedMonth/$selectedYear"

            // Validació RadioButtons
            when {
                this.bindingRegistre2.rbFemale.isChecked -> {
                    this.tGender = "F"
                }
                this.bindingRegistre2.rbMale.isChecked -> {
                    this.tGender = "M"
                }
                else -> {
                    this.tGender = "O"
                }
            }
            cambiarPantalla(it, 2)
        }

        // Pantalla Login
        this.bindingRegistre2.txtIdentificar.setOnClickListener {
            cambiarPantalla(it, 1)
        }

        // Pantalla Back
        this.bindingRegistre2.btnTornar.setOnClickListener {
            cambiarPantalla(it, 0)
        }

        fullScreenMode()
    }

    private fun cambiarPantalla(view: View, option: Int ){
        var intent = Intent()

        when(option){
            0 -> {
                // Pantalla Back
                intent = Intent(applicationContext, ActivityLogin::class.java)
            }
            1 -> {
                // Pantalla Login
                intent = Intent(applicationContext, ActivityLogin::class.java)
            }
            2 -> {
                // Pantalla registre
                intent = Intent(applicationContext, ActivityRegistre3::class.java).apply {
                    this.putExtra("EXTRA_SESSION_NOMCOMPLERT", tNomComplert )
                    this.putExtra("EXTRA_SESSION_CORREU", tEmail )
                    this.putExtra("EXTRA_SESSION_PASSWORD", tPassword )
                    this.putExtra("EXTRA_SESSION_DNI", tNif )
                    this.putExtra("EXTRA_SESSION_GENDER", tGender )
                    this.putExtra("EXTRA_SESSION_DATANAIXEMENT", tDataNaixement )
                }
            }
        }

        startActivity(intent)
        if(option == 0){
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }else{
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