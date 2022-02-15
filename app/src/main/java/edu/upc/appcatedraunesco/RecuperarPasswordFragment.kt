package edu.upc.appcatedraunesco

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import edu.upc.appcatedraunesco.databinding.FragmentRecuperarPasswordBinding

class RecuperarPasswordFragment : DialogFragment() {

    private lateinit var bindingRecuperarPassword: FragmentRecuperarPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentRecuperarPasswordBinding.inflate(inflater, container, false)
        bindingRecuperarPassword = binding

        bindingRecuperarPassword.btnEnviar.setOnClickListener {
            cambiarPassword()
            dismiss()
        }

        return binding.root
    }


    private fun cambiarPassword() {
        val email = bindingRecuperarPassword.tfMail.getText().toString()
        if (isValidEmail(email)) {
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        showAlert()
                        //view?.snack("Correu enviat correctament!")
                    } else {
                        view?.snack("Error en enviar el correu electrònic")
                    }
                }
        } else {
            view?.snack("El format de l'email és invàlid")
        }
    }

    private fun showAlert() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Correu enviat correctament!")
        builder.setMessage("Revisa el teu correu electrònic per canviar la contrasenya")
        builder.setPositiveButton(getString(R.string.infoAcceptar), null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

}