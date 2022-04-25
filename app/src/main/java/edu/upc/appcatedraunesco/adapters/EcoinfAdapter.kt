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
import edu.upc.appcatedraunesco.R
import edu.upc.appcatedraunesco.models.Ecoinf

class EcoinfAdapter(private val context: Context) :
    RecyclerView.Adapter<EcoinfAdapter.ViewHolder>() {

    private var dataListEcoinf = mutableListOf<Ecoinf>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EcoinfAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.diseno_ecoinf, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItemEcoinf = dataListEcoinf[position]
        holder.itemView.findViewById<TextView>(R.id.tvNombre).text = currentItemEcoinf.nombre
        holder.itemView.findViewById<TextView>(R.id.tvDirecion).text = currentItemEcoinf.direccion
        holder.itemView.findViewById<TextView>(R.id.tvNumTel).text = currentItemEcoinf.numeroTelefono
        Picasso.get().load(currentItemEcoinf.imagen).into(holder.itemView.findViewById<ImageView>(R.id.ivImagen))

        //Funcion para ir a la noticia IMPORTANTE!!!!!
        /*holder.itemView.findViewById<CardView>(R.id.cardViewEcoinf).setOnClickListener {
            val action = EcoinfFragmentDirections.actionToInfoClient(currentItemEcoinf)
            holder.itemView.findNavController().navigate(action)
        }*/
    }

    override fun getItemCount(): Int {
        return dataListEcoinf.size
    }

    fun setListData(data: MutableList<Ecoinf>) {
        dataListEcoinf = data
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }
}