package com.piciu.bricklist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast


class ProjectAdapter(private var context: Context, private var projectList: MutableList<Project>) : BaseAdapter() {

    fun changeDataSet(projectList: MutableList<Project>) {
        this.projectList = projectList
        notifyDataSetChanged()
    }

    fun addNewProject(project: Project) {
        this.projectList.add(project)
        notifyDataSetChanged()
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

        projectName.setText(project.name)

        deleteProject.setOnClickListener {
            projectList.remove(project)
            notifyDataSetChanged()
        }

        archiveProject.setOnClickListener {
            Toast.makeText(context, "Project archived", Toast.LENGTH_LONG).show()
        }

        projectName.setOnClickListener {
            Toast.makeText(context, "Project opened", Toast.LENGTH_LONG).show()
        }

        return row
    }
}
