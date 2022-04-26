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
import edu.upc.appcatedraunesco.NoticiasFragmentDirections
import edu.upc.appcatedraunesco.R
import edu.upc.appcatedraunesco.models.Noticia

class NoticiasAdapter(private val context: Context) :
    RecyclerView.Adapter<NoticiasAdapter.ViewHolder>() {

    private var dataListNoticia = mutableListOf<Noticia>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoticiasAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.diseno_noticias, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItemNoticia = dataListNoticia[position]
        holder.itemView.findViewById<TextView>(R.id.tvTitulo).text = currentItemNoticia.titulo
        holder.itemView.findViewById<TextView>(R.id.tvDescripcion).text =
            currentItemNoticia.descripcion
        Picasso.get().load(currentItemNoticia.imagen)
            .into(holder.itemView.findViewById<ImageView>(R.id.ivImagen))

        //Funcion para ir a la noticia IMPORTANTE!!!!!
        holder.itemView.findViewById<CardView>(R.id.cardViewNoticias).setOnClickListener {
            val action = NoticiasFragmentDirections.actionToNoticiaFragmentInfo(currentItemNoticia)
            holder.itemView.findNavController().navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return dataListNoticia.size
    }

    fun setListData(data: MutableList<Noticia>) {
        dataListNoticia = data
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}