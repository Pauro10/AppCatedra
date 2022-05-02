package edu.upc.appcatedraunesco

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import edu.upc.appcatedraunesco.databinding.FragmentOpcionesBinding
import edu.upc.appcatedraunesco.models.Usuario
import java.util.*

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
            showShangeLangDialog()
        }

        bindingFragmentOpciones.informarProb.setOnClickListener {
            findNavController().navigate(R.id.action_to_informarProblemaFragment)
        }

        bindingFragmentOpciones.txtCompartirApp.setOnClickListener {
            //findNavController().navigate(R.id.action_to_condicionesFragment)
            view?.snack(getString(R.string.appprueba))
        }

        return this.bindingFragmentOpciones.root
    }

    private fun showShangeLangDialog(){

        val listItems = arrayOf(getString(R.string.language_es), getString(R.string.language_ca), getString(R.string.language_en))
        val mBuilder = AlertDialog.Builder( requireContext() )
        mBuilder.setTitle(getString(R.string.settingsfragment_selectlang))
        mBuilder.setSingleChoiceItems( listItems, -1 ){ dialog, which ->
            when( which ){
                0 -> {
                    this.setLocale("es")
                    requireActivity().recreate()
                }
                1 -> {
                    this.setLocale("")
                    requireActivity().recreate()
                }
                2 -> {
                    this.setLocale("en")
                    requireActivity().recreate()
                }
            }

            dialog.dismiss()
        }
        val mDialog = mBuilder.create()
        mDialog.show()

    }

    private fun setLocale(language: String){
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = Configuration()
        config.locale = locale
        requireContext().resources.updateConfiguration(config, requireContext().resources.displayMetrics )

        val editor = requireActivity().getSharedPreferences("Settings", Activity.MODE_PRIVATE ).edit()
        editor.putString("My_Lang", language)
        editor.apply()
    }

}