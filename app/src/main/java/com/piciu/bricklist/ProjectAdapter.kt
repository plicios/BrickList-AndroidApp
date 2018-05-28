package com.piciu.bricklist

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types


class ProjectAdapter : BaseAdapter {

    private val context: Context
    private val projectList: ArrayList<Project>
    private var prefs: SharedPreferences? = null
    private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val listMyData = Types.newParameterizedType(ArrayList::class.java, Project::class.java)
    private val issueAdapter = moshi.adapter<ArrayList<Project>>(listMyData)


    constructor(context: Context) : super() {
        this.context = context

        prefs = context.getSharedPreferences(Globals.PREFS_FILENAME, 0)
        val projectListJson: String = prefs!!.getString(Globals.PROJECTS_LIST,"[]")

        val newProjectList: ArrayList<Project>? = issueAdapter.fromJson(projectListJson)
        if(newProjectList != null) {
            this.projectList = newProjectList
        }
        else{
            this.projectList = ArrayList()
        }
    }

    fun addNewProject(project: Project) {
        this.projectList.add(project)
        notifyDataSetChanged()

        val projectsListJson: String = issueAdapter.toJson(projectList)

        val editor = prefs!!.edit()
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

            val projectsListJson: String = issueAdapter.toJson(projectList)

            val editor = prefs!!.edit()
            editor.putString(Globals.PROJECTS_LIST, projectsListJson)
            editor.apply()
        }

        archiveProject.setOnClickListener {
            Toast.makeText(context, "Project archived", Toast.LENGTH_LONG).show()

            val projectsListJson: String = issueAdapter.toJson(projectList)

            val editor = prefs!!.edit()
            editor.putString(Globals.PROJECTS_LIST, projectsListJson)
            editor.apply()
        }

        projectName.setOnClickListener {
            Toast.makeText(context, "Project opened", Toast.LENGTH_LONG).show()
        }

        return row
    }
}
