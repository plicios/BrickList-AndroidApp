package com.piciu.bricklist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*


class BrickAdapter(private var context: Context, private var brickList: MutableList<Brick>) : BaseAdapter() {

    fun addNewBrick(brick: Brick) {
        this.brickList.add(brick)
        notifyDataSetChanged()
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

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var row = view
        if (row == null) {
            val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            row = inflater.inflate(R.layout.brick_row_layout, viewGroup, false)
        }

        val brickName: TextView = row!!.findViewById(R.id.brickName)
        val brickCount: TextView = row.findViewById(R.id.brickCount)
        val brickCountNeeded: TextView = row.findViewById(R.id.brickCountNeeded)
        //val brickImage: ImageView = row.findViewById(R.id.brickImage)
        val brickCountDown: Button = row.findViewById(R.id.brickCountDown)
        val brickCountUp: Button = row.findViewById(R.id.brickCountUp)


        val brick = brickList[i]

        brickName.text = brick.name
        brickCount.text = brick.currentAmount.toString()
        brickCountNeeded.text = brick.amountNeeded.toString()
        //brickImage.setImageDrawable()

        brickCountUp.setOnClickListener{
            brick.currentAmount += 1
            brickCount.text = brick.currentAmount.toString()
        }

        brickCountDown.setOnClickListener{
            brick.currentAmount -= 1
            brickCount.text = brick.currentAmount.toString()
        }

        return row
    }
}
