package com.piciu.bricklist

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new_project.*
import kotlin.concurrent.thread


class NewProjectActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_project)

        addNewProject.setOnClickListener {

            val nDialog = ProgressDialog(this)
            nDialog.setMessage("Pobieranie projektu oraz obrazków")
            nDialog.setTitle("Trwa wczytywanie")
            nDialog.isIndeterminate = false
            nDialog.setCancelable(false)
            nDialog.show()
            thread {

                val newProject = Project(newProjectName.text.toString(), newProjectUrl.text.toString().toInt())
                project = newProject

                val returnIntent = Intent()
                setResult(Activity.RESULT_OK, returnIntent)
                runOnUiThread {
                    nDialog.dismiss()
                    finish()
                }
            }
        }
    }

    companion object {
        var project: Project? = null
            private set
    }
}
