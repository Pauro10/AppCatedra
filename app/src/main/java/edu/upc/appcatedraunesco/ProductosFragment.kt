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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import edu.upc.appcatedraunesco.adapters.ProductosAdapter
import edu.upc.appcatedraunesco.databinding.FragmentProductosBinding
import edu.upc.appcatedraunesco.models.Producto

class ProductosFragment : Fragment() {

    private lateinit var bindingFragmentProductos: FragmentProductosBinding
    private lateinit var productosAdapter: ProductosAdapter
    private lateinit var user: FirebaseUser
    private lateinit var dbReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this.bindingFragmentProductos = DataBindingUtil.inflate(inflater, R.layout.fragment_productos, container, false)

        dbReference = FirebaseDatabase.getInstance().reference.child("Producto")

        initProductosRecycler()

        return this.bindingFragmentProductos.root
    }

    private fun initProductosRecycler() {
        this.bindingFragmentProductos.rvProductos.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        this.productosAdapter = ProductosAdapter(requireContext())
        this.bindingFragmentProductos.rvProductos.adapter = this.productosAdapter

        return observeData()
    }

    fun fetchGestionProductos(): LiveData<MutableList<Producto>> {
        val mutableData = MutableLiveData<MutableList<Producto>>()
        getDatosProducto().observeForever { datosList ->
            mutableData.value = datosList
        }
        return mutableData
    }


    private fun observeData() {
        fetchGestionProductos().observe(viewLifecycleOwner, Observer {
            productosAdapter.setListData(it)
            productosAdapter.notifyDataSetChanged()
        })
    }

    private fun getDatosProducto(): LiveData<MutableList<Producto>> {
        val mutableData = MutableLiveData<MutableList<Producto>>()
        val listData = mutableListOf<Producto>()
        dbReference.orderByKey().limitToFirst(1)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach { producto ->
                        val nombre =producto.child("nombre").value.toString()
                        val descripcion =producto.child("descripcion").value.toString()
                        val comercializacion =producto.child("comercializacion").value.toString()
                        val imagen =producto.child("imagen").value.toString()

                        val datosNoticia = Producto(
                            nombre,
                            descripcion,
                            imagen
                        )

                        listData.add(datosNoticia)
                        mutableData.value =listData
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        return mutableData
    }

}