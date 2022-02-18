package edu.upc.appcatedraunesco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import edu.upc.appcatedraunesco.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private lateinit var bindingFragmetnHome: FragmentHomeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this.bindingFragmetnHome = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return this.bindingFragmetnHome.root
    }

}