package edu.upc.appcatedraunesco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import edu.upc.appcatedraunesco.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bindingMainActivity: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMainActivity = DataBindingUtil.setContentView(this, R.layout.activity_main)

        bindingMainActivity.btnProfile.setOnClickListener{
            val intent = Intent(this, ActivityLogin::class.java)
            startActivity(intent)
        }

        fullScreenMode()

        bindingMainActivity.btnHome.setOnClickListener {
            findNavController(R.id.fragmentContainer).navigate(R.id.action_to_homeFragment)
        }

        bindingMainActivity.btnMapa.setOnClickListener {
        }

        bindingMainActivity.btnNoticias.setOnClickListener {
            findNavController(R.id.fragmentContainer).navigate(R.id.action_to_noticiasFragment)
        }

        bindingMainActivity.btnProductos.setOnClickListener {
            findNavController(R.id.fragmentContainer).navigate(R.id.action_to_productosFragment)
        }

        bindingMainActivity.btnInfo.setOnClickListener {
            findNavController(R.id.fragmentContainer).navigate(R.id.action_to_infoFragment)
        }

        bindingMainActivity.btnMapa.setOnClickListener {
            findNavController(R.id.fragmentContainer).navigate(R.id.action_to_mapsFragment)
        }

    }

    private fun  fullScreenMode(){
        // Hide actionBar and fullScreen mode
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    private fun changeBottomMenuIcon( option: Int ){

        this.bindingMainActivity.btnHome.setImageResource(R.drawable.ic_home_dark)
        this.bindingMainActivity.btnInfo.setImageResource(R.drawable.ic_info_dark)
        this.bindingMainActivity.btnProductos.setImageResource(R.drawable.ic_interests_dark)
        this.bindingMainActivity.btnNoticias.setImageResource(R.drawable.ic_notes_dark)
        this.bindingMainActivity.btnMapa.setImageResource(R.drawable.ic_map_dark)


        /*when(option){
            0 -> this.bindingMainActivity.btnHome.setImageResource(R.drawable.ic_home_yellow)
            1 -> this.bindingMainActivity.btnInfo.setImageResource(R.drawable.ic_trends_yellow)
            2 -> this.bindingMainActivity.btnProductos.setImageResource(R.drawable.ic_add_yellow)
            3 -> this.bindingMainActivity.btnNoticias.setImageResource(R.drawable.ic_social_yellow)
            4 -> this.bindingMainActivity.btnMapa.setImageResource(R.drawable.ic_notify_yellow)
        }*/
    }

    fun setBottomMenuNavigation( option: Boolean ){
        when(option) {
            true -> this.bindingMainActivity.bottomMenuNavigation.visibility = View.VISIBLE
            false -> this.bindingMainActivity.bottomMenuNavigation.visibility = View.INVISIBLE
        }
    }


}