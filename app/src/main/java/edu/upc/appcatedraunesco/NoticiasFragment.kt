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
import edu.upc.appcatedraunesco.adapters.NoticiasAdapter
import edu.upc.appcatedraunesco.databinding.FragmentNoticiasBinding
import edu.upc.appcatedraunesco.models.Noticia

class NoticiasFragment : Fragment() {

    private lateinit var bindingFragmentNoticias: FragmentNoticiasBinding
    private lateinit var noticiasAdapter: NoticiasAdapter
    private lateinit var user: FirebaseUser
    private lateinit var dbReference: DatabaseReference
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this.bindingFragmentNoticias =
            DataBindingUtil.inflate(inflater, R.layout.fragment_noticias, container, false)

        dbReference = FirebaseDatabase.getInstance().reference.child("Noticia")

        initNoticiasRecycler()

        return this.bindingFragmentNoticias.root
    }

    private fun initNoticiasRecycler() {
        this.bindingFragmentNoticias.rvNoticias.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        this.noticiasAdapter = NoticiasAdapter(requireContext())
        this.bindingFragmentNoticias.rvNoticias.adapter = this.noticiasAdapter

        return observeData()
    }

    fun fetchGestionNoticias(): LiveData<MutableList<Noticia>> {
        val mutableData = MutableLiveData<MutableList<Noticia>>()
        getDatosNoticia().observeForever { datosList ->
            mutableData.value = datosList
        }

        return mutableData
    }

    private fun observeData() {
        fetchGestionNoticias().observe(viewLifecycleOwner, Observer {
            noticiasAdapter.setListData(it)
            noticiasAdapter.notifyDataSetChanged()
        })
    }

    private fun getDatosNoticia(): LiveData<MutableList<Noticia>> {
        val mutableData = MutableLiveData<MutableList<Noticia>>()
        val listData = mutableListOf<Noticia>()
        dbReference.orderByKey().limitToFirst(1)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach { noticia ->
                        val titulo =noticia.child("titulo").value.toString()
                        val descripcion =noticia.child("descripcion").value.toString()
                        val imagen =noticia.child("imagen").value.toString()

                        val datosNoticia = Noticia(
                            titulo,
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