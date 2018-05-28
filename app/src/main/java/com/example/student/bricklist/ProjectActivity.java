package com.example.student.bricklist;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProjectActivity extends AppCompatActivity {

    private Button addNewProject;
    private TextView newProjectTitle;
    private EditText newProjectName;
    private EditText newProjectUrl;

    private static Project project;

    public static Project getProject(){
        return project;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        addNewProject = findViewById(R.id.addNewProject);
        newProjectTitle = findViewById(R.id.newProjectTitle);
        newProjectName = findViewById(R.id.newProjectName);
        newProjectUrl = findViewById(R.id.newProjectUrl);

        addNewProject.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Project newProject = new Project(0,newProjectName.getText().toString());
                project = newProject;
                Intent returnIntent = new Intent();
                setResult(Activity.RESULT_OK,returnIntent);
                finish();
            }
        });
    }
}
