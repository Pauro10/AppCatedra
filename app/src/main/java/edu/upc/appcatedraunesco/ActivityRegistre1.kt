package edu.upc.appcatedraunesco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import edu.upc.appcatedraunesco.databinding.ActivityRegistre1Binding

class ActivityRegistre1 : AppCompatActivity() {

    private lateinit var bindingRegistre1: ActivityRegistre1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingRegistre1 = DataBindingUtil.setContentView(this, R.layout.activity_registre1)

        // Boto Tornar Enrrere
        this.bindingRegistre1.btnTornar.setOnClickListener {
            cambiarPantalla(it, 0)
        }

        // Next Signup Screen
        this.bindingRegistre1.btnSeguent.setOnClickListener {

            if( !comprovarCamps(it) ){
                return@setOnClickListener
            }

            // Camps Comprovats
            cambiarPantalla(it, 1)
        }

        // Pantalla Identifica't
        this.bindingRegistre1.txtIdentificar.setOnClickListener {
            cambiarPantalla(it, 2)
        }

        fullScreenMode()
    }

    private fun cambiarPantalla(view: View, option: Int ){

        var intent = Intent()

        when(option){
            0 -> {
                // Atrás
                intent = Intent( applicationContext, ActivityLogin::class.java )
            }
            1 -> {
                // Següent
                intent = Intent( applicationContext, ActivityRegistre2::class.java ).apply {
                    // Preparamos los datos del usuario
                    this.putExtra("EXTRA_SESSION_NOMCOMPLERT", bindingRegistre1.tfNom.text.toString() )
                    this.putExtra("EXTRA_SESSION_DNI", bindingRegistre1.tfDni.text.toString() )
                    this.putExtra("EXTRA_SESSION_CORREU", bindingRegistre1.tfMail.text.toString() )
                    this.putExtra("EXTRA_SESSION_PASSWORD", bindingRegistre1.tfContrasena.text.toString() )
                }
            }
            2 -> {
                // Login
                intent = Intent( applicationContext, ActivityLogin::class.java )
            }
        }

        startActivity(intent)
        if( option == 0 ){
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
        }else{
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        }
        finish()
    }

    private fun comprovarCamps(view: View): Boolean {

        // Validate password
        return if( this.bindingRegistre1.tfContrasena.text.toString() == "" || this.bindingRegistre1.tfContrasena.text.toString().length < 6 ) {
            view.snack("La contrasenya ha de contenir més de 6 caràcters")
            false
        } else if( this.bindingRegistre1.tfMail.text.toString() == "" || !this.bindingRegistre1.tfMail.text.toString().contains(regex = email_Param.toRegex())) {
            view.snack("Has d'introduir un correu vàlid")
            false
        } else if( this.bindingRegistre1.tfDni.text.toString() == "" ) {
            view.snack("No pots deixar sense omplir el DNI")
            false
        } else if( this.bindingRegistre1.tfNom.text.toString() == "" ) {
            view.snack("No pots deixar sense omplir el nom")
            false
        } else{
            true
        }

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