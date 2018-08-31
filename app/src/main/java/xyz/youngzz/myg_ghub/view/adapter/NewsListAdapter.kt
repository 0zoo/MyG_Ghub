package xyz.youngzz.myg_ghub.view.adapter

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.github.twocoffeesoneteam.glidetovectoryou.GlideApp
import kotlinx.android.synthetic.main.list_item_news.view.*
import timber.log.Timber
import xyz.youngzz.myg_ghub.R
import xyz.youngzz.myg_ghub.api.model.ReceivedEventsResponse
import xyz.youngzz.myg_ghub.api.provideGithubApi
import xyz.youngzz.myg_ghub.utils.addFagment
import xyz.youngzz.myg_ghub.utils.convertEventType
import xyz.youngzz.myg_ghub.utils.enqueue
import xyz.youngzz.myg_ghub.utils.getDateFromISO
import xyz.youngzz.myg_ghub.view.ui.MainActivity
import xyz.youngzz.myg_ghub.view.ui.ProfileActivity
import xyz.youngzz.myg_ghub.view.ui.fragment.FragmentProfile
import xyz.youngzz.myg_ghub.view.viewholder.NewsViewHolder

class NewsListAdapter : RecyclerView.Adapter<NewsViewHolder>() {
    var items: List<ReceivedEventsResponse> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val item = items[position]

        with(holder.itemView) {
            val user = item.actor
            val repo = item.repo

            actorNameTextView.text = user.login
            eventTypeTextView.text = item.type.convertEventType(context)
            repoNameTextView.text = repo.name
            createdAtTextView.text = item.createdAt.getDateFromISO()

            GlideApp.with(this)
                    .load(user.avatarUrl)
                    .placeholder(R.drawable.ic_github_logo)
                    .into(actorAvatarImage)

            val intent = Intent(context, ProfileActivity::class.java)
            intent.putExtra("OWNER",user.login)

            actorAvatarImage.setOnClickListener {
                startActivity(context,intent,null)
            }
            actorNameTextView.setOnClickListener { v->

                val activity = v.context as AppCompatActivity


                provideGithubApi(context).getOtherUser(user.login).enqueue({ response ->
                    response.body()?.let { user ->
                        provideGithubApi(context).getStarredRepo(user.login).enqueue({ response ->
                            response.body()?.let {
                                user.starredCount = it.size
                                Timber.i("setting!!")
                                FragmentProfile.newInstance(user).addFagment(R.id.content,user.login,activity.supportFragmentManager)
                            }
                        }, {})
                    }
                }, { t ->
                    Timber.e(t.localizedMessage)
                })



            }

        }
    }
}