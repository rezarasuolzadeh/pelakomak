package ir.rezarasoulzadeh.pelakomak.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.rezarasoulzadeh.pelakomak.R
import ir.rezarasoulzadeh.pelakomak.model.Foul
import kotlinx.android.synthetic.main.model_for_foul_items.view.*

class FoulAdapter(private val fouls: List<Foul>) : RecyclerView.Adapter<FoulAdapter.FoulViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoulViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.model_for_foul_items, parent, false)
        return FoulViewHolder(view)
    }

    override fun getItemCount(): Int {
        return fouls.size
    }

    override fun onBindViewHolder(holder: FoulViewHolder, position: Int) {
        holder.bind(fouls[position])
    }

    inner class FoulViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(foul: Foul) {
            val time = foul.createdAt.split(" ")
            itemView.dateTextView.text = time[0]
            itemView.timeTextView.text = time[1]
            itemView.titleTextView.text = foul.description
            itemView.priceTextView.text = foul.amount.toString()
            itemView.cityTextView.text = foul.city
            itemView.locationTextView.text = foul.location
        }
    }

}