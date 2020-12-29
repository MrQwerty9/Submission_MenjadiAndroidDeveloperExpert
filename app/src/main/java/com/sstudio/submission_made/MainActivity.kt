package com.sstudio.submission_made

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sstudio.submission_made.ui.channel.ChannelFragment
import com.sstudio.submission_made.ui.favorite.FavoriteFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar.title = this.getString(R.string.app_name)
        supportActionBar?.elevation = 0f
        bottom_nav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        if (savedInstanceState == null){
            bottom_nav.selectedItemId = R.id.navigation_channel
        }
    }

    private val mOnNavigationItemSelectedListener =
        object : BottomNavigationView.OnNavigationItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                val fragment: Fragment
                when (item.itemId){
                    R.id.navigation_channel -> {
                        fragment = ChannelFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(
                                R.id.layout_container, fragment, fragment::class.java.simpleName
                            ).commit()
                        return true
                    }
                    R.id.navigation_favorite -> {
                        fragment = FavoriteFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(
                                R.id.layout_container, fragment, fragment::class.java.simpleName
                            ).commit()
                        return true
                    }
                }
                return false
            }
        }
}