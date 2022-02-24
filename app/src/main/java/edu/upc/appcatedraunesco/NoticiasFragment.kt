package edu.upc.appcatedraunesco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import edu.upc.appcatedraunesco.databinding.FragmentNoticiasBinding

class NoticiasFragment : Fragment() {

    private lateinit var bindingFragmentNoticias: FragmentNoticiasBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this.bindingFragmentNoticias = DataBindingUtil.inflate(inflater, R.layout.fragment_noticias, container, false)
        return this.bindingFragmentNoticias.root
    }
}