package com.example.matsubajun.githubsample.ui.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.TransitionInflater
import com.example.matsubajun.githubsample.R
import com.example.matsubajun.githubsample.data.Status
import com.example.matsubajun.githubsample.data.network.ErrorFeedBack
import com.example.matsubajun.githubsample.databinding.FragmentFollowBinding
import com.example.matsubajun.githubsample.ui.MainFragment
import com.example.matsubajun.githubsample.ui.MainFragmentArgs
import com.example.matsubajun.githubsample.ui.MainFragmentDirections
import com.example.matsubajun.githubsample.ui.common.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class FollowListFragment : BaseFragment() {

    private val followListViewModel: FollowListViewModel by viewModel()
    lateinit var binding: FragmentFollowBinding
    private val controller = FollowListController { icon, follow ->
        val extras = FragmentNavigatorExtras(icon to icon.transitionName)
        findNavController().navigate(MainFragmentDirections.actionToDetail(follow.login, follow.avatarUrl), extras)
    }
    private val isFollow by lazy {
        arguments!!.getBoolean(MainFragment.FollowPagerAdapter.EXTRA_IS_FOLLOW)
    }
    private val loginName by lazy {
        MainFragmentArgs.fromBundle(parentFragment!!.arguments!!).extraUserLogin
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        returnTransition = TransitionInflater.from(context).inflateTransition(R.transition.default_transition)
        exitTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.no_transition)

        setUpObserver()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_follow, container, false)
        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        followListViewModel.setFirstParam(isFollow, loginName)
    }

    private fun setUpView() {
        binding.recyclerView.apply {
            adapter = controller.adapter
            layoutManager = LinearLayoutManager(context)
        }

        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastPosition = layoutManager.findLastVisibleItemPosition()
                if (lastPosition == controller.adapter.itemCount - 1) {
                    followListViewModel.loadNextPage()
                }
            }
        })
    }

    private fun setUpObserver() {
        followListViewModel.followListLiveData.observe(this, Observer { resource ->
            if (followListViewModel.followRequest.value?.page == 1) {
                binding.resource = resource
            }
            when (resource?.status) {
                Status.SUCCESS -> {
                    resource.data?.let {
                        controller.addData(it.follows, it.hasNextPage)
                    }
                }
                Status.ERROR -> {
                    if (isVisible) {
                        resource.throwable?.let {
                            snackbar(ErrorFeedBack.map(it))?.show()
                        }
                    }
                }
                else -> {
                    // fall through
                }
            }
        })
    }
}