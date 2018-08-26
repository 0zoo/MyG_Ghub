package xyz.youngzz.myg_ghub.view.ui

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.customtabs.CustomTabsIntent
import kotlinx.android.synthetic.main.activity_sign_in.*
import android.support.v4.content.ContextCompat
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast
import timber.log.Timber
import xyz.youngzz.myg_ghub.R
import xyz.youngzz.myg_ghub.api.authApi
import xyz.youngzz.myg_ghub.api.getToken
import xyz.youngzz.myg_ghub.api.updateToken
import xyz.youngzz.myg_ghub.utils.enqueue


class SignInActivity : AppCompatActivity() {

    companion object {
        val TAG = SignInActivity::class.java.simpleName

        const val CLIENT_ID = "2665b3d14a0c0fb47d25"
        const val CLIENT_SECRET = "1fb2914898c25210864d30120c30cb71bc49d33d"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        getToken(this)?.let{
            startActivity<MainActivity>()
        }

        signInButton.setOnClickListener {
            val authUri = Uri.Builder().scheme("https")
                    .authority("github.com")
                    .appendPath("login")
                    .appendPath("oauth")
                    .appendPath("authorize")
                    .appendQueryParameter("client_id", CLIENT_ID)
                    .build()

            val builder = CustomTabsIntent.Builder()
                    .setToolbarColor(ContextCompat.getColor(this, R.color.colorPrimaryDark))
                    .addDefaultShareMenuItem()

            val customTabsIntent = builder.build()

            customTabsIntent.launchUrl(this, authUri)

        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        check(intent != null)
        check(intent?.data != null)

        val uri = intent?.data
        val code = uri?.getQueryParameter("code") ?: throw IllegalStateException("no code!!")

        getAccessToken(code)

    }

    private fun getAccessToken(code: String) {
        val call = authApi.getAccessToken(CLIENT_ID, CLIENT_SECRET, code)

        call.enqueue({ response ->
            response.body()?.let {

                updateToken(this, it.accessToken)

                Timber.i( it.toString())

                startActivity<MainActivity>()

            }
        }, {
            toast(it.message.toString())
        })


    }
}
