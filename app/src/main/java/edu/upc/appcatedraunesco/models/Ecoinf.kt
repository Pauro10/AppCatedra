package edu.upc.appcatedraunesco.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable

class Ecoinf @RequiresApi(Build.VERSION_CODES.O) constructor(
    var nombre: String,
    var numeroTelefono: String,
    var imagen: String,
    var cordenadas: String,
) : Serializable