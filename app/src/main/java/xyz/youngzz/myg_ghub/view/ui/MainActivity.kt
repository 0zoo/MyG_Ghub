package xyz.youngzz.myg_ghub.view.ui

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import xyz.youngzz.myg_ghub.R
import xyz.youngzz.myg_ghub.view.ui.fragment.*


class MainActivity : AppCompatActivity() {

    private var content: FrameLayout? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigationFeed -> {
                val fragment = FragmentFeed()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigationSearch -> {
                val fragment = FragmentSearch.newInstance()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigationNotification -> {
                val fragment = FragmentNotification()
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigationProfile -> {
                val fragment = FragmentProfile()
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


        val fragment = FragmentSearch.newInstance()
        addFragment(fragment)

        content = findViewById(R.id.content)
        val navigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        //navigation.disableShiftMode()

        navigation.getMenu().getItem(1).setChecked(true);


    }

    private fun addFragment(fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
                .replace(R.id.content, fragment, fragment.javaClass.simpleName)
                .commit()
    }

}



