package xyz.youngzz.myg_ghub.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.github.twocoffeesoneteam.glidetovectoryou.GlideApp
import kotlinx.android.synthetic.main.list_item_sample.view.*
import xyz.youngzz.myg_ghub.R
import xyz.youngzz.myg_ghub.api.model.GithubRepo
import xyz.youngzz.myg_ghub.view.viewholder.SearchRepoViewHolder

class SearchRepoListAdapter : RecyclerView.Adapter<SearchRepoViewHolder>() {
    var items: List<GithubRepo> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchRepoViewHolder {
        return SearchRepoViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: SearchRepoViewHolder, position: Int) {
        val item = items[position]

        with(holder.itemView) {
            repoNameText.text = item.fullName
            repoOwnerText.text = item.owner.login


            GlideApp.with(this)
                    .load(item.owner.avatarUrl)
                    .placeholder(R.drawable.ic_github_logo)
                    .into(ownerAvatarImage)
        }
    }
}