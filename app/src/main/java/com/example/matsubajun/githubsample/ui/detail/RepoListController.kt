package com.example.matsubajun.githubsample.ui.detail

import com.airbnb.epoxy.AsyncEpoxyController
import com.example.matsubajun.githubsample.ListItemEmptyBindingModel_
import com.example.matsubajun.githubsample.ListItemRepoBindingModel_
import com.example.matsubajun.githubsample.domain.model.GithubUser
import com.example.matsubajun.githubsample.domain.model.Repo

class RepoListController(
        private val repoClickCallback: ((Repo) -> Unit)?
) : AsyncEpoxyController() {

    private var userData: GithubUser? = null
    private val repoList = mutableListOf<Repo>()

    override fun buildModels() {

        if (repoList.isEmpty()) {
            ListItemEmptyBindingModel_()
                    .id("empty")
                    .addTo(this)
            return
        }

        repoList.forEach {
            ListItemRepoBindingModel_()
                    .id(modelCountBuiltSoFar)
                    .repo(it)
                    .onRepoClick { _ ->
                        repoClickCallback?.invoke(it)
                    }
                    .addTo(this)
        }
    }

    fun setData(userData: GithubUser, repoList: List<Repo>) {
        this.userData = userData
        this.repoList.addAll(repoList)
        requestModelBuild()
    }
}