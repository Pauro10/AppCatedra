package edu.upc.appcatedraunesco

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import edu.upc.appcatedraunesco.databinding.FragmentMapsBinding
import edu.upc.appcatedraunesco.models.Ecoinf

class MapsFragment : Fragment(), OnMapReadyCallback {

    private lateinit var bindingFragmentMaps: FragmentMapsBinding
    private lateinit var map: GoogleMap
    private val REQUEST_LOCATION_PERMISSION = 1
    private lateinit var user: FirebaseUser
    private lateinit var dbReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onMapReady(googleMap: GoogleMap) {

        map = googleMap

        val catedraUnesco = LatLng(41.56421636208062, 2.0225820193470034)
        val zoomLevel = 15f
        googleMap.addMarker(
            MarkerOptions().position(catedraUnesco).title("Marcador en Catedra UNESCO")
        )
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(catedraUnesco, zoomLevel))
        val UiSettings = map.getUiSettings()
        UiSettings.setZoomControlsEnabled(true)

        enableMyLocation()

        dbReference.orderByKey().addListenerForSingleValueEvent(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { ecoinf ->
                    val nombre = ecoinf.child("nombre").value.toString()
                    val latitud = ecoinf.child("latitud").value.toString()
                    val longitud = ecoinf.child("longitud").value.toString()

                    val auxLatLang = LatLng(latitud.toDouble(), longitud.toDouble())
                    googleMap.addMarker(MarkerOptions().position(auxLatLang).title(nombre))
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        this.bindingFragmentMaps =
            DataBindingUtil.inflate(inflater, R.layout.fragment_maps, container, false)

        dbReference = FirebaseDatabase.getInstance().reference.child("Ecoinfraestructura")

        bindingFragmentMaps.btnLista.setOnClickListener {
            findNavController().navigate(R.id.action_to_ecoinfFragment)
            this.changeBottomMenuIcon(1)
        }


        //return inflater.inflate(R.layout.fragment_maps, container, false)
        return this.bindingFragmentMaps.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    private fun enableMyLocation() {
        if (isPermissionGranted()) {
            if (ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            map.isMyLocationEnabled = true
        } else {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf<String>(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_LOCATION_PERMISSION
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.contains(PackageManager.PERMISSION_GRANTED)) {
                enableMyLocation()
            }
        }
    }

    private fun changeBottomMenuIcon(option: Int) {

        this.bindingFragmentMaps.btnMapa.setImageResource(R.drawable.ic_map2_light)
        this.bindingFragmentMaps.btnLista.setImageResource(R.drawable.ic_list_bright)


        when (option) {
            0 -> this.bindingFragmentMaps.btnMapa.setImageResource(R.drawable.ic_map2_dark)
            1 -> this.bindingFragmentMaps.btnLista.setImageResource(R.drawable.ic_list_dark)
        }
    }

    private fun getDatosEcoinf(): LiveData<MutableList<Ecoinf>> {
        val mutableData = MutableLiveData<MutableList<Ecoinf>>()
        val listData = mutableListOf<Ecoinf>()
        dbReference.orderByKey().addListenerForSingleValueEvent(object : ValueEventListener {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { ecoinf ->
                    val nombre = ecoinf.child("nombre").value.toString()
                    val numeroTelefono = ecoinf.child("numeroTelefono").value.toString()
                    val imagen = ecoinf.child("imagen").value.toString()
                    val latitud = ecoinf.child("latitud").value.toString()
                    val longitud = ecoinf.child("longitud").value.toString()
                    val direccion = ecoinf.child("direccion").value.toString()
                    val urlPagina = ecoinf.child("urlPagina").value.toString()

                    val datosEcoinf = Ecoinf(
                        nombre,
                        numeroTelefono,
                        imagen,
                        latitud,
                        longitud,
                        direccion,
                        urlPagina
                    )

                    listData.add(datosEcoinf)
                    mutableData.value = listData
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        return mutableData
    }

}