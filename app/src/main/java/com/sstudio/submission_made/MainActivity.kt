package com.sstudio.submission_made

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navController = supportFragmentManager
            .findFragmentById(R.id.fragment_nav_host) as NavHostFragment
        NavigationUI.setupWithNavController(
            bottom_nav,
            navController.navController
        )
    }

}