package com.kotlinusergroup.demo.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.kotlinusergroup.demo.R
import com.kotlinusergroup.ulfoodcourt.models.FoodMenu

/**
 * Created by ajithvgiri on 28/01/18.
 */
class FoodMenuRecyclerViewAdapter(var context: Context, private var foodMenuList: List<FoodMenu>) : RecyclerView.Adapter<FoodMenuRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_foodmenu, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        //Bind values
    }

    //Optional
    override fun getItemCount(): Int {
        return foodMenuList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //Create UI Widgets
    }


}