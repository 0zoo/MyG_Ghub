package xyz.youngzz.myg_ghub.view.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import org.jetbrains.anko.*

class RepoListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RepoListActivityUI().setContentView(this)
    }


}

class RepoListActivityUI : AnkoComponent<RepoListActivity>{
    override fun createView(ui: AnkoContext<RepoListActivity>) = with(ui){
        verticalLayout {
            toolbar {

            }


        }

    }

}
