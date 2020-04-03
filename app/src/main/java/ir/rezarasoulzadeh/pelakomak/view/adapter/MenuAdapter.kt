package ir.rezarasoulzadeh.pelakomak.view.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.rezarasoulzadeh.pelakomak.R
import ir.rezarasoulzadeh.pelakomak.model.Menu
import ir.rezarasoulzadeh.pelakomak.view.activity.*
import kotlinx.android.synthetic.main.model_for_menu_items.view.*

class MenuAdapter(private val menuList: List<Menu>) :
    RecyclerView.Adapter<MenuAdapter.MenuViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.model_for_menu_items, parent, false)
        return MenuViewHolder(view)
    }

    override fun getItemCount(): Int {
        return menuList.size
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(menuList[position])
    }

    inner class MenuViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(menu: Menu) {
            var intent: Intent
            itemView.menuTitle.text = menu.title
            itemView.menuImage.setImageResource(menu.image)
            itemView.setOnClickListener {
                intent = when (menu.id) {
                    1 -> Intent(itemView.context, CarActivity::class.java)
                    2 -> Intent(itemView.context, MotorcycleActivity::class.java)
                    3 -> Intent(itemView.context, OtherActivity::class.java)
                    4 -> Intent(itemView.context, FreezoneActivity::class.java)
                    5 -> Intent(itemView.context, CarCityActivity::class.java)
                    6 -> Intent(itemView.context, MotorcycleCityActivity::class.java)
                    else -> Intent(itemView.context, HomeActivity::class.java)
                }
                itemView.context.startActivity(intent)
            }
        }
    }

}