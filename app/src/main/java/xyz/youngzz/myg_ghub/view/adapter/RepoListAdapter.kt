package xyz.youngzz.myg_ghub.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.github.twocoffeesoneteam.glidetovectoryou.GlideApp
import kotlinx.android.synthetic.main.list_item_repo.view.*
import xyz.youngzz.myg_ghub.R
import xyz.youngzz.myg_ghub.api.model.Repo
import xyz.youngzz.myg_ghub.view.viewholder.RepoViewHolder

class RepoListAdapter : RecyclerView.Adapter<RepoViewHolder>() {
    var items: List<Repo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder = RepoViewHolder(parent)

    override fun getItemCount(): Int = items.count()

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        val item = items[position]

        with(holder.itemView) {

            if (item.private) {
                GlideApp.with(this)
                        .load(R.drawable.ic_lock_outline_black_24dp)
                        .placeholder(R.drawable.ic_github_logo)
                        .into(repoPrivateImage)
            } else {
                GlideApp.with(this)
                        .load(R.drawable.repo)
                        .placeholder(R.drawable.ic_github_logo)
                        .into(repoPrivateImage)
            }



            repoNameText.text = item.name
            repoDescriptionText.text = item.description
            repoLanguageText.text = item.language

            watchersCountTextView.text = item.watchersCount.toString()
            stargazersCountTextView.text = item.stargazersCount.toString()
            forksCountTextView.text = item.forksCount.toString()

        }
    }
}