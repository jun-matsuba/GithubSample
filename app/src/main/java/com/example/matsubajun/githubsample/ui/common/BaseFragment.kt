package com.example.matsubajun.githubsample.ui.common

import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.matsubajun.githubsample.R
import com.example.matsubajun.githubsample.data.network.ErrorFeedBack
import com.example.matsubajun.githubsample.ui.MainActivity
import com.google.android.material.snackbar.Snackbar

open class BaseFragment : Fragment() {

    fun snackbar(errorFeedback: ErrorFeedBack, duration: Int = Snackbar.LENGTH_LONG, view: View? = getView()): Snackbar? {
        view ?: return null
        return Snackbar.make(view, errorFeedback.getMessage(context!!), duration)
    }

    fun findNavController(): NavController {
        return Navigation.findNavController(activity as MainActivity, R.id.nav_host_fragment)
    }
}