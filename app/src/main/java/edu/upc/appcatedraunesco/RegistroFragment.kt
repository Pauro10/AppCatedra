package edu.upc.appcatedraunesco

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import edu.upc.appcatedraunesco.databinding.FragmentRegistroBinding

class RegistroFragment : Fragment() {

    private lateinit var bindingFragmentRegistro: FragmentRegistroBinding

    private lateinit var auth: FirebaseAuth

    private lateinit var database: FirebaseDatabase

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this.bindingFragmentRegistro =
            DataBindingUtil.inflate(inflater, R.layout.fragment_registro, container, false)

        // Initialize Firebase Auth
        this.auth = FirebaseAuth.getInstance()

        // Instanciate database
        database = FirebaseDatabase.getInstance()

        this.bindingFragmentRegistro.btnVolver.setOnClickListener {
            getFragmentManager()?.popBackStack()
        }

        bindingFragmentRegistro.btnCrear.setOnClickListener {
            if (comprovarCampos(it)) {
                signUp(
                    it,
                    bindingFragmentRegistro.tfMail.text.toString(),
                    bindingFragmentRegistro.tfContrasena.text.toString()
                )
            } else {
                return@setOnClickListener
            }
        }


        return this.bindingFragmentRegistro.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun signUp(view: View, email: String, pass: String) {
        this.auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener {
            if (it.isSuccessful) {
                view.snack("Usuario Creado Correctamente!")
                showHome()
            } else {
                view.snack(getString(R.string.error_registro))
            }
        }
    }

    private fun comprovarCampos(view: View): Boolean {

        // Validate password
        return if (this.bindingFragmentRegistro.tfContrasena.text.toString() == "" || this.bindingFragmentRegistro.tfContrasena.text.toString().length < 6) {
            view.snack((getString(R.string.error_pass)))
            false
        } else if (this.bindingFragmentRegistro.tfMail.text.toString() == "" || !this.bindingFragmentRegistro.tfMail.text.toString()
                .contains(regex = email_Param.toRegex())
        ) {
            view.snack((getString(R.string.error_mail)))
            false
        } else if (this.bindingFragmentRegistro.tfConfirmaContrasena.text.toString() != bindingFragmentRegistro.tfContrasena.text.toString()) {
            view.snack("La contraseña no coincide!")
            false
        } else if (this.bindingFragmentRegistro.tfConfirmaContrasena.text.toString() == "") {
            view.snack("Tienes que confirmar la contraseña!")
            false
        } else {
            true
        }

    }

    private fun showHome() {
        findNavController().navigate(R.id.action_to_adminFragment)
    }
}