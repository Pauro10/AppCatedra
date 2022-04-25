package edu.upc.appcatedraunesco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import edu.upc.appcatedraunesco.databinding.FragmentEcoinfInfoBinding

class EcoinfFragmentInfo : Fragment() {

    private lateinit var bindingFragmentEcoinfInfo: FragmentEcoinfInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.bindingFragmentEcoinfInfo = DataBindingUtil.inflate(inflater, R.layout.fragment_ecoinf_info, container, false)

        return this.bindingFragmentEcoinfInfo.root
    }

}