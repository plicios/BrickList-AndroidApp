package com.piciu.bricklist

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*


class BrickAdapter(private var context: Context, private var brickList: MutableList<Brick>) : BaseAdapter() {

    private val allBrickList = brickList
    private var colorFilter = 0
    private var itemIdFilter: String? = null


    private fun sortData(){
        this.brickList.sortWith(compareBy({it.quantityInSet == it.quantityInStore}))
        notifyDataSetChanged()
    }

    fun setColorFilter(colorId: Int){
        colorFilter = colorId
        filter()
    }

    private fun filter(){
        var newBrickList = allBrickList
        if(this.colorFilter != -1){
            newBrickList = newBrickList.filter { b -> b.colorId == colorFilter } as MutableList<Brick>
        }
        if(this.itemIdFilter != null){
            newBrickList = newBrickList.filter { b -> b.itemId == itemIdFilter } as MutableList<Brick>
        }

        this.brickList = newBrickList
        sortData()
    }

    fun setItemIdFilter(itemID: String?){
        itemIdFilter = itemID
        filter()
    }

    override fun getCount(): Int {
        return brickList.size
    }

    override fun getItem(i: Int): Brick {
        return brickList[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    @SuppressLint("SetTextI18n")
    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
//        var row = view
//        if (row == null) {
//            val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
//            row = inflater.inflate(R.layout.brick_row_layout, viewGroup, false)
//        }

        val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var row = inflater.inflate(R.layout.brick_row_layout, viewGroup, false)

        val brickName: TextView = row!!.findViewById(R.id.brickName)
        val brickCount: EditText = row.findViewById(R.id.brickCount)
        val brickCountNeeded: TextView = row.findViewById(R.id.brickCountNeeded)
        val brickImage: ImageView = row.findViewById(R.id.brickImage)
        val background: LinearLayout = row.findViewById(R.id.background)
        val brickCountDown: Button = row.findViewById(R.id.brickCountDown)
        val brickCountUp: Button = row.findViewById(R.id.brickCountUp)


        val brick = brickList[i]

        background.setBackgroundColor(if (brick.quantityInStore == brick.quantityInSet) Color.rgb(145,255,145) else Color.WHITE)
        brickName.text = "${brick.itemId} ${brick.colorName}"
        brickCount.setText(brick.quantityInStore.toString())
        brickCountNeeded.text = brick.quantityInSet.toString()
        brickImage.setImageBitmap(brick.image)

        brickCountUp.setOnClickListener{
            if(brick.quantityInStore != brick.quantityInSet) {
                brickCount.setText((brick.quantityInStore + 1).toString())
            }
        }

        brickCountDown.setOnClickListener{
            if(brick.quantityInStore != 0) {
                brickCount.setText((brick.quantityInStore - 1).toString())
            }
        }

        brickCount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(textAfterChange: Editable?) {
                val newCount = textAfterChange.toString().toIntOrNull()
                if(newCount != null && newCount <= brick.quantityInSet && newCount >= 0){
                    brick.quantityInStore = newCount
                    background.setBackgroundColor(if (brick.quantityInStore == brick.quantityInSet) Color.rgb(145,255,145) else Color.WHITE)
                    sortData()
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })



        return row
    }
}
