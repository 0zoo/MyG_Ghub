package xyz.youngzz.myg_ghub.view.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_news.view.*
import timber.log.Timber
import xyz.youngzz.myg_ghub.R
import xyz.youngzz.myg_ghub.api.provideGithubApi
import xyz.youngzz.myg_ghub.utils.enqueue
import xyz.youngzz.myg_ghub.view.adapter.NewsListAdapter

class FragmentNews : Fragment() {

    private var login: String = ""
    lateinit var listAdapter: NewsListAdapter

    companion object {
        fun newInstance(ownerLogin: String): FragmentNews {
            val fragment = FragmentNews()
            val args = Bundle()
            args.putString("LOGIN", ownerLogin)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_news, container, false)

        arguments?.let { args ->
            login = args.getString("LOGIN")
            val call = provideGithubApi(requireContext()).receivedEvents(login)
            call.enqueue({ response ->
                val statusCode = response.code()
                if (statusCode == 200) {
                    val result = response.body()
                    result?.let {
                        Timber.i(it.toString())
                        listAdapter.items = it
                        listAdapter.notifyDataSetChanged()
                    }

                } else {
                    Timber.e("error - $statusCode")
                }

            }, {
                Timber.e(it.localizedMessage)
            })

        }


        rootView.newsListView.layoutManager = LinearLayoutManager(this.requireContext())
        rootView.newsListView.adapter = NewsListAdapter()

        listAdapter = rootView.newsListView.adapter as NewsListAdapter

        return rootView
    }
}
