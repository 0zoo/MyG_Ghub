package xyz.youngzz.myg_ghub

import android.app.Application
import timber.log.Timber

class GlobalApplication: Application(){
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}