package xyz.youngzz.myg_ghub.utils

import android.annotation.SuppressLint
import android.content.Context
import android.support.design.internal.BottomNavigationItemView
import android.support.design.internal.BottomNavigationMenuView
import android.support.design.widget.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import xyz.youngzz.myg_ghub.R
import java.text.SimpleDateFormat
import java.util.*

fun <T> Call<T>.enqueue(success: (response: Response<T>) -> Unit, failure: (t: Throwable) -> Unit) {
    enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) = success(response)
        override fun onFailure(call: Call<T>, t: Throwable) = failure(t)
    })
}

@SuppressLint("RestrictedApi")
fun BottomNavigationView.disableShiftMode() {
    val menuView = getChildAt(0) as BottomNavigationMenuView
    try {
        val shiftingMode = menuView::class.java.getDeclaredField("mShiftingMode")
        shiftingMode.isAccessible = true
        shiftingMode.setBoolean(menuView, false)
        shiftingMode.isAccessible = false
        for (i in 0 until menuView.childCount) {
            val item = menuView.getChildAt(i) as BottomNavigationItemView
            item.setShiftingMode(false)
            // set once again checked value, so view will be updated
            item.setChecked(item.itemData.isChecked)
        }
    } catch (e: NoSuchFieldException) {
        Timber.tag("ERROR NO SUCH FIELD").e("Unable to get shift mode field")
    } catch (e: IllegalStateException) {
        Timber.tag("ERROR ILLEGAL ALG").e("Unable to change value of shift mode")
    }
}

fun String.convertEventType(ctx: Context): String {
    when (this) {
        "CreateEvent" -> return ctx.getString(R.string.created)
        "WatchEvent" -> return ctx.getString(R.string.starred)
        "ForkEvent" -> return ctx.getString(R.string.fork)
        "PushEvent" -> return ctx.getString(R.string.push)
    }
    return this
}

fun String.getDateFromISO(): String {
    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    dateFormat.timeZone = TimeZone.getTimeZone("UTC")

    val millis = Date().time - dateFormat.parse(this).time

    val sec = millis / 1000
    val min = millis / (60 * 1000)
    val hour = millis / (60 * 60 * 1000)
    val day = millis / (24 * 60 * 60 * 1000)
    val week = millis / (7 * 24 * 60 * 60 * 1000)
    val month = (millis / (30.5 * 24 * 60 * 60 * 1000)).toInt()
    //val year = day / 365

    if (day >= 365) {
        return "${(day / 365)}년 전"
    } else if (month > 0) {
        return "${month}달 전"
    } else if (week > 0){
        return "${week}주 전"
    }else if (day > 0){
        return "${day}일 전"
    }else if (hour > 0){
        return "${hour}시간 전"
    }else if (min > 0){
        return "${min}분 전"
    }else {
        return "${sec}초 전"
    }

}


















