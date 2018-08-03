package xyz.youngzz.myg_ghub.view.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import xyz.youngzz.myg_ghub.R

class FragmentSearch : Fragment(){

    companion object {
        fun newInstance(): FragmentSearch {
            val fragmentSearch = FragmentSearch()
            val args = Bundle()
            fragmentSearch.arguments = args
            return fragmentSearch
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //textView.text = "change!"
        return inflater.inflate(R.layout.fragment_sample, container, false)
    }
}