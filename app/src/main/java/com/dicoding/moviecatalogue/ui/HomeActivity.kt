package com.dicoding.moviecatalogue.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.dicoding.moviecatalogue.R
import com.dicoding.moviecatalogue.databinding.ActivityHomeBinding
import com.dicoding.moviecatalogue.ui.movie.MovieFragment
import com.dicoding.moviecatalogue.ui.tvshow.TvShowFragment
import com.google.android.material.navigation.NavigationView

class HomeActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        initDrawer()
        setFragment(MovieFragment(), getString(R.string.app_name))
    }

    private fun initDrawer() {
        val toggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.appBarMain.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)
    }

    private fun setFragment(fragment: Fragment, title: String) {
        supportActionBar?.title = title
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment)
        fragmentTransaction.commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
        when (item.itemId) {
            R.id.navigation_movie -> {
                if (currentFragment !is MovieFragment)
                    setFragment(MovieFragment(), getString(R.string.app_name))
            }
            R.id.navigation_tv_show -> {
                if (currentFragment !is TvShowFragment)
                    setFragment(TvShowFragment(), getString(R.string.tv_show_catalogue))
            }
            R.id.navigation_favourite -> {
                val uri = Uri.parse("moviecatalogue://favorite")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
            }
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}