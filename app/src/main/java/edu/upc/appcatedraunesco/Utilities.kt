package edu.upc.appcatedraunesco

import android.view.View
import android.widget.ProgressBar
import com.google.android.material.snackbar.Snackbar
import java.util.regex.Pattern

fun View.snack(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG).show()
}

var email_Param =
    Pattern.compile("^[_a-z0-9]+(.[_a-z0-9]+)*@[a-z0-9]+(.[a-z0-9]+)*(.[a-z]{2,4})\$")

fun isValidEmail(email: CharSequence?): Boolean {
    return if (email == null) false else email_Param.matcher(email).matches()
}

//Funciones de la barra de progreso
private var progressBar: ProgressBar? = null

fun setProgressBar(bar: ProgressBar) {
    progressBar = bar
}

fun showProgressBar() {
    progressBar!!.visibility = View.VISIBLE
}

fun hideProgressBar() {
    progressBar!!.visibility = View.INVISIBLE
}