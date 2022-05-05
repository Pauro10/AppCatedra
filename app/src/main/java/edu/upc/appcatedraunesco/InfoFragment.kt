package edu.upc.appcatedraunesco

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import edu.upc.appcatedraunesco.databinding.FragmentInfoBinding


class InfoFragment : Fragment() {

    private lateinit var bindingFragmentInfo: FragmentInfoBinding

    private val colab2: String = "www.metropol.gov.co"
    private val colab3: String = "www.catedraunesco.es"
    private val colab4: String = "www.medellin.gov.co"
    private val colab5: String = "rec.net"
    private val colab6: String = "www.medellin.gov.co"
    private val colab7: String = "www.itm.edu.co"
    private val colab8: String = "www.tdea.edu.co"
    private val colab9: String = "catalunya-america.org"
    private val colab10: String = "www.corantioquia.gov.co"
    private val colab11: String = "www.epm.com.co"
    private val colab12: String = "www.upc.edu/es/la-upc/la-institucion/catedres-unesco"
    private val colab13: String = "facebook.com/cojardicom"


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this.bindingFragmentInfo =
            DataBindingUtil.inflate(inflater, R.layout.fragment_info, container, false)

        bindingFragmentInfo.colab2.setOnClickListener {
            comprovarEnlace(colab2)
        }

        bindingFragmentInfo.colab3.setOnClickListener {
            comprovarEnlace(colab3)
        }

        bindingFragmentInfo.colab4.setOnClickListener {
            comprovarEnlace(colab4)
        }

        bindingFragmentInfo.colab5.setOnClickListener {
            comprovarEnlace(colab5)
        }

        bindingFragmentInfo.colab6.setOnClickListener {
            comprovarEnlace(colab6)
        }

        bindingFragmentInfo.colab7.setOnClickListener {
            comprovarEnlace(colab7)
        }

        bindingFragmentInfo.colab8.setOnClickListener {
            comprovarEnlace(colab8)
        }

        bindingFragmentInfo.colab9.setOnClickListener {
            comprovarEnlace(colab9)
        }

        bindingFragmentInfo.colab10.setOnClickListener {
            comprovarEnlace(colab10)
        }

        bindingFragmentInfo.colab11.setOnClickListener {
            comprovarEnlace(colab11)
        }

        bindingFragmentInfo.colab12.setOnClickListener {
            comprovarEnlace(colab12)
        }

        bindingFragmentInfo.colab13.setOnClickListener {
            comprovarEnlace(colab13)
        }

        return this.bindingFragmentInfo.root
    }

    private fun comprovarEnlace(enlace: String) {
        var webpage = Uri.parse(enlace)

        if (!enlace.startsWith("http://") && !enlace.startsWith("https://")) {
            webpage = Uri.parse("http://$enlace")
        }

        val intent = Intent(Intent.ACTION_VIEW, webpage)
            startActivity(intent)
    }
}