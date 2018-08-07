package xyz.youngzz.myg_ghub.api.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ReceivedEventsResponse (
        val id: String,
        val type : String,
        val actor : User,
        val repo: Repo,
        @field:SerializedName("created_at")
        val createdAt: String
)

