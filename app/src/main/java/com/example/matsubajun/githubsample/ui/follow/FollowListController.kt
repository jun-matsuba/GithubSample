package com.example.matsubajun.githubsample.ui.follow

import com.airbnb.epoxy.AsyncEpoxyController
import com.example.matsubajun.githubsample.ListFooterBindingModel_
import com.example.matsubajun.githubsample.ListItemEmptyBindingModel_
import com.example.matsubajun.githubsample.ListItemFollowBindingModel_
import com.example.matsubajun.githubsample.R
import com.example.matsubajun.githubsample.domain.model.Follow
import com.facebook.drawee.view.SimpleDraweeView

class FollowListController(private val clickCallback: ((SimpleDraweeView, Follow) -> Unit)?
) : AsyncEpoxyController() {

    private var showFooter = true
    private val followList = mutableListOf<Follow>()

    override fun buildModels() {
        if (followList.isEmpty()) {
            ListItemEmptyBindingModel_()
                    .id("empty")
                    .addTo(this)
            return
        }

        followList.forEach {
            ListItemFollowBindingModel_()
                    .id(modelCountBuiltSoFar)
                    .follow(it)
                    .onClick { view ->
                        val icon = view.findViewById<SimpleDraweeView>(R.id.image)
                        clickCallback?.invoke(icon, it)
                    }
                    .onBind { _, view, _ ->
                        val icon = view.dataBinding.root.findViewById<SimpleDraweeView>(R.id.image)
                        icon.transitionName = it.login
                    }
                    .addTo(this)
        }

        ListFooterBindingModel_()
                .id("footer")
                .addIf(showFooter, this)
    }

    fun addData(items: List<Follow>, showFooter: Boolean) {
        this.showFooter = showFooter
        followList.addAll(items)
        requestModelBuild()
    }
}