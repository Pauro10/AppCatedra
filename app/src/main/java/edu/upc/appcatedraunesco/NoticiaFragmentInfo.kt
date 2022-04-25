package edu.upc.appcatedraunesco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.squareup.picasso.Picasso
import edu.upc.appcatedraunesco.databinding.FragmentNoticiaInfoBinding

class NoticiaFragmentInfo : Fragment() {

    private lateinit var bindingFragmentNoticiaInfo: FragmentNoticiaInfoBinding

    private val args by navArgs<NoticiaFragmentInfoArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.bindingFragmentNoticiaInfo = DataBindingUtil.inflate(inflater, R.layout.fragment_noticia_info, container, false)

        this.bindingFragmentNoticiaInfo.btnAtras.setOnClickListener {
            getFragmentManager()?.popBackStack()
        }

        bindingFragmentNoticiaInfo.tvTitulo.setText(args.currentNoticia.titulo)
        bindingFragmentNoticiaInfo.tvDescripcion.setText(args.currentNoticia.descripcion)
        Picasso.get().load(args.currentNoticia.imagen).into(bindingFragmentNoticiaInfo.imagenNoticia)

        return this.bindingFragmentNoticiaInfo.root
    }

}