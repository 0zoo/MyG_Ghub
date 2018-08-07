package xyz.youngzz.myg_ghub.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import kotlinx.android.synthetic.main.list_item_news.view.*
import xyz.youngzz.myg_ghub.R
import xyz.youngzz.myg_ghub.api.model.ReceivedEventsResponse
import xyz.youngzz.myg_ghub.utils.GlideApp
import xyz.youngzz.myg_ghub.utils.convertEventType
import xyz.youngzz.myg_ghub.utils.getDateFromISO
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
        }
    }
}