package xyz.youngzz.myg_ghub.view.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*
import xyz.youngzz.myg_ghub.R
import xyz.youngzz.myg_ghub.api.provideGithubApi
import xyz.youngzz.myg_ghub.utils.enqueue
import xyz.youngzz.myg_ghub.view.adapter.SearchRepoListAdapter


class FragmentSearch : Fragment() {

    companion object {
        fun newInstance(): FragmentSearch {
            val fragmentSearch = FragmentSearch()
            val args = Bundle()
            fragmentSearch.arguments = args
            return fragmentSearch
        }
    }

    lateinit var listAdapter: SearchRepoListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_search, container, false)

        rootView.searchListView.layoutManager = LinearLayoutManager(this.requireContext())
        rootView.searchListView.adapter = SearchRepoListAdapter()

        listAdapter = rootView.searchListView.adapter as SearchRepoListAdapter


        return rootView
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchView.isIconified = false
        searchView.clearFocus()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Log.i("onQueryTextSubmit", query)
                if(!query.isNullOrBlank()) {

                    val githubApi = provideGithubApi(requireContext())

                    val call = githubApi.searchRepository(query!!)
                    call.enqueue({ response ->
                        val statusCode = response.code()
                        if (statusCode == 200) {
                            val result = response.body()
                            result?.let {
                                listAdapter.items = it.items
                                listAdapter.notifyDataSetChanged()
                            }


                        } else {
                            //toast("error - $statusCode")
                        }


                    }, { t ->
                        //toast(t.localizedMessage)
                    })

                }
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                Log.i("onQueryTextChange", newText)
                return true
            }
        })


    }




    //private var mSearchView: SearchView? = null


}


