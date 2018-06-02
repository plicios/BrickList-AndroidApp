package com.piciu.bricklist

import android.graphics.Bitmap

class Brick{
    constructor(typeId: Int, itemId: String, quantityInSet: Int, quantityInStore: Int, colorId: Int, extra: String){
        this.typeId = typeId
        this.itemId = itemId
        this.quantityInSet = quantityInSet
        this.quantityInStore = quantityInStore
        this.colorId = colorId
        this.extra = extra
        this.image = ImagesHelper.getImage(itemId, colorId)
        this.colorName = if (colorId != 0) DataBaseHelper(CustomApplication.context!!).getColorName(colorId) else ""
    }
    val typeId: Int
    val itemId: String
    val quantityInSet: Int
    var quantityInStore: Int
    val colorId: Int
    val extra: String
    val image: Bitmap
    val colorName: String
}