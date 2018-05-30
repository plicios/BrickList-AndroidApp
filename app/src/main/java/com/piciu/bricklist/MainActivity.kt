package com.piciu.bricklist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

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

        projectAdapter = ProjectAdapter(this)

        projectList.adapter = projectAdapter



        newProject.setOnClickListener {
            val intent = Intent(this, NewProjectActivity::class.java)
            startActivityForResult(intent, Globals.NEW_PROJECT_REQUEST_CODE)
        }

        settings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}
