package edu.upc.appcatedraunesco.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable

class Usuario @RequiresApi(Build.VERSION_CODES.O) constructor(
    var uidUsuario: String,
    var NomComplert: String,
    var correu: String,
    var gender: String,
    var dataNaixement: String,
    var nif: String,
    var numeroTelefon: String,
) : Serializable