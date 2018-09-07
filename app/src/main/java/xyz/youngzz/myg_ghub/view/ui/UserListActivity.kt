package xyz.youngzz.myg_ghub.view.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_user_list.*
import kotlinx.android.synthetic.main.toolbar_header.*
import retrofit2.Call
import timber.log.Timber
import xyz.youngzz.myg_ghub.R
import xyz.youngzz.myg_ghub.api.model.User
import xyz.youngzz.myg_ghub.api.provideGithubApi
import xyz.youngzz.myg_ghub.utils.enqueue
import xyz.youngzz.myg_ghub.view.adapter.UserListAdapter

class UserListActivity : AppCompatActivity() {

    lateinit var listAdapter: UserListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_list)

        listAdapter = UserListAdapter()

        userListView.adapter = listAdapter
        userListView.layoutManager = LinearLayoutManager(this)


        val owner = intent.extras.getString("OWNER")
        val action = intent.extras.getString("ACTION")


        toolbar_title.text = action

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }


        when(action){
            "Follower" -> {
                bindingUsers(provideGithubApi(this).getFollowers(owner))
            }

            "Following" -> {
                bindingUsers(provideGithubApi(this).getFollowing(owner))
            }

        }
    }

    private fun bindingUsers(call : Call<List<User>>){

        call.enqueue({response ->
            val statusCode = response.code()
            if ( statusCode == 200) {
                val result = response.body()
                result?.let {
                    listAdapter.items = it
                    listAdapter.notifyDataSetChanged()
                }

            } else {
                Timber.e("error - $statusCode")
            }
        },{
            Timber.e(it.localizedMessage)
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
