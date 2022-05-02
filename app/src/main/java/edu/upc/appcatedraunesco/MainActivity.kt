package edu.upc.appcatedraunesco

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import edu.upc.appcatedraunesco.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var bindingMainActivity: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingMainActivity = DataBindingUtil.setContentView(this, R.layout.activity_main)

        fullScreenMode()

        if (FirebaseAuth.getInstance().currentUser == null) {
            bindingMainActivity.btnInfo.visibility = View.VISIBLE
            bindingMainActivity.btnAdmin.visibility = View.GONE
        } else {
            bindingMainActivity.btnInfo.visibility = View.GONE
            bindingMainActivity.btnAdmin.visibility = View.VISIBLE
        }

        // Paint Maps Icon ( Default onStart )
        this.changeBottomMenuIcon(0)

        bindingMainActivity.btnSettings.setOnClickListener {
            findNavController(R.id.fragmentContainer).navigate(R.id.action_to_opcionesFragment)
            this.changeBottomMenuIcon(4)
        }

        bindingMainActivity.btnNoticias.setOnClickListener {
            findNavController(R.id.fragmentContainer).navigate(R.id.action_to_noticiasFragment)
            this.changeBottomMenuIcon(3)
        }

        bindingMainActivity.btnProductos.setOnClickListener {
            findNavController(R.id.fragmentContainer).navigate(R.id.action_to_productosFragment)
            this.changeBottomMenuIcon(2)
        }

        bindingMainActivity.btnInfo.setOnClickListener {
            findNavController(R.id.fragmentContainer).navigate(R.id.action_to_infoFragment)
            this.changeBottomMenuIcon(1)
        }

        bindingMainActivity.btnMapa.setOnClickListener {
            findNavController(R.id.fragmentContainer).navigate(R.id.action_to_mapsFragment)
            this.changeBottomMenuIcon(0)
        }

        bindingMainActivity.btnAdmin.setOnClickListener {
            findNavController(R.id.fragmentContainer).navigate(R.id.action_to_adminFragment)
            this.changeBottomMenuIcon(5)
        }

    }

    private fun changeBottomMenuIcon(option: Int) {

        this.bindingMainActivity.btnSettings.setImageResource(R.drawable.ic_settings_bright)
        this.bindingMainActivity.btnInfo.setImageResource(R.drawable.ic_info_bright)
        this.bindingMainActivity.btnProductos.setImageResource(R.drawable.ic_interests_bright)
        this.bindingMainActivity.btnNoticias.setImageResource(R.drawable.ic_notes_bright)
        this.bindingMainActivity.btnMapa.setImageResource(R.drawable.ic_map_bright)
        this.bindingMainActivity.btnAdmin.setImageResource(R.drawable.ic_admin_panel_settings_bright)


        when (option) {
            5 -> this.bindingMainActivity.btnAdmin.setImageResource(R.drawable.ic_admin_panel_settings_dark)
            4 -> this.bindingMainActivity.btnSettings.setImageResource(R.drawable.ic_settings)
            1 -> this.bindingMainActivity.btnInfo.setImageResource(R.drawable.ic_info_dark)
            2 -> this.bindingMainActivity.btnProductos.setImageResource(R.drawable.ic_interests_dark)
            3 -> this.bindingMainActivity.btnNoticias.setImageResource(R.drawable.ic_notes_dark)
            0 -> this.bindingMainActivity.btnMapa.setImageResource(R.drawable.ic_map_dark)
        }
    }

    /*fun setBottomMenuNavigation( option: Boolean ){
        when(option) {
            true -> this.bindingMainActivity.bottomMenuNavigation.visibility = View.VISIBLE
            false -> this.bindingMainActivity.bottomMenuNavigation.visibility = View.INVISIBLE
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val action = NavGraphDirections.actionToHomeFragment()
        setBottomMenuNavigation(true)
        changeBottomMenuIcon(0)
        findNavController(R.id.fragmentContainer).navigate(action)
    }*/

    private fun fullScreenMode() {
        // Hide actionBar and fullScreen mode
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }


}