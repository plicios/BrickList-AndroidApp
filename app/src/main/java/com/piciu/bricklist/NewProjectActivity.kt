package com.piciu.bricklist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new_project.*

class NewProjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_project)

        addNewProject.setOnClickListener {
            val brickList: ArrayList<Brick> = ArrayList()
            var brick = Brick(1,"brick","red",10,5)
            brickList.add(brick)
            val newProject = Project(0, newProjectName.text.toString(), brickList)
            project = newProject

            val returnIntent = Intent()
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }

    companion object {
        var project: Project? = null
            private set
    }
}
