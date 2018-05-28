package com.example.student.bricklist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView projectList;
    private Button newProject;
    private ProjectAdapter projectAdapter;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case Globals.NEW_PROJECT_REQUEST_CODE :
                if(resultCode == Activity.RESULT_OK){
                    Project newProject = ProjectActivity.getProject();
                }
                break;
            case Globals.EDIT_PROJECT_REQUEST_CODE:
                break;
        }
    }//onActivityResult

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        projectList = findViewById(R.id.projectsList);
        newProject = findViewById(R.id.newProject);

        final List<Project> projectsList = new ArrayList<>();
        projectsList.add(new Project(0, "pierwszy projekt"));
        projectsList.add(new Project(0, "drugi projekt"));
        projectAdapter = new ProjectAdapter(this, projectsList);

        projectList.setAdapter(projectAdapter);

        final Intent intent = new Intent(this, ProjectActivity.class);

        newProject.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                startActivityForResult(intent, Globals.NEW_PROJECT_REQUEST_CODE);
            }
        });
    }
}
