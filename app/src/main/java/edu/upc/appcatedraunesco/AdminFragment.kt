package edu.upc.appcatedraunesco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import edu.upc.appcatedraunesco.databinding.FragmentAdminBinding

class AdminFragment : Fragment() {

    private lateinit var bindingFragmentAdmin: FragmentAdminBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this.bindingFragmentAdmin = DataBindingUtil.inflate(inflater, R.layout.fragment_admin, container, false)
        return this.bindingFragmentAdmin.root
    }

}