package edu.upc.appcatedraunesco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.squareup.picasso.Picasso
import edu.upc.appcatedraunesco.databinding.FragmentProductosInfoBinding

class ProductosFragmentInfo : Fragment() {

    private lateinit var bindingFragmentProductosInfo: FragmentProductosInfoBinding

    private val args by navArgs<ProductosFragmentInfoArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.bindingFragmentProductosInfo = DataBindingUtil.inflate(inflater, R.layout.fragment_productos_info, container, false)

        this.bindingFragmentProductosInfo.btnAtras.setOnClickListener {
            getFragmentManager()?.popBackStack()
        }

        bindingFragmentProductosInfo.tvNombre.setText(args.currentProducto.nombre)
        bindingFragmentProductosInfo.tvDescripcion.setText(args.currentProducto.descripcion)
        Picasso.get().load(args.currentProducto.imagen).into(bindingFragmentProductosInfo.imagenProducto)

        return this.bindingFragmentProductosInfo.root
    }

}