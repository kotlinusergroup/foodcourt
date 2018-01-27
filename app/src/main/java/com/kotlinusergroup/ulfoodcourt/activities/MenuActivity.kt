package com.kotlinusergroup.ulfoodcourt.activities

import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.kotlinusergroup.ulfoodcourt.R
import com.kotlinusergroup.ulfoodcourt.adapters.FoodMenuRecyclerViewAdapter
import com.kotlinusergroup.ulfoodcourt.models.FoodMenu
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.layout_feedback.*

class MenuActivity : AppCompatActivity() {

    private var TAG = "MainActivity"
    private var foodMenuList = ArrayList<FoodMenu>()
    // Write a message to the database
    private val database = FirebaseDatabase.getInstance()
    private var foodMenuDatabase = database.getReference("menu")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        initBottomSheet()

        initFoodMenu()
    }

    private fun initFoodMenu() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FoodMenuRecyclerViewAdapter(foodMenuList)

        // Read from the database
        foodMenuDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                foodMenuList.clear()
                dataSnapshot.children
                        .map { it.getValue(FoodMenu::class.java) }
                        .forEach {
                            Log.e("Get Data", it!!.name)
                            foodMenuList.add(it)
                            recyclerView.adapter.notifyDataSetChanged()
                        }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })


    }


    private fun initBottomSheet() {

        val behavior = BottomSheetBehavior.from(bottomsheet)

        imageViewUp.setOnClickListener {
            if (behavior!!.state == BottomSheetBehavior.STATE_COLLAPSED) {
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                imageViewUp.setImageResource(R.drawable.ic_down)
            } else {
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                imageViewUp.setImageResource(R.drawable.ic_up)
            }
        }

        behavior?.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                //showing the different states
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        imageViewUp.setImageResource(R.drawable.ic_down)
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        imageViewUp.setImageResource(R.drawable.ic_up)
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // React to dragging events
            }
        })

    }

}
