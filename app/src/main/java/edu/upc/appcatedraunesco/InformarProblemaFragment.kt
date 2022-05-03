package edu.upc.appcatedraunesco

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import edu.upc.appcatedraunesco.databinding.FragmentInformarProblemaBinding
import edu.upc.appcatedraunesco.models.Problema
import edu.upc.appcatedraunesco.models.Producto

class InformarProblemaFragment : DialogFragment() {

    private lateinit var bindingFragmentInformarProblema: FragmentInformarProblemaBinding

    private lateinit var auth: FirebaseAuth

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentInformarProblemaBinding.inflate(inflater, container, false)
        bindingFragmentInformarProblema = binding

        // Initialize Firebase Auth
        this.auth = FirebaseAuth.getInstance()

        // Instanciate database
        database = FirebaseDatabase.getInstance()
        // Reference working document
        reference = database.getReference("Problemas")

        bindingFragmentInformarProblema.btnEnviar.setOnClickListener {
            crearConsultaProblema(it)
            dismiss()
        }

        return binding.root
    }

    private fun crearConsultaProblema(view: View) {

        val descripcion = this.bindingFragmentInformarProblema.tfDescripcion.text.toString().trim()

        val problema = Problema(
            descripcion = descripcion
        )
        val id = this.reference.push().key

        this.reference.child(id!!).setValue(problema)
        view?.snack(getString(R.string.txtConsulta))
        // Retroalimentació

    }

}
