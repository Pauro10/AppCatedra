package edu.upc.appcatedraunesco

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import edu.upc.appcatedraunesco.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {

    private lateinit var bindingFragmentLogin: FragmentLoginBinding

    private var remember: Boolean = false

    //Referencias a la base de datos
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser
    private lateinit var referencia: DatabaseReference
    private lateinit var database: FirebaseDatabase
    private lateinit var userId: String
    private val GOOGLE_SIGN_IN = 100
    private lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        this.bindingFragmentLogin =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)

        setProgressBar(bindingFragmentLogin.progressBarLogin)
        hideProgressBar()

        auth = FirebaseAuth.getInstance()
        referencia = FirebaseDatabase.getInstance().reference.child("USUARIO/CLIENTE")
        userId = auth.currentUser?.uid.toString()


        bindingFragmentLogin.btnIdentificarse.setOnClickListener {
            login(it)
        }


        bindingFragmentLogin.claveOlvidada.setOnClickListener {
            findNavController().navigate(R.id.action_to_recuperarPasswordFragment)
        }

        // Recordar cuenta
        bindingFragmentLogin.cbRecuerdame.setOnClickListener {
            remember = !remember
        }

        bindingFragmentLogin.btnAtras.setOnClickListener {
            getFragmentManager()?.popBackStack()
        }

        return this.bindingFragmentLogin.root
    }

    override fun onStart() {
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)

        super.onStart()
    }

    private fun login(view: View) {
        val email = bindingFragmentLogin.tfMail.text.toString()
        val clave = bindingFragmentLogin.tfContrasena.text.toString()
        showProgressBar()

        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(clave)) {
            auth.signInWithEmailAndPassword(email, clave)
                .addOnCompleteListener { task ->

                    if (task.isSuccessful) {

                        view.snack(getString(R.string.login_sucs))

                        val prefs = activity?.getSharedPreferences(
                            getString(R.string.prefs_file),
                            Context.MODE_PRIVATE
                        )?.edit()
                        prefs?.putString("currentUid", auth.currentUser?.uid!!)
                        prefs?.putBoolean("rememberOption", remember)
                        prefs?.apply()

                        findNavController().navigate(R.id.action_to_mapsFragment)
                    } else {
                        view.snack(getString(R.string.login_sucs))
                        hideProgressBar()
                    }

                }
        } else {
            view.snack(getString(R.string.error_campos))
            hideProgressBar()
        }
    }

    private fun updateUI(user: FirebaseUser?) {

    }

}