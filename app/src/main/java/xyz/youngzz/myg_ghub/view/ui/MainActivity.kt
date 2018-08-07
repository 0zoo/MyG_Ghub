package xyz.youngzz.myg_ghub.view.ui

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import timber.log.Timber
import xyz.youngzz.myg_ghub.R
import xyz.youngzz.myg_ghub.api.model.User
import xyz.youngzz.myg_ghub.api.provideGithubApi
import xyz.youngzz.myg_ghub.utils.enqueue
import xyz.youngzz.myg_ghub.view.ui.fragment.*


class MainActivity : AppCompatActivity() {

    private var content: FrameLayout? = null
    private lateinit var user: User
    private lateinit var fragments : List<Fragment>

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigationNews -> {
                val fragment = FragmentNews.newInstance(user.login)
                addFragment(fragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigationSearch -> {
                val fragment = FragmentSearch()
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


        provideGithubApi(this).getCurrentUser().enqueue({ response ->
            val statusCode = response.code()
            if (statusCode == 200) {
                val result = response.body()
                result?.let {
                   Timber.i(it.toString())
                    user = it
                }

            } else {
                Timber.e("error - $statusCode")
            }

        }, { t ->
            Timber.e(t.localizedMessage)
        })



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



