package edu.upc.appcatedraunesco

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.squareup.picasso.Picasso
import edu.upc.appcatedraunesco.databinding.FragmentEcoinfInfoBinding


class EcoinfFragmentInfo : Fragment() {

    private lateinit var bindingFragmentEcoinfInfo: FragmentEcoinfInfoBinding
    private lateinit var map: GoogleMap

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


        bindingFragmentEcoinfInfo.tfUrl.isFocusable = false
        bindingFragmentEcoinfInfo.tftelefono.isFocusable = false
        bindingFragmentEcoinfInfo.tfDireccion.isFocusable = false

        return this.bindingFragmentEcoinfInfo.root
    }

}