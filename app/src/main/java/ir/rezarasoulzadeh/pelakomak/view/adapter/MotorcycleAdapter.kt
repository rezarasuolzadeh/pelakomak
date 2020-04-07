package ir.rezarasoulzadeh.pelakomak.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.rezarasoulzadeh.pelakomak.R
import ir.rezarasoulzadeh.pelakomak.model.Motorcycle
import kotlinx.android.synthetic.main.model_for_motorcycle_city.view.*

class MotorcycleAdapter(private val motorcycleList: List<Motorcycle>) : RecyclerView.Adapter<MotorcycleAdapter.FarmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FarmViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.model_for_motorcycle_city, parent, false)
        return FarmViewHolder(view)
    }

    override fun getItemCount(): Int {
        return motorcycleList.size
    }

    override fun onBindViewHolder(holder: FarmViewHolder, position: Int) {
        holder.bind(motorcycleList[position])
    }

    inner class FarmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(motorcycle: Motorcycle) {
            itemView.motorcycleCityNumber.text = motorcycle.number
            itemView.motorcycleCityCounty.text = motorcycle.state
        }
    }

}