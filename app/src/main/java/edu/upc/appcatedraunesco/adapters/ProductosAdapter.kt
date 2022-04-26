package edu.upc.appcatedraunesco.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import edu.upc.appcatedraunesco.ProductosFragmentDirections
import edu.upc.appcatedraunesco.R
import edu.upc.appcatedraunesco.models.Producto

class ProductosAdapter(private val context: Context) :
    RecyclerView.Adapter<ProductosAdapter.ViewHolder>() {

    private var dataListProducto = mutableListOf<Producto>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductosAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.diseno_noticias, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItemProducto = dataListProducto[position]
        holder.itemView.findViewById<TextView>(R.id.tvTitulo).text = currentItemProducto.nombre
        holder.itemView.findViewById<TextView>(R.id.tvDescripcion).text =
            currentItemProducto.descripcion
        Picasso.get().load(currentItemProducto.imagen)
            .into(holder.itemView.findViewById<ImageView>(R.id.ivImagen))

        //Funcion para ir a la noticia IMPORTANTE!!!!!
        holder.itemView.findViewById<CardView>(R.id.cardViewProductos).setOnClickListener {
            val action =
                ProductosFragmentDirections.actionToProductosFragmentInfo(currentItemProducto)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return dataListProducto.size
    }

    fun setListData(data: MutableList<Producto>) {
        dataListProducto = data
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}