package edu.upc.appcatedraunesco

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import edu.upc.appcatedraunesco.databinding.FragmentInformarProblemaBinding

class InformarProblemaFragment : DialogFragment() {

    private lateinit var bindingFragmentInformarProblema: FragmentInformarProblemaBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentInformarProblemaBinding.inflate(inflater, container, false)
        bindingFragmentInformarProblema = binding

        return binding.root
    }

}
