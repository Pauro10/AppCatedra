package edu.upc.appcatedraunesco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import edu.upc.appcatedraunesco.databinding.FragmentProductosBinding

class ProductosFragment : Fragment() {

    private lateinit var bindingFragmentProductos: FragmentProductosBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this.bindingFragmentProductos = DataBindingUtil.inflate(inflater, R.layout.fragment_productos, container, false)
        return this.bindingFragmentProductos.root
    }

}