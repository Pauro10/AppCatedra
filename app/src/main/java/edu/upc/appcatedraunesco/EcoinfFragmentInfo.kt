package edu.upc.appcatedraunesco

import android.content.Intent.getIntent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.GoogleMap
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.squareup.picasso.Picasso
import edu.upc.appcatedraunesco.databinding.FragmentEcoinfInfoBinding


class EcoinfFragmentInfo : Fragment() {

    private lateinit var bindingFragmentEcoinfInfo: FragmentEcoinfInfoBinding
    private lateinit var map: GoogleMap
    private lateinit var dbReference: DatabaseReference
    private lateinit var ecoinfQuery: Query

    private val args by navArgs<EcoinfFragmentInfoArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.bindingFragmentEcoinfInfo =
            DataBindingUtil.inflate(inflater, R.layout.fragment_ecoinf_info, container, false)

        this.bindingFragmentEcoinfInfo.btnAtras.setOnClickListener {
            getFragmentManager()?.popBackStack()
        }

        bindingFragmentEcoinfInfo.tvNombre.setText(args.currentEcoinf.nombre)
        bindingFragmentEcoinfInfo.tfDireccion.setText(args.currentEcoinf.direccion)
        bindingFragmentEcoinfInfo.tftelefono.setText(args.currentEcoinf.numeroTelefono)
        bindingFragmentEcoinfInfo.tfUrl.setText(args.currentEcoinf.urlPagina)
        Picasso.get().load(args.currentEcoinf.imagen).into(bindingFragmentEcoinfInfo.imagen)

        dbReference = FirebaseDatabase.getInstance().reference;
        ecoinfQuery = dbReference.child("Ecoinfraestructura").orderByChild("nombre").equalTo(args.currentEcoinf.nombre)


        bindingFragmentEcoinfInfo.tfUrl.isFocusable = false
        bindingFragmentEcoinfInfo.tftelefono.isFocusable = false
        bindingFragmentEcoinfInfo.tfDireccion.isFocusable = false

        if (bindingFragmentEcoinfInfo.tfUrl.text!!.isEmpty()) {
            bindingFragmentEcoinfInfo.textInputLayout4.visibility = View.GONE
        }

        if (FirebaseAuth.getInstance().currentUser == null) {
            bindingFragmentEcoinfInfo.btEditar.visibility = View.GONE
            bindingFragmentEcoinfInfo.btEliminar.visibility = View.GONE
        } else {
            //bindingFragmentEcoinfInfo.btEditar.visibility = View.VISIBLE
            bindingFragmentEcoinfInfo.btEliminar.visibility = View.VISIBLE
        }

        bindingFragmentEcoinfInfo.btEliminar.setOnClickListener {

            MaterialAlertDialogBuilder( requireContext() )
                .setTitle(getString(R.string.txt_eliminarecoinf))
                .setMessage(getString(R.string.txt_eliminarecoinf2))
                .setNegativeButton(getString(R.string.txt_cancelar)) { dialog, which ->
                    // Respond to negative button press
                }
                .setPositiveButton(getString(R.string.txt_aceptar)) { dialog, which ->
                    ecoinfQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            for (ecoinfSnapshot in snapshot.children) {
                                ecoinfSnapshot.ref.removeValue()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            TODO("Not yet implemented")
                        }
                    })
                    view?.snack(getString(R.string.ecoinfelim))
                    findNavController().navigate(R.id.action_to_mapsFragment)
                }
                .show()

        }

        return this.bindingFragmentEcoinfInfo.root
    }

}