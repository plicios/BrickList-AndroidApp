package pl.piotrgorny.bricklist.data.api

import com.google.gson.annotations.SerializedName

data class PartResult(
    @SerializedName("part_num")
    val partNumber: String,
    val name: String,
    @SerializedName("part_img_url")
    val imageUrl: String
)