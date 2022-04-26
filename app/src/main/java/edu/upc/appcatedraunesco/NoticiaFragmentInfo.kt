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
import com.squareup.picasso.Picasso
import edu.upc.appcatedraunesco.databinding.FragmentNoticiaInfoBinding


class NoticiaFragmentInfo : Fragment() {

    private lateinit var bindingFragmentNoticiaInfo: FragmentNoticiaInfoBinding

    private val args by navArgs<NoticiaFragmentInfoArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.bindingFragmentNoticiaInfo =
            DataBindingUtil.inflate(inflater, R.layout.fragment_noticia_info, container, false)

        this.bindingFragmentNoticiaInfo.btnAtras.setOnClickListener {
            getFragmentManager()?.popBackStack()
        }

        bindingFragmentNoticiaInfo.tvTitulo.setText(args.currentNoticia.titulo)
        bindingFragmentNoticiaInfo.tvDescripcion.setText(args.currentNoticia.descripcion)
        Picasso.get().load(args.currentNoticia.imagen)
            .into(bindingFragmentNoticiaInfo.imagenNoticia)

        this.bindingFragmentNoticiaInfo.tvMasInfo.setOnClickListener {
            var auxurl = ""
            if (!args.currentNoticia.urlPagina.startsWith("http://") && !args.currentNoticia.urlPagina.startsWith(
                    "https://"
                )
            )
                auxurl = "http://" + args.currentNoticia.urlPagina;
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(auxurl))
            startActivity(browserIntent)
        }

        return this.bindingFragmentNoticiaInfo.root
    }

}