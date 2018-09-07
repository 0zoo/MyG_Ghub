package xyz.youngzz.myg_ghub.view.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_repo_list.*
import kotlinx.android.synthetic.main.toolbar_header.*
import timber.log.Timber
import xyz.youngzz.myg_ghub.R
import xyz.youngzz.myg_ghub.api.provideGithubApi
import xyz.youngzz.myg_ghub.utils.enqueue
import xyz.youngzz.myg_ghub.view.adapter.RepoListAdapter

class RepoListActivity : AppCompatActivity() {

    lateinit var listAdapter: RepoListAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo_list)

        listAdapter = RepoListAdapter()

        repoListView.adapter = listAdapter
        repoListView.layoutManager = LinearLayoutManager(this)


        val login = intent.extras.getString("LOGIN")

        provideGithubApi(this).getUserRepos(login).enqueue({response ->
            val statusCode = response.code()
            if (statusCode == 200) {
                val result = response.body()
                result?.let {
                    listAdapter.items = it
                    listAdapter.notifyDataSetChanged()
                }

            } else {
                Timber.e("error - $statusCode")
            }
        }, {
            Timber.e(it.localizedMessage)
        })



        toolbar_title.text = "Repositories"

        setSupportActionBar(toolbar)
        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }



    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


}
