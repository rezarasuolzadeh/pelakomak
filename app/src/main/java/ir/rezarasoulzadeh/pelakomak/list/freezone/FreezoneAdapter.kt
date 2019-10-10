package ir.rezarasoulzadeh.pelakomak.list.freezone

import androidx.recyclerview.widget.RecyclerView
import ir.rezarasoulzadeh.pelakomak.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class FreezoneAdapter(private val freezone: Array<Freezone>) :
    RecyclerView.Adapter<FreezoneAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("not implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        holder.name.text = freezone[position].name
        holder.numerplate.setImageResource(freezone[position].numberplate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.model_for_freezone_items, parent, false)
        return ViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return freezone.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.freezone_name)
        val numerplate: ImageView = itemView.findViewById(R.id.freezone_numberplate)
    }

}