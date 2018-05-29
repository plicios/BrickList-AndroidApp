package com.piciu.bricklist

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue


class ProjectAdapter(private val context: Context) : BaseAdapter() {

    private var prefs: SharedPreferences = context.getSharedPreferences(Globals.PREFS_FILENAME, 0)
    private val projectListJson: String = prefs.getString(Globals.PROJECTS_LIST,"[]")
    private val mapper = jacksonObjectMapper()
    val projectList: ArrayList<Project> = mapper.readValue(projectListJson)

    fun addNewProject(project: Project) {
        this.projectList.add(project)
        notifyDataSetChanged()

        val projectsListJson = mapper.writeValueAsString(projectList)

        val editor = prefs.edit()
        editor.putString(Globals.PROJECTS_LIST, projectsListJson)
        editor.apply()
    }

    override fun getCount(): Int {
        return projectList.size
    }

    override fun getItem(i: Int): Project {
        return projectList[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
        var row = view
        if (row == null) {
            val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            row = inflater.inflate(R.layout.project_row_layout, viewGroup, false)
        }

        val projectName = row!!.findViewById<TextView>(R.id.projectName)
        val deleteProject = row.findViewById<ImageView>(R.id.deleteProject)
        val archiveProject = row.findViewById<ImageView>(R.id.archiveProject)

        val project = projectList[i]

        projectName.text = project.name

        deleteProject.setOnClickListener {
            projectList.remove(project)
            notifyDataSetChanged()

            val projectsListJson = mapper.writeValueAsString(projectList)

            val editor = prefs.edit()
            editor.putString(Globals.PROJECTS_LIST, projectsListJson)
            editor.apply()
        }

        archiveProject.setOnClickListener {
            Toast.makeText(context, "Project archived", Toast.LENGTH_LONG).show()

            val projectsListJson = mapper.writeValueAsString(projectList)

            val editor = prefs.edit()
            editor.putString(Globals.PROJECTS_LIST, projectsListJson)
            editor.apply()
        }

        projectName.setOnClickListener {
            ProjectActivity.project = project
            val intent = Intent(context,ProjectActivity::class.java)
            context.startActivity(intent)
        }

        return row
    }
}
