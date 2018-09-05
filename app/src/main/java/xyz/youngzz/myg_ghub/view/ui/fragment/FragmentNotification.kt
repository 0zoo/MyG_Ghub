package xyz.youngzz.myg_ghub.view.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_notification.view.*
import xyz.youngzz.myg_ghub.R
import xyz.youngzz.myg_ghub.view.adapter.TabAdapter

class FragmentNotification : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_notification, container, false)

        val viewPager = rootView.findViewById<ViewPager>(R.id.viewPagerContent)
        rootView.tabs.setupWithViewPager(viewPager)

        setupViewPager(viewPager)

        return rootView
    }

    private fun setupViewPager(viewPager: ViewPager){
        val adapter = TabAdapter(childFragmentManager)
        adapter.addFragment(NotificationUnreadFragment(), "Unread")
        adapter.addFragment(NotificationParticipatingFragment(), "Participating")
        adapter.addFragment(NotificationAllFragment(), "All")

        viewPager.adapter = adapter

    }

}