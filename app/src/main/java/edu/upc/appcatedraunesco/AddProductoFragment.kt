package edu.upc.appcatedraunesco

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import edu.upc.appcatedraunesco.databinding.FragmentAddProductoBinding
import edu.upc.appcatedraunesco.models.Noticia
import edu.upc.appcatedraunesco.models.Producto


class AddProductoFragment : Fragment() {

    private lateinit var bindingFragmentAddProducto: FragmentAddProductoBinding

    private lateinit var auth: FirebaseAuth

    private lateinit var database: FirebaseDatabase
    private lateinit var reference: DatabaseReference
    private val GALLERY_PICKUP_CODE: Int = 123
    private var imageUri: Uri? = null
    private var storageRef: StorageReference? = null
    private var glbUrl = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        this.bindingFragmentAddProducto =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_producto, container, false)

        // Initialize Firebase Auth
        this.auth = FirebaseAuth.getInstance()

        // Instanciate database
        database = FirebaseDatabase.getInstance()
        // Reference working document
        reference = database.getReference("Producto")
        storageRef = FirebaseStorage.getInstance().reference.child("Productos")

        bindingFragmentAddProducto.ivAnadir.setOnClickListener {
            this.crearProducto(it)
            findNavController().navigate(R.id.action_to_productosFragment)
        }

        bindingFragmentAddProducto.btnImagen.setOnClickListener {
            pickImage()
        }

        this.bindingFragmentAddProducto.btnAtras.setOnClickListener {
            getFragmentManager()?.popBackStack()
        }

        return this.bindingFragmentAddProducto.root
    }

    private fun crearProducto(view: View) {

        val nombre = this.bindingFragmentAddProducto.tfNombre.text.toString().trim()
        val descripcion = this.bindingFragmentAddProducto.tfDescripcion.text.toString().trim()
        val comercializacion = this.bindingFragmentAddProducto.tfComerc.text.toString().trim()

        if (glbUrl === "") {
            glbUrl =
                "https://firebasestorage.googleapis.com/v0/b/appcatedraunesco.appspot.com/o/Noticias%2Faaaaaa.png?alt=media&token=ed648828-3f7c-4063-aaa1-19941152c20a"
        }
        val producto = Producto(
            nombre = nombre,
            descripcion = descripcion,
            comercializacion = comercializacion,
            imagen = this.glbUrl
        )
        val id = this.reference.push().key

        this.reference.child(id!!).setValue(producto)

        // Retroalimentació
        view.snack("se ha publicado con exito!")
    }

    private fun pickImage() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(intent, GALLERY_PICKUP_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Change Image Peview to Choosed Image
        if (requestCode == GALLERY_PICKUP_CODE && resultCode == Activity.RESULT_OK && data!!.data != null) {
            imageUri = data.data
            uploadedImageToDatabase()
        }

    }

    private fun uploadedImageToDatabase() {
        val progressBar = ProgressDialog(context)
        progressBar.setMessage("La imagen se esta subiendo espera")
        progressBar.show()

        if (imageUri != null) {
            var fileRef = storageRef!!.child(System.currentTimeMillis().toString() + ".jpg")

            var uploadTask: StorageTask<*>
            uploadTask = fileRef.putFile(imageUri!!)

            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation fileRef.downloadUrl
            }).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    imageUri = task.result
                    glbUrl = imageUri.toString()

                    progressBar.dismiss()
                }
            }
        }
    }

}