package xyz.youngzz.myg_ghub.view.ui

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import timber.log.Timber
import xyz.youngzz.myg_ghub.R
import xyz.youngzz.myg_ghub.api.model.User
import xyz.youngzz.myg_ghub.api.provideGithubApi
import xyz.youngzz.myg_ghub.utils.addFragment
import xyz.youngzz.myg_ghub.utils.disableShiftMode
import xyz.youngzz.myg_ghub.utils.enqueue
import xyz.youngzz.myg_ghub.utils.setFragmentManager
import xyz.youngzz.myg_ghub.view.ui.fragment.FragmentNews
import xyz.youngzz.myg_ghub.view.ui.fragment.FragmentNotification
import xyz.youngzz.myg_ghub.view.ui.fragment.FragmentProfile
import xyz.youngzz.myg_ghub.view.ui.fragment.FragmentSearch


class MainActivity : AppCompatActivity() {

    private lateinit var user: User

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigationNews -> {
                FragmentNews.newInstance(user.login).addFragment(R.id.content,FRAGMENT_TAGS[0])
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigationSearch -> {
                FragmentSearch().addFragment(R.id.content,FRAGMENT_TAGS[1])
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigationNotification -> {
                FragmentNotification().addFragment(R.id.content,FRAGMENT_TAGS[2])
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigationProfile -> {
                FragmentProfile.newInstance(user,false).addFragment(R.id.content,FRAGMENT_TAGS[3])
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    companion object {
        val FRAGMENT_TAGS = listOf("NEWS","SEARCH","NOTIFICATION","PROFILE")
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        provideGithubApi(this).getCurrentUser().enqueue({ response ->
            val statusCode = response.code()
            if (statusCode == 200) {
                val result = response.body()
                result?.let { _user ->
                    user = _user
                    provideGithubApi(this).getStarredRepo(user.login).enqueue({ response ->
                        response.body()?.let {
                            user.starredCount = it.size
                        }
                    },{})

                    setupBottomNavigation()
                }

            } else {
                Timber.e("error - $statusCode")
            }

        }, { t ->
            Timber.e(t.localizedMessage)
        })




    }

    private fun setupBottomNavigation(){

        setFragmentManager(supportFragmentManager)

        FragmentNews.newInstance(user.login).addFragment(R.id.content,FRAGMENT_TAGS[0])

        val navigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        navigation.disableShiftMode()

        //navigation.getMenu().getItem(0).setChecked(true)
    }


}







