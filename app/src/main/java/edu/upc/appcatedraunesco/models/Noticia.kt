package edu.upc.appcatedraunesco.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable

class Noticia @RequiresApi(Build.VERSION_CODES.O) constructor(
    var titulo: String,
    var descripcion: String,
    var imagen: String,
    var urlPagina: String,
) : Serializable