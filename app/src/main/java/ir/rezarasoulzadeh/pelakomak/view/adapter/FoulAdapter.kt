package ir.rezarasoulzadeh.pelakomak.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.rezarasoulzadeh.pelakomak.R
import ir.rezarasoulzadeh.pelakomak.model.Foul
import ir.rezarasoulzadeh.pelakomak.service.utils.Format
import kotlinx.android.synthetic.main.model_for_foul_items.view.*

class FoulAdapter(private val fouls: List<Foul>) : RecyclerView.Adapter<FoulAdapter.FoulViewHolder>() {

    private lateinit var format: Format

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoulViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.model_for_foul_items, parent, false)
        return FoulViewHolder(view)
    }

    override fun getItemCount(): Int {
        return fouls.size
    }

    override fun onBindViewHolder(holder: FoulViewHolder, position: Int) {
        holder.bind(fouls[position], position)
    }

    inner class FoulViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(foul: Foul, position: Int) {
            format = Format()
            val time = foul.createdAt.split(" ")
            itemView.titleTextView.isSelected = true
            itemView.dateTextView.text = time[0]
            itemView.timeTextView.text = time[1]
            itemView.titleTextView.text = foul.description
            itemView.priceTextView.text = format.priceFormat(foul.amount)
            itemView.cityTextView.text = foul.city
            itemView.locationTextView.text = foul.location
            itemView.typeTextView.text = foul.type
            itemView.countTextView.text = (position + 1).toString()
        }
    }
}