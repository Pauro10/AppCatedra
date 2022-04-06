package edu.upc.appcatedraunesco.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable

class Producto @RequiresApi(Build.VERSION_CODES.O) constructor(
    var nombre: String,
    var descripcion: String,
    var imagen: String,
) : Serializable