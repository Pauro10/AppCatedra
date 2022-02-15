package edu.upc.appcatedraunesco

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import edu.upc.appcatedraunesco.databinding.ActivityRegistre3Binding
import edu.upc.appcatedraunesco.models.Usuari

class ActivityRegistre3 : AppCompatActivity() {

    private lateinit var bindingRegistre3: ActivityRegistre3Binding
    private lateinit var auth: FirebaseAuth

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    private var showSnack: Boolean = false

    private lateinit var tDataNaixement: String
    private lateinit var tGender: String
    private lateinit var tNomComplert: String
    private lateinit var tNif: String
    private lateinit var tEmail: String
    private lateinit var tPassword: String
    private lateinit var tNumeroTelefon: String
    private lateinit var tUid: String

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingRegistre3 = DataBindingUtil.setContentView(this, R.layout.activity_registre3)

        // Initialize Firebase Auth
        this.auth = FirebaseAuth.getInstance()

        // Instanciate database
        database = FirebaseDatabase.getInstance()
        // Reference working document
        reference = database.getReference("USUARI/CLIENT")

        // Informació Usuari
        this.tNomComplert = intent.getStringExtra("EXTRA_SESSION_NOMCOMPLERT").toString()
        this.tNif = intent.getStringExtra("EXTRA_SESSION_DNI").toString()
        this.tEmail = intent.getStringExtra("EXTRA_SESSION_CORREU").toString()
        this.tPassword = intent.getStringExtra("EXTRA_SESSION_PASSWORD").toString()
        this.tGender = intent.getStringExtra("EXTRA_SESSION_GENDER").toString()
        this.tDataNaixement = intent.getStringExtra("EXTRA_SESSION_DATANAIXEMENT").toString()

        // Back Screenn
        this.bindingRegistre3.btnTornar.setOnClickListener {
            cambiarPantalla(it, 0)
        }

        // Login Screen
        this.bindingRegistre3.txtIdentificar.setOnClickListener {
            cambiarPantalla(it, 1)
        }

        // Validate Signup
        this.bindingRegistre3.btnFinalitza.setOnClickListener {
            if (comprovarCamps(it)) {
                signUp(it, this.tEmail, this.tPassword)
            } else {
                return@setOnClickListener
            }
        }

        fullScreenMode()
    }

    private fun cambiarPantalla(view: View, option: Int) {
        var intent = Intent()

        when (option) {
            0 -> {
                // Back Screen
                intent = Intent(applicationContext, ActivityRegistre2::class.java)
            }
            1 -> {
                // Login Screen
                intent = Intent(
                    applicationContext,
                    ActivityLogin::class.java
                ).apply { putExtra("EXTRA_SHOW_SNACK", showSnack) }
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

    private fun comprovarCamps(view: View): Boolean {
        return if (this.bindingRegistre3.tfTel.text.toString() == "" || this.bindingRegistre3.tfTel.text.toString().length != 9) {
            view.snack("Has d'introduir un número de telèfon vàlid")
            false
        } else {
            true
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun signUp(view: View, email: String, pass: String) {
        this.auth.createUserWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user: FirebaseUser? = task.getResult()!!.user
                    referenceUserDatabase(view, user)
                    // Change to Login screen
                    showSnack = true
                    cambiarPantalla(view, 1)
                } else {
                    view.snack("No hem pogut registrar-te")
                }
            }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun referenceUserDatabase(view: View, user: FirebaseUser?) {
        //Collect user data
        this.tNumeroTelefon = this.bindingRegistre3.tfTel.text.toString().trim()

        if (user != null) {
            this.tUid = user.uid
        } else {
            return
        }
        val userModel = Usuari(
            uidCliente = user.uid,
            NomComplert = this.tNomComplert,
            correu = this.tEmail,
            nif = this.tNif,
            gender = this.tGender,
            dataNaixement = this.tDataNaixement,
            numeroTelefon = this.tNumeroTelefon,
        )

        //val id = this.reference.push().key

        // Send data to Firebase
        this.reference.child(tUid).setValue(userModel)
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