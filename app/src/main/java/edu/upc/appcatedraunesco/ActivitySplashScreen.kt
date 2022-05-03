package edu.upc.appcatedraunesco

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import java.util.*

class ActivitySplashScreen : AppCompatActivity() {

    private val SPLASH_TIMER: Long = 5000

    // Variables
    private lateinit var backgroundImage: ImageView
    private lateinit var poweredByLine: TextView

    // Animations
    private lateinit var sideAnim: Animation
    private lateinit var bottomAnim: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fullScreenMode()
        setContentView(R.layout.activity_splash_screen)


        // Hooks
        backgroundImage = findViewById(R.id.background_image)
        poweredByLine = findViewById(R.id.powered_by_line)

        // Animations
        sideAnim = AnimationUtils.loadAnimation(this, R.anim.side_anim)
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_anim)

        // set Animations on elements
        backgroundImage.animation = sideAnim
        poweredByLine.animation = bottomAnim

        Handler().postDelayed({
            var intent: Intent
            intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
            finish()

        }, SPLASH_TIMER)
    }

    private fun fullScreenMode() {
        // Hide actionBar and fullScreen mode
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}