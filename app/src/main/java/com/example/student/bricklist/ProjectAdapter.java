package com.example.student.bricklist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by student on 28.05.2018.
 */

public class ProjectAdapter extends BaseAdapter {

    List<Project> projectList;
    Context context;

    public ProjectAdapter(Context context, List<Project> projectList){
        this.projectList = projectList;
        this.context = context;
    }

    public void changeDataSet(List<Project> projectList){
        this.projectList = projectList;
        notifyDataSetChanged();
    }

    public void addNewProject(Project project){
        this.projectList.add(project);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return projectList.size();
    }

    @Override
    public Project getItem(int i) {
        return projectList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return projectList.get(i).getId();
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View row = view;
        if(view == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(context);
            row = vi.inflate(R.layout.project_row_layout, null);
        }

        TextView projectName = row.findViewById(R.id.projectName);
        ImageView deleteProject = row.findViewById(R.id.deleteProject);
        ImageView archiveProject = row.findViewById(R.id.archiveProject);

        final Project project = projectList.get(i);

        projectName.setText(project.getName());

        deleteProject.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                projectList.remove(project);
                notifyDataSetChanged();
            }
        });

        archiveProject.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Toast.makeText(context, "Project archived", Toast.LENGTH_LONG).show();
            }
        });

        projectName.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Toast.makeText(context, "Project opened", Toast.LENGTH_LONG).show();
            }
        });

        return row;
    }
}
