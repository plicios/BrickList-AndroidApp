package com.piciu.bricklist

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class ProjectActivity : AppCompatActivity() {

    var adapter: BrickAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project)

        try {
            adapter = BrickAdapter(this, project!!.brickList)
        }catch (e: Exception){
            finish()
        }
    }

    companion object {
        var project: Project? = null
    }
}
