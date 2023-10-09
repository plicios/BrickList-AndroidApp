package pl.piotrgorny.bricklist.data.api

import com.google.gson.annotations.SerializedName

data class MinifigResult(
    @SerializedName("set_num")
    val id: String
)
