package xyz.youngzz.myg_ghub.view.ui

import android.graphics.Color
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.github.twocoffeesoneteam.glidetovectoryou.GlideApp
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import kotlinx.android.synthetic.main.fragment_profile.*
import timber.log.Timber
import xyz.youngzz.myg_ghub.R
import xyz.youngzz.myg_ghub.api.model.User
import xyz.youngzz.myg_ghub.api.provideGithubApi
import xyz.youngzz.myg_ghub.utils.enqueue
import org.jetbrains.anko.startActivity

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_profile)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val login = intent.extras.getString("OWNER")

        provideGithubApi(this).getOtherUser(login).enqueue({ response ->
            val statusCode = response.code()
            if (statusCode == 200) {
                response.body()?.let { user ->
                    provideGithubApi(this).getStarredRepo(user.login).enqueue({ response ->
                        response.body()?.let {
                            user.starredCount = it.size
                            bindingView(user)
                        }
                    }, {})
                }

            } else {
                Timber.e("error - $statusCode")
            }

        }, { t ->
            Timber.e(t.localizedMessage)
        })
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home) {
            finish()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    private fun bindingView(user: User) {

        GlideApp.with(this)
                .load(user.avatarUrl)
                .placeholder(R.drawable.ic_github_logo)
                .into(avatarImage)

        val uri = Uri.parse("https://ghchart.rshah.org/${user.login}")
        GlideToVectorYou.justLoadImage(this, uri, contributionsImageView)

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
            startActivity<UserListActivity>("OWNER" to user.login, "ACTION" to "Follower")
        }

        followingCountTextView.setOnClickListener {
            startActivity<UserListActivity>("OWNER" to user.login, "ACTION" to "Following")
        }
    }

}
