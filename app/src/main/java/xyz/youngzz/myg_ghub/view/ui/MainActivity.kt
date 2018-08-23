package xyz.youngzz.myg_ghub.view.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.widget.FrameLayout
import timber.log.Timber
import xyz.youngzz.myg_ghub.R
import xyz.youngzz.myg_ghub.api.model.User
import xyz.youngzz.myg_ghub.api.provideGithubApi
import xyz.youngzz.myg_ghub.utils.disableShiftMode
import xyz.youngzz.myg_ghub.utils.enqueue
import xyz.youngzz.myg_ghub.view.ui.fragment.FragmentNews
import xyz.youngzz.myg_ghub.view.ui.fragment.FragmentNotification
import xyz.youngzz.myg_ghub.view.ui.fragment.FragmentProfile
import xyz.youngzz.myg_ghub.view.ui.fragment.FragmentSearch


class MainActivity : AppCompatActivity() {

    private var content: FrameLayout? = null
    private lateinit var user: User

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigationNews -> {
                addFragment(FragmentNews.newInstance(user.login),FRAGMENT_TAGS[0])
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigationSearch -> {
                addFragment(FragmentSearch(),FRAGMENT_TAGS[1])
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigationNotification -> {
                addFragment(FragmentNotification(),FRAGMENT_TAGS[2])
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigationProfile -> {
                addFragment(FragmentProfile.newInstance(user),FRAGMENT_TAGS[3])
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    companion object {
        val TAG = MainActivity::class.java.simpleName
        val FRAGMENT_TAGS = listOf("NEWS","SEARCH","NOTIFICATION","PROFILE")
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

        addFragment(FragmentSearch(),FRAGMENT_TAGS[1])

        content = findViewById(R.id.content)
        val navigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        //navigation.disableShiftMode()

        navigation.getMenu().getItem(1).setChecked(true);


    }

    @SuppressLint("PrivateResource")
    private fun addFragment(fragment: Fragment, tag : String) {
        supportFragmentManager.inTransaction {
            setCustomAnimations(R.anim.design_bottom_sheet_slide_in, R.anim.design_bottom_sheet_slide_out)
            replace(R.id.content, fragment, tag)
        }


    }

}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}





