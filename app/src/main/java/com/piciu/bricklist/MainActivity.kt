package com.piciu.bricklist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {
    
    private var projectAdapter: ProjectAdapter? = null

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {

        when (requestCode) {
            Globals.NEW_PROJECT_REQUEST_CODE -> if (resultCode == Activity.RESULT_OK) {
                val newProject = NewProjectActivity.project
                if(newProject != null) {
                    projectAdapter?.addNewProject(newProject)
                }
            }
            Globals.EDIT_PROJECT_REQUEST_CODE -> {
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val projectsList = ArrayList<Project>()
        projectsList.add(Project(0, "pierwszy projekt", ArrayList()))
        projectsList.add(Project(0, "drugi projekt", ArrayList()))
        projectAdapter = ProjectAdapter(this)

        projectList.adapter = projectAdapter

        val intent = Intent(this, NewProjectActivity::class.java)

        newProject.setOnClickListener {
            startActivityForResult(intent, Globals.NEW_PROJECT_REQUEST_CODE)
        }
    }
}
