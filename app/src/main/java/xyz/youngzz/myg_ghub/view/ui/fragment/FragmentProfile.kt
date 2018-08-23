package xyz.youngzz.myg_ghub.view.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_profile.view.*
import timber.log.Timber
import xyz.youngzz.myg_ghub.R
import xyz.youngzz.myg_ghub.api.model.User
import xyz.youngzz.myg_ghub.utils.GlideApp

class FragmentProfile : Fragment() {

    companion object {
        fun newInstance(user: User): FragmentProfile {
            val fragment = FragmentProfile()
            fragment.setUserData(user)
            return fragment
        }
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)

        Timber.i(user.toString())

        GlideApp.with(this)
                .load(user.avatarUrl)
                .placeholder(R.drawable.ic_github_logo)
                .into(rootView.avatarImage)

        with(rootView) {
            toolbar_title.text = user.login
            nameTextView.text = user.name
            bioTextView.text = user.bio
            repoCountTextView.text = user.publicRepos.toString()
            followerCountTextView.text = user.followers.toString()
            followingCountTextView.text = user.following.toString()
        }

        return rootView
    }

    private lateinit var user: User

    fun setUserData(user: User) {
        this.user = user
    }
}