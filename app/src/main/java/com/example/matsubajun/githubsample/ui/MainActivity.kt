package com.example.matsubajun.githubsample.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.matsubajun.githubsample.R
import com.example.matsubajun.githubsample.databinding.ActivityMainBinding
import com.example.matsubajun.githubsample.databinding.DrawerHeaderBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()
    private val binding by lazy {
        DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpView()
    }

    private fun setUpView() {
        setSupportActionBar(binding.mainToolbar)

        // drawer
        binding.mainDrawerNavigation.setNavigationItemSelectedListener {
            binding.mainDrawer.closeDrawers()
            return@setNavigationItemSelectedListener true
        }
        binding.mainDrawerNavigation.run {
            DataBindingUtil.bind<DrawerHeaderBinding>(getHeaderView(0))
            val headerBinding = DataBindingUtil.getBinding<DrawerHeaderBinding>(getHeaderView(0))
            headerBinding?.mainViewModel = mainViewModel
        }

        // navigation
        val navController = findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this, navController, binding.mainDrawer)
    }

    override fun onBackPressed() {
        if (binding.mainDrawer.isDrawerOpen(GravityCompat.START)) {
            binding.mainDrawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(findNavController(R.id.nav_host_fragment), binding.mainDrawer)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

    }
}
