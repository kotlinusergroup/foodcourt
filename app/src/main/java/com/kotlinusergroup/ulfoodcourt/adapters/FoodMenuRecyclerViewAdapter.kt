package com.kotlinusergroup.ulfoodcourt.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.kotlinusergroup.ulfoodcourt.R
import com.kotlinusergroup.ulfoodcourt.models.FoodMenu
import com.squareup.picasso.Picasso


/**
 * Created by ajithvgiri on 18/06/17
 */

class FoodMenuRecyclerViewAdapter(var context: Context, private var foodMenuList: List<FoodMenu>) : RecyclerView.Adapter<FoodMenuRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_foodmenu, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val foodMenu = foodMenuList[position]
        viewHolder.textViewItem.text = foodMenu.name
        viewHolder.textViewPrice.text = foodMenu.price.toString()
        if (foodMenu.status == 1) {
            viewHolder.textViewAvailability.text = "Available"
        } else {
            viewHolder.textViewAvailability.text = "Not Available"
        }
        Picasso.with(context).load(foodMenu.image).into(viewHolder.imageViewItem)

    }

    override fun getItemCount(): Int {
        return foodMenuList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var textViewItem: TextView = itemView.findViewById(R.id.textViewItem)
        var textViewPrice: TextView = itemView.findViewById(R.id.textViewPrice)
        var textViewAvailability: TextView = itemView.findViewById(R.id.textViewAvailability)
        var imageViewItem: ImageView = itemView.findViewById(R.id.imageViewItem)
    }


}
