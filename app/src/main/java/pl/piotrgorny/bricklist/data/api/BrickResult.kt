package pl.piotrgorny.bricklist.data.api

import com.google.gson.annotations.SerializedName

data class BrickResult(
    val id: Int,
    val quantity: Int,
    @SerializedName("element_id")
    val elementId: String,
    val part: PartResult,
    val color: ColorResult,
    @SerializedName("is_spare")
    val isSpare: Boolean
)