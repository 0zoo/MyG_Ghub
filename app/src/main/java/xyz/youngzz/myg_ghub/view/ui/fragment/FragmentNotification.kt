package xyz.youngzz.myg_ghub.view.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_notification.*
import kotlinx.android.synthetic.main.fragment_notification.view.*
import xyz.youngzz.myg_ghub.R

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
        val adapter = Adapter(childFragmentManager)
        adapter.addFragment(NotificationUnreadFragment(), "Unread")
        adapter.addFragment(NotificationParticipatingFragment(), "Participating")
        adapter.addFragment(NotificationAllFragment(), "All")

        viewPager.adapter = adapter

    }

    private class Adapter(manager: FragmentManager) : FragmentPagerAdapter(manager) {
        val fragments = mutableListOf<Fragment>()
        val titles = mutableListOf<String>()

        override fun getItem(position: Int): Fragment = fragments[position]

        override fun getCount(): Int = fragments.size

        override fun getPageTitle(position: Int): CharSequence? = titles[position]

        fun addFragment(fragment: Fragment, title: String) {
            fragments.add(fragment)
            titles.add(title)
        }
    }
}