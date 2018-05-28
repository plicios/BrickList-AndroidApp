package com.piciu.bricklist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_project.*

class ProjectActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project)

        addNewProject.setOnClickListener {
            val newProject = Project(0, newProjectName.text.toString())
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
