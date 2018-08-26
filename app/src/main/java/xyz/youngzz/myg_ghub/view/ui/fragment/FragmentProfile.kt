package xyz.youngzz.myg_ghub.view.ui.fragment

import android.content.Intent
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade
import kotlinx.android.synthetic.main.fragment_profile.view.*
import timber.log.Timber
import xyz.youngzz.myg_ghub.R
import xyz.youngzz.myg_ghub.api.model.User
import xyz.youngzz.myg_ghub.utils.GlideApp
import xyz.youngzz.myg_ghub.utils.SvgSoftwareLayerSetter
import xyz.youngzz.myg_ghub.view.ui.UserListActivity
import android.R.attr.scaleType
import android.R.attr.button
import android.widget.TextView
import android.widget.ImageView.ScaleType
import java.nio.file.Files.delete
import java.nio.file.Files.isDirectory
import com.bumptech.glide.Glide
import android.text.method.TextKeyListener.clear
import android.widget.ImageView
import com.bumptech.glide.RequestBuilder
import xyz.youngzz.myg_ghub.utils.GlideRequests








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

            starCountTextView.text = user.starredCount.toString()

            companyTextView.text = user.company
            locationTextView.text = user.location
            blogTextView.text = user.blog
            emailTextView.text = user.email

            val createdStr = "Created At ${user.createdAt.split("T")[0]}"
            val updatedStr = "Updated At ${user.updatedAt.split("T")[0]}"
            createdAtTextView.text = createdStr
            updatedAtTextView.text = updatedStr

            followerCountTextView.setOnClickListener {
                Timber.i("click!!")
                val intent = Intent(context,UserListActivity::class.java)
                intent.putExtra("OWNER",user.login)
                intent.putExtra("ACTION","Follower")
                startActivity(intent)
            }

            followingCountTextView.setOnClickListener {
                val intent = Intent(context,UserListActivity::class.java)
                intent.putExtra("OWNER",user.login)
                intent.putExtra("ACTION","Following")
                startActivity(intent)
            }
        }


        val requestBuilder = GlideApp.with(this)
                .`as`(PictureDrawable::class.java)
                .placeholder(R.drawable.ic_github_logo)
                //.transition(withCrossFade())
                .listener(SvgSoftwareLayerSetter())

        val uri = Uri.parse("http://www.clker.com/cliparts/u/Z/2/b/a/6/android-toy-h.svg")
        requestBuilder.load(uri).into(rootView.contributionsImageView)

        return rootView
    }

    private lateinit var user: User

    fun setUserData(user: User) {
        this.user = user
    }


}