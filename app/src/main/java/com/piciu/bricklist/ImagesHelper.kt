package com.piciu.bricklist

import android.graphics.Bitmap.CompressFormat
import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import android.graphics.BitmapFactory
import kotlin.concurrent.thread
import android.graphics.Canvas
import android.graphics.Color
import java.io.InputStream
import java.net.URL


object ImagesHelper{
    private fun getBitmapAsByteArray(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(CompressFormat.PNG, 0, outputStream)
        return outputStream.toByteArray()
    }

    private fun insertImg(ItemID: String, color: Int, img: Bitmap) {
        val data = getBitmapAsByteArray(img) // this is a function
        val dbHelper = DataBaseHelper(CustomApplication.context!!)
        dbHelper.insertImageOld(ItemID, color, data)
    }

    private fun insertImg(code: Int, img: Bitmap) {
        val data = getBitmapAsByteArray(img) // this is a function
        val dbHelper = DataBaseHelper(CustomApplication.context!!)
        dbHelper.updateImage(code, data)
    }

    private fun downloadImage(ItemID: String, color: Int): Bitmap? {
        var bitmap: Bitmap? = null
        val downloadThread = thread {
            try {
                val url = URL("http://img.bricklink.com/P/$color/$ItemID.gif")
                bitmap = BitmapFactory.decodeStream(url.content as InputStream)
            }catch (e: Exception){}

            if(bitmap == null) {
                try {
                    val url = URL("http://img.bricklink.com/P/$color/$ItemID.jpg")
                    bitmap = BitmapFactory.decodeStream(url.content as InputStream)
                } catch (e: Exception) {
                }
            }

            if(bitmap == null) {
                try {
                    val url = URL("http://img.bricklink.com/P/$color/$ItemID.png")
                    bitmap = BitmapFactory.decodeStream(url.content as InputStream)
                } catch (e: Exception) {
                }
            }

            if(bitmap == null) {
                try {
                    val url = URL("http://bricklink.com/PL/$ItemID.gif")
                    bitmap = BitmapFactory.decodeStream(url.content as InputStream)
                } catch (e: Exception) {
                }
            }

            if(bitmap == null) {
                try {
                    val url = URL("http://bricklink.com/PL/$ItemID.jpg")
                    bitmap = BitmapFactory.decodeStream(url.content as InputStream)
                } catch (e: Exception) {
                }
            }

            if(bitmap == null) {
                try {
                    val url = URL("http://bricklink.com/PL/$ItemID.png")
                    bitmap = BitmapFactory.decodeStream(url.content as InputStream)
                } catch (e: Exception) {
                }
            }

            if(bitmap == null) {
                try {
                    val url = URL("https://img.bricklink.com/ItemImage/ML/$ItemID.gif")
                    bitmap = BitmapFactory.decodeStream(url.content as InputStream)
                } catch (e: Exception) {
                }
            }

            if(bitmap == null) {
                try {
                    val url = URL("https://img.bricklink.com/ItemImage/ML/$ItemID.jpg")
                    bitmap = BitmapFactory.decodeStream(url.content as InputStream)
                } catch (e: Exception) {
                }
            }

            if(bitmap == null) {
                try {
                    val url = URL("https://img.bricklink.com/ItemImage/ML/$ItemID.png")
                    bitmap = BitmapFactory.decodeStream(url.content as InputStream)
                } catch (e: Exception) {
                }
            }
        }
        downloadThread.join()

        return bitmap
    }

    private fun downloadImage(code: Int): Bitmap?{
        var bitmap: Bitmap? = null
        val downloadThread = thread {
            try {
                val url = URL("https://www.lego.com/service/bricks/5/2/$code")
                bitmap = BitmapFactory.decodeStream(url.content as InputStream)
            }catch (e: Exception){

            }
        }
        downloadThread.join()

        return bitmap
    }

    fun getImage(ItemID: String, color: Int): Bitmap{
        val dbHelper = DataBaseHelper(CustomApplication.context!!)

        val itemIdInt = ItemID.toIntOrNull()

        if(itemIdInt != null){
            val image = dbHelper.getImage(itemIdInt, color)
            if(image != null){
                if(image.imgData != null){
                    val options = BitmapFactory.Options()
                    return BitmapFactory.decodeByteArray(image.imgData, 0 , image.imgData.size, options)
                }
                else{
                    val bitmap = downloadImage(image.code)
                    if(bitmap != null) {
                        insertImg(image.code, bitmap)
                        return bitmap
                    }
                    else{
                        val bmp = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888)
                        val canvas = Canvas(bmp)
                        canvas.drawColor(Color.GREEN)
                        return bmp
                    }
                }
            }
        }
        val data = dbHelper.getImageOld(ItemID, color)
        if(data == null){
            val bitmap = downloadImage(ItemID, color)
            if(bitmap != null) {
                insertImg(ItemID, color, bitmap)
                return bitmap
            }
            else{
                val bmp = Bitmap.createBitmap(50, 50, Bitmap.Config.ARGB_8888)
                val canvas = Canvas(bmp)
                canvas.drawColor(Color.GREEN)
                return bmp
            }
        }
        else{
            val options = BitmapFactory.Options()
            return BitmapFactory.decodeByteArray(data, 0 , data.size, options)
        }
    }
}