package com.piciu.bricklist

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.app.ProgressDialog
import kotlin.concurrent.thread


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

        val prefs: SharedPreferences = getSharedPreferences(Globals.PREFS_FILENAME, 0)
        var urlPath = prefs.getString(Globals.ADDRESS_URL, "http://fcds.cs.put.poznan.pl/MyWeb/BL/")
        if(!urlPath.last().equals('/', true)){
            urlPath += '/'
        }
        Globals.LEGOSETURL = urlPath

        newProject.setOnClickListener {
            val intent = Intent(this, NewProjectActivity::class.java)
            startActivityForResult(intent, Globals.NEW_PROJECT_REQUEST_CODE)
        }

        settings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }

        val nDialog = ProgressDialog(this)
        nDialog.setMessage("Pobieranie projektów i obrazków")
        nDialog.setTitle("Trwa wczytywanie")
        nDialog.isIndeterminate = false
        nDialog.setCancelable(false)
        nDialog.show()
        thread {
            projectAdapter = ProjectAdapter(this)
            runOnUiThread {
                projectList.adapter = projectAdapter
                nDialog.dismiss()
            }
        }
    }
}
