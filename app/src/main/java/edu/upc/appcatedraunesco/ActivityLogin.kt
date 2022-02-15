package edu.upc.appcatedraunesco

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import edu.upc.appcatedraunesco.databinding.ActivityLoginBinding

class ActivityLogin : AppCompatActivity() {

    private lateinit var bindingLogin: ActivityLoginBinding

    private var remember: Boolean = false

    //Referencias a la base de datos
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var referencia: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var userId: String

    private val GOOGLE_SIGN_IN = 100
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingLogin = DataBindingUtil.setContentView(this, R.layout.activity_login)

        setProgressBar(bindingLogin.progressBarLogin)
        hideProgressBar()

        auth = FirebaseAuth.getInstance()
        referencia = FirebaseDatabase.getInstance().reference.child("USUARI/CLIENT")
        userId = auth.currentUser?.uid.toString()

        bindingLogin.txtRegistre.setOnClickListener {
            val intent = Intent(this, ActivityRegistre1::class.java)
            startActivity(intent)
        }

        bindingLogin.btnIdentificat.setOnClickListener {
            login(it)
        }

        bindingLogin.btnGoogle.setOnClickListener {
            val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

            googleSignInClient = GoogleSignIn.getClient(this, gso)

            signIn()
        }

        bindingLogin.claveOlvidada.setOnClickListener {
            val dialog = RecuperarPasswordFragment()
            dialog.show(supportFragmentManager, "recuperarPassword")
        }

        // Recordar cuenta
        bindingLogin.cbRecordam.setOnClickListener {
            remember = !remember
        }

        fullScreenMode()
    }

    override fun onStart() {
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)

        super.onStart()
    }

    private fun login(view: View) {
        val email = bindingLogin.tfMail.text.toString()
        val clave = bindingLogin.tfContrasena.text.toString()
        showProgressBar()

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(clave)) {
            auth.signInWithEmailAndPassword(email, clave)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {

                        view.snack("Inici de sessió correcte")

                        val prefs = getSharedPreferences(
                            getString(R.string.prefs_file),
                            Context.MODE_PRIVATE
                        ).edit()
                        prefs.putString("currentUid", auth.currentUser?.uid!!)
                        prefs.putBoolean("rememberOption", remember)
                        prefs.apply()

                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        view.snack("Inici de sessió incorrecte")
                        hideProgressBar()
                    }

                }
        } else {
            view.snack("Has d'introduir els camps demanats")
            hideProgressBar()
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    updateUI(null)
                    showAlert()
                }
            }
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
        showProgressBar()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
                startActivity(Intent(this, MainActivity::class.java))
            } catch (e: ApiException) {
                showAlert()
            }


        }
    }

    private fun updateUI(user: FirebaseUser?) {

    }

    private fun showAlert() {

        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error inici de sessió")
        builder.setMessage("S'ha produït un error en iniciar sessió")
        builder.setPositiveButton(getString(R.string.infoAcceptar), null)
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private fun fullScreenMode() {
        // Amagar actionBar i mode fullScreen
        supportActionBar?.hide()
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }
}