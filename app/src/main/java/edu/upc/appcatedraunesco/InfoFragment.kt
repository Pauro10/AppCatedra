package edu.upc.appcatedraunesco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import edu.upc.appcatedraunesco.databinding.FragmentInfoBinding


class InfoFragment : Fragment() {

    private lateinit var bindingFragmentInfo: FragmentInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this.bindingFragmentInfo =
            DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false)
        return this.bindingFragmentInfo.root
    }
}