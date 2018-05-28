package com.piciu.bricklist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import kotlinx.android.synthetic.main.activity_main.*


import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    
    private var projectAdapter: ProjectAdapter? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {

        when (requestCode) {
            Globals.NEW_PROJECT_REQUEST_CODE -> if (resultCode == Activity.RESULT_OK) {
                val newProject = ProjectActivity.project
            }
            Globals.EDIT_PROJECT_REQUEST_CODE -> {
            }
        }
    }//onActivityResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val projectsList = ArrayList<Project>()
        projectsList.add(Project(0, "pierwszy projekt"))
        projectsList.add(Project(0, "drugi projekt"))
        projectAdapter = ProjectAdapter(this, projectsList)

        //projectList.adapter = projectAdapter

        val intent = Intent(this, ProjectActivity::class.java)

        newProject!!.setOnClickListener { startActivityForResult(intent, Globals.NEW_PROJECT_REQUEST_CODE) }
    }
}
