package com.jct.oshri.academyfirebaseapplication.controller;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.jct.oshri.academyfirebaseapplication.R;
import com.jct.oshri.academyfirebaseapplication.model.datasourse.Firebase_DBManager;
import com.jct.oshri.academyfirebaseapplication.model.entities.Student;

import java.util.List;

//import com.bumptech.glide.Glide;

public class StudentListActivity extends Activity {

    private RecyclerView studentsRecycleView;
    private List<Student> students;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_student);

        studentsRecycleView = findViewById(R.id.studentsRecycleView);
        studentsRecycleView.setHasFixedSize(true);
        studentsRecycleView.setLayoutManager(new LinearLayoutManager(this));

        Firebase_DBManager.notifyToStudentList(new Firebase_DBManager.NotifyDataChange<List<Student>>() {
            @Override
            public void OnDataChanged(List<Student> obj) {
                if (studentsRecycleView.getAdapter() == null) {
                    students = obj;
                    studentsRecycleView.setAdapter(new StudentsRecycleViewAdapter(StudentListActivity.this, students));
                } else
                    studentsRecycleView.getAdapter().notifyDataSetChanged();
            }

            @Override
            public void onFailure(Exception exception) {
                Toast.makeText(getBaseContext(), "error to get students list\n" + exception.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        Firebase_DBManager.stopNotifyToStudentList();
        super.onDestroy();
    }




}

