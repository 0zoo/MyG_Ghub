package xyz.youngzz.myg_ghub.view.ui

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.support.v4.app.Fragment
import android.widget.FrameLayout
import xyz.youngzz.myg_ghub.R
import xyz.youngzz.myg_ghub.api.provideGithubApi
import xyz.youngzz.myg_ghub.utils.enqueue
import xyz.youngzz.myg_ghub.view.ui.fragment.FragmentSample
import xyz.youngzz.myg_ghub.view.ui.fragment.FragmentSearch
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import xyz.youngzz.myg_ghub.utils.disableShiftMode


class MainActivity : AppCompatActivity() {

    private var content: FrameLayout? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigationFeed -> {
                val fragment = FragmentSample()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigationSearch -> {
                val fragment = FragmentSearch.newInstance()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigationNotification -> {
                val fragment = FragmentSample()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigationProfile -> {
                val fragment = FragmentSample()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val githubApi = provideGithubApi(this)
        val call = githubApi.searchRepository("kotlin")

        call.enqueue({ response ->
            /*
            if (response.isSuccessful) {
                response.body()?.let {
                    listAdapter.items = it.items
                    listAdapter.notifyDataSetChanged()
                }

            } else {
                Log.i(TAG, "response code: ${response.code()}")
            }
            */

        }, {
            Log.e(TAG, it.localizedMessage)
        })



        content = findViewById(R.id.content)
        val navigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        navigation.disableShiftMode()

        val fragment = FragmentSearch.newInstance()
        addFragment(fragment)


    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
                .replace(R.id.content, fragment, fragment.javaClass.simpleName)
                .commit()
    }

}



