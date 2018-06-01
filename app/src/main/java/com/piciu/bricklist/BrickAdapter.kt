package com.piciu.bricklist

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.TextWatcher
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
        val brickCount: EditText = row.findViewById(R.id.brickCount)
        val brickCountNeeded: TextView = row.findViewById(R.id.brickCountNeeded)
        //val brickImage: ImageView = row.findViewById(R.id.brickImage)
        val background: LinearLayout = row.findViewById(R.id.background)
        val brickCountDown: Button = row.findViewById(R.id.brickCountDown)
        val brickCountUp: Button = row.findViewById(R.id.brickCountUp)


        val brick = brickList[i]

        brickName.text = brick.name
        brickCount.setText(brick.currentAmount.toString())
        brickCountNeeded.text = brick.amountNeeded.toString()
        //brickImage.setImageDrawable()

        brickCountUp.setOnClickListener{
            if(brick.currentAmount != brick.amountNeeded) {
                brickCount.setText((brick.currentAmount + 1).toString())
            }
        }

        brickCountDown.setOnClickListener{
            if(brick.currentAmount != 0) {
                brickCount.setText((brick.currentAmount - 1).toString())
            }
        }

        brickCount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(textAfterChange: Editable?) {
                val newCount = textAfterChange.toString().toIntOrNull()
                if(newCount != null && newCount <= brick.amountNeeded && newCount >= 0){
                    brick.currentAmount = newCount
                    background.setBackgroundColor(if (brick.currentAmount == brick.amountNeeded) Color.rgb(145,255,145) else Color.WHITE)
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
