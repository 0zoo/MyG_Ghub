package xyz.youngzz.myg_ghub.view.ui

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import timber.log.Timber
import xyz.youngzz.myg_ghub.R
import xyz.youngzz.myg_ghub.api.model.User
import xyz.youngzz.myg_ghub.api.provideGithubApi
import xyz.youngzz.myg_ghub.utils.addFagment
import xyz.youngzz.myg_ghub.utils.enqueue
import xyz.youngzz.myg_ghub.view.ui.fragment.FragmentNews
import xyz.youngzz.myg_ghub.view.ui.fragment.FragmentNotification
import xyz.youngzz.myg_ghub.view.ui.fragment.FragmentProfile
import xyz.youngzz.myg_ghub.view.ui.fragment.FragmentSearch


class MainActivity : AppCompatActivity() {

    //private var content: FrameLayout? = null
    private lateinit var user: User

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigationNews -> {
                FragmentNews.newInstance(user.login).addFagment(R.id.content,FRAGMENT_TAGS[0],supportFragmentManager)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigationSearch -> {
                FragmentSearch().addFagment(R.id.content,FRAGMENT_TAGS[1],supportFragmentManager)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigationNotification -> {
                FragmentNotification().addFagment(R.id.content,FRAGMENT_TAGS[2],supportFragmentManager)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigationProfile -> {
                FragmentProfile.newInstance(user).addFagment(R.id.content,FRAGMENT_TAGS[3],supportFragmentManager)
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
                result?.let {
                   Timber.i(it.toString())
                    user = it

                    provideGithubApi(this).getStarredRepo(user.login).enqueue({ response ->
                        response.body()?.let {
                            user.starredCount = it.size
                        }
                    },{})

                    setupBottomNavi()
                }

            } else {
                Timber.e("error - $statusCode")
            }

        }, { t ->
            Timber.e(t.localizedMessage)
        })




    }

    private fun setupBottomNavi(){

        FragmentNews.newInstance(user.login).addFagment(R.id.content,FRAGMENT_TAGS[0],supportFragmentManager)


        //content = findViewById(R.id.content)
        val navigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        //navigation.disableShiftMode()

        //navigation.getMenu().getItem(0).setChecked(true)
    }


}







