package com.kotlinusergroup.ulfoodcourt.activities

import android.os.Bundle
import android.provider.Settings
import android.support.constraint.ConstraintLayout
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import com.google.firebase.database.*
import com.kotlinusergroup.ulfoodcourt.R
import com.kotlinusergroup.ulfoodcourt.adapters.FoodMenuRecyclerViewAdapter
import com.kotlinusergroup.ulfoodcourt.models.Feedback
import com.kotlinusergroup.ulfoodcourt.models.FoodMenu
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.layout_feedback.*
import java.util.*


class MenuActivity : AppCompatActivity() {

    private var TAG = "MainActivity"
    //ArrayList
    private var foodMenuList = ArrayList<FoodMenu>()

    // Firebase init
    lateinit var database: FirebaseDatabase
    lateinit var foodMenuDatabase: DatabaseReference
    lateinit var feedBackDatabase: DatabaseReference


    //BottomSheet behavior
    lateinit var behavior: BottomSheetBehavior<ConstraintLayout>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        //Enable Offline
        FirebaseDatabase.getInstance().setPersistenceEnabled(true)

        database = FirebaseDatabase.getInstance()
        foodMenuDatabase = database.getReference("menu")
        feedBackDatabase = database.getReference("feedback")


        behavior = BottomSheetBehavior.from(bottomsheet)
        initBottomSheet()
        initFoodMenu()

        //Add Feedback
        buttonSend.setOnClickListener {

            val rating = findViewById<RadioButton>(radioGroup.checkedRadioButtonId)

            if (!rating.text.isEmpty()) {
                //Unique Device Identiy
                val id = Settings.Secure.ANDROID_ID
                val rating = rating.text.toString()
                val feedbackText = editTextFeedback.text.toString()
                val time = Calendar.getInstance().time.toString()

                //Feedback Model
                val feedback = Feedback(id, rating, feedbackText, time)
                //Set values
                feedBackDatabase.push().setValue(feedback)

                Toast.makeText(this, getString(R.string.thanks_message), Toast.LENGTH_SHORT).show()
                editTextFeedback.text.clear()
                radioGroup.clearCheck()
            } else {
                Toast.makeText(this, getString(R.string.select_rating), Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun initFoodMenu() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = FoodMenuRecyclerViewAdapter(this, foodMenuList)

        //Write to the database
//        var menuList = ArrayList<FoodMenu>()
//        menuList.add(FoodMenu(0,"Burger","http://bit.ly/2Ee4M6I",200,1))
//        menuList.add(FoodMenu(1,"Pizza","http://bit.ly/2rFdOqY",200,0))
//        foodMenuDatabase.setValue(menuList)


        // Read from the database
        foodMenuDatabase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                foodMenuList.clear()

                for (snapshot in dataSnapshot.children) {
                    val user = snapshot.getValue<FoodMenu>(FoodMenu::class.java)
                    foodMenuList.add(user!!)
                    recyclerView.adapter.notifyDataSetChanged()
                }

//                OR
//                dataSnapshot.children
//                        .map { it.getValue(FoodMenu::class.java) }
//                        .forEach {
//                            foodMenuList.add(it)
//                            recyclerView.adapter.notifyDataSetChanged()
//                        }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })

    }


    private fun initBottomSheet() {

        imageViewUp.setOnClickListener {
            if (behavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
                imageViewUp.setImageResource(R.drawable.ic_down)
            } else {
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                imageViewUp.setImageResource(R.drawable.ic_up)
            }
        }

        
        behavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
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

    override fun onBackPressed() {
        if (behavior.state == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        } else {
            onBackPressed()
        }
    }

}
