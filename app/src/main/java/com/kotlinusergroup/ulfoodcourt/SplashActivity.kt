package com.kotlinusergroup.ulfoodcourt

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.kotlinusergroup.ulfoodcourt.activities.MenuActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startActivity(Intent(this, MenuActivity::class.java))
        finish()
    }
}
