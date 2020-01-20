package ir.rezarasoulzadeh.pelakomak.list.other

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.view.SimpleDraweeView
import ir.rezarasoulzadeh.pelakomak.R

class OtherAdapter(private val other: Array<Other>) :
    RecyclerView.Adapter<OtherAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("not implemented")
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: MutableList<Any>) {
        holder.name.text = other[position].name
        holder.numberplate.setImageResource(other[position].numberplate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewHolder =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.model_for_other_items, parent, false)
        return ViewHolder(viewHolder)
    }

    override fun getItemCount(): Int {
        return other.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.other_name)
        val numberplate: SimpleDraweeView = itemView.findViewById(R.id.other_numberplate)
    }

}