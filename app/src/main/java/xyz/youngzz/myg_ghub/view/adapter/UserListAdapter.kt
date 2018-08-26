package xyz.youngzz.myg_ghub.view.adapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup
import com.github.twocoffeesoneteam.glidetovectoryou.GlideApp
import kotlinx.android.synthetic.main.list_item_user.view.*
import xyz.youngzz.myg_ghub.R
import xyz.youngzz.myg_ghub.api.model.User
import xyz.youngzz.myg_ghub.view.viewholder.UserViewHolder

class UserListAdapter : RecyclerView.Adapter<UserViewHolder>() {
    var items: List<User> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(parent)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val item = items[position]

        with(holder.itemView) {
            loginTextView.text = item.login


            GlideApp.with(this)
                    .load(item.avatarUrl)
                    .placeholder(R.drawable.ic_github_logo)
                    .into(actorAvatarImage)


        }
    }
}