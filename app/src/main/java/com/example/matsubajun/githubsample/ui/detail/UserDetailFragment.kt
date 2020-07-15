package com.example.matsubajun.githubsample.ui.detail

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionInflater
import com.example.matsubajun.githubsample.R
import com.example.matsubajun.githubsample.data.Status
import com.example.matsubajun.githubsample.data.network.ErrorFeedBack
import com.example.matsubajun.githubsample.databinding.FragmentUserDetailBinding
import com.example.matsubajun.githubsample.ui.common.BaseFragment
import com.facebook.imagepipeline.request.ImageRequestBuilder
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailFragment : BaseFragment() {

    private val args by navArgs<UserDetailFragmentArgs>()

    private val login by lazy { args.extraUserLogin }
    private val userImage by lazy { args.extraUserImage }

    private val controller = RepoListController(
            repoClickCallback = {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it.htmlUrl)))
            }
    )
    private val userDetailViewModel: UserDetailViewModel by viewModel()
    lateinit var binding: FragmentUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.default_transition)
        enterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.no_transition)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_detail, container, false)
        binding.lifecycleOwner = this
        binding.userDetailViewModel = userDetailViewModel
        binding.icon.transitionName = login

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpView()
        setUpObserver()
        userDetailViewModel.setParam(login)
    }

    private fun setUpView() {
        binding.recyclerView.apply {
            adapter = controller.adapter
            layoutManager = LinearLayoutManager(context)
        }
        binding.followingCount.setOnClickListener {
            findNavController().navigate(UserDetailFragmentDirections.actionToFollow().setExtraUserLogin(login).setExtraIsFollow(true))
        }
        binding.followerCount.setOnClickListener {
            findNavController().navigate(UserDetailFragmentDirections.actionToFollow().setExtraUserLogin(login).setExtraIsFollow(false))
        }

        val imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(userImage)).build()
        binding.icon.setImageRequest(imageRequest)
    }

    private fun setUpObserver() {
        userDetailViewModel.userDetailLiveData.observe(this, Observer { resource ->
            //binding.resource = it
            when (resource?.status) {
                Status.SUCCESS -> {
                    resource.data?.let {
                        controller.setData(it.githubUser, it.repos)
                    }
                }
                Status.ERROR -> {
                    resource.throwable?.let {
                        snackbar(ErrorFeedBack.map(it))?.show()
                    }
                }
                else -> {
                    // fall through
                }
            }
        })
    }
}