package pl.piotrgorny.bricklist.data.api

import com.google.gson.annotations.SerializedName

data class SetResult(
    @SerializedName("set_img_url")
    val imageUrl: String,
    @SerializedName("name")
    val name: String
)
