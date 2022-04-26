package edu.upc.appcatedraunesco

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import edu.upc.appcatedraunesco.databinding.FragmentOpcionesBinding
import edu.upc.appcatedraunesco.models.Usuario

class OpcionesFragment : Fragment() {

    private lateinit var bindingFragmentOpciones: FragmentOpcionesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this.bindingFragmentOpciones =
            DataBindingUtil.inflate(inflater, R.layout.fragment_opciones, container, false)

        if (FirebaseAuth.getInstance().currentUser == null) {
            bindingFragmentOpciones.txtLogIn.setVisibility(VISIBLE)
            bindingFragmentOpciones.txtLogOut.setVisibility(GONE)
        } else {
            bindingFragmentOpciones.txtLogIn.setVisibility(GONE)
            bindingFragmentOpciones.txtLogOut.setVisibility(VISIBLE)
        }

        bindingFragmentOpciones.txtLogIn.setOnClickListener {
            val intent = Intent(requireContext(), ActivityLogin::class.java)
            startActivity(intent)
        }

        bindingFragmentOpciones.txtLogOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(requireContext(), MainActivity::class.java))
            Usuario(
                "",
                "",
                "",
                "",
                "",
                "",
                ""
            )
        }

        bindingFragmentOpciones.txtCambiarIdioma.setOnClickListener {
            //findNavController().navigate(R.id.action_to_privacidadFragment)
        }

        bindingFragmentOpciones.informarProb.setOnClickListener {
            findNavController().navigate(R.id.action_to_informarProblemaFragment)
        }

        bindingFragmentOpciones.txtCompartirApp.setOnClickListener {
            //findNavController().navigate(R.id.action_to_condicionesFragment)
        }

        return this.bindingFragmentOpciones.root
    }

}