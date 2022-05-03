package edu.upc.appcatedraunesco.models

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable

class Problema @RequiresApi(Build.VERSION_CODES.O) constructor(
    var descripcion: String
) : Serializable