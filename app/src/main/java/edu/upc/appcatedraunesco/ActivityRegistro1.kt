package edu.upc.appcatedraunesco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import edu.upc.appcatedraunesco.databinding.ActivityRegistro1Binding

class ActivityRegistro1 : AppCompatActivity() {

    private lateinit var bindingRegistro1: ActivityRegistro1Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingRegistro1 = DataBindingUtil.setContentView(this, R.layout.activity_registro1)

        // Listener para boton volver
        this.bindingRegistro1.btnVolver.setOnClickListener {
            cambiarPantalla(it, 0)
        }

        // Next Signup Screen
        this.bindingRegistro1.btnSiguiente.setOnClickListener {

            if( !comprovarCampos(it) ){
                return@setOnClickListener
            }

            // Camps Comprovats
            cambiarPantalla(it, 1)
        }

        // Pantalla Identificarse
        this.bindingRegistro1.txtIdentificar.setOnClickListener {
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
                intent = Intent( applicationContext, ActivityRegistro2::class.java ).apply {
                    // Preparamos los datos del usuario
                    this.putExtra("EXTRA_SESSION_NOMBRECOMPLETO", bindingRegistro1.tfNombre.text.toString() )
                    this.putExtra("EXTRA_SESSION_DNI", bindingRegistro1.tfDni.text.toString() )
                    this.putExtra("EXTRA_SESSION_CORREO", bindingRegistro1.tfMail.text.toString() )
                    this.putExtra("EXTRA_SESSION_PASSWORD", bindingRegistro1.tfContrasena.text.toString() )
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

    private fun comprovarCampos(view: View): Boolean {

        // Validate password
        return if( this.bindingRegistro1.tfContrasena.text.toString() == "" || this.bindingRegistro1.tfContrasena.text.toString().length < 6 ) {
            view.snack((getString(R.string.error_pass)))
            false
        } else if( this.bindingRegistro1.tfMail.text.toString() == "" || !this.bindingRegistro1.tfMail.text.toString().contains(regex = email_Param.toRegex())) {
            view.snack((getString(R.string.error_mail)))
            false
        } else if( this.bindingRegistro1.tfDni.text.toString() == "" ) {
            view.snack((getString(R.string.error_dni)))
            false
        } else if( this.bindingRegistro1.tfNombre.text.toString() == "" ) {
            view.snack((getString(R.string.error_nombre)))
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