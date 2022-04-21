package edu.upc.appcatedraunesco

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import edu.upc.appcatedraunesco.adapters.EcoinfAdapter
import edu.upc.appcatedraunesco.adapters.NoticiasAdapter
import edu.upc.appcatedraunesco.databinding.FragmentEcoinfBinding
import edu.upc.appcatedraunesco.models.Ecoinf
import edu.upc.appcatedraunesco.models.Noticia

class EcoinfFragment : Fragment() {

    private lateinit var bindingFragmentEcoinf: FragmentEcoinfBinding
    private lateinit var ecoinfAdapter: EcoinfAdapter
    private lateinit var user: FirebaseUser
    private lateinit var dbReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this.bindingFragmentEcoinf = DataBindingUtil.inflate(inflater, R.layout.fragment_ecoinf, container, false)

        dbReference = FirebaseDatabase.getInstance().reference.child("Ecoinfraestructura")

        initNoticiasRecycler()

        bindingFragmentEcoinf.btnMapa.setOnClickListener {
            findNavController().navigate(R.id.action_to_mapsFragment)
            this.changeBottomMenuIcon(0)
        }

        return this.bindingFragmentEcoinf.root
    }

    private fun initNoticiasRecycler() {
        this.bindingFragmentEcoinf.rvEcoinf.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        this.ecoinfAdapter = EcoinfAdapter(requireContext())
        this.bindingFragmentEcoinf.rvEcoinf.adapter = this.ecoinfAdapter

        return observeData()
    }

    fun fetchGestionNoticias(): LiveData<MutableList<Ecoinf>> {
        val mutableData = MutableLiveData<MutableList<Ecoinf>>()
        getDatosEcoinf().observeForever { datosList ->
            mutableData.value = datosList
        }
        return mutableData
    }

    private fun observeData() {
        fetchGestionNoticias().observe(viewLifecycleOwner, Observer {
            ecoinfAdapter.setListData(it)
            ecoinfAdapter.notifyDataSetChanged()
        })
    }

    private fun getDatosEcoinf(): LiveData<MutableList<Ecoinf>> {
        val mutableData = MutableLiveData<MutableList<Ecoinf>>()
        val listData = mutableListOf<Ecoinf>()
        dbReference.orderByKey().limitToFirst(1)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach { ecoinf ->
                        val nombre =ecoinf.child("nombre").value.toString()
                        val numeroTelefono =ecoinf.child("numeroTelefono").value.toString()
                        val imagen =ecoinf.child("imagen").value.toString()
                        val latitud =ecoinf.child("latitud").value.toString()
                        val longitud =ecoinf.child("longitud").value.toString()
                        val direccion =ecoinf.child("direccion").value.toString()

                        val datosEcoinf = Ecoinf(
                            nombre,
                            numeroTelefono,
                            imagen,
                            latitud,
                            longitud,
                            direccion
                        )

                        listData.add(datosEcoinf)
                        mutableData.value =listData
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        return mutableData
    }

    private fun changeBottomMenuIcon( option: Int ){

        this.bindingFragmentEcoinf.btnMapa.setImageResource(R.drawable.ic_map2_light)
        this.bindingFragmentEcoinf.btnLista.setImageResource(R.drawable.ic_list_bright)


        when(option){
            0 -> this.bindingFragmentEcoinf.btnMapa.setImageResource(R.drawable.ic_map2_dark)
            1 -> this.bindingFragmentEcoinf.btnLista.setImageResource(R.drawable.ic_list_dark)
        }
    }

}