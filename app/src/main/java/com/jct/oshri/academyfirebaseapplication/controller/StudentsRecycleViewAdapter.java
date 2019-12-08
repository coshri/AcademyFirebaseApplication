package com.jct.oshri.academyfirebaseapplication.controller;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jct.oshri.academyfirebaseapplication.R;
import com.jct.oshri.academyfirebaseapplication.model.datasourse.Firebase_DBManager;
import com.jct.oshri.academyfirebaseapplication.model.entities.Student;

import java.util.List;

/**
 * Created by Yair on 08/12/2019.
 */

public class StudentsRecycleViewAdapter extends RecyclerView.Adapter<StudentsRecycleViewAdapter.StudentViewHolder> {

    private Context baseContext;
    List<Student> students;

    public StudentsRecycleViewAdapter(Context baseContext, List<Student> students) {
        this.students = students;
        this.baseContext = baseContext;
    }

    @Override
    public StudentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(baseContext.getApplicationContext() ).inflate(R.layout.student_item_view,
                parent,
                false);

        return new StudentViewHolder(v);
    }

    @Override
    public void onBindViewHolder(StudentViewHolder holder, int position) {

        Student student = students.get(position);
        holder.nameTextView.setText(student.getName());
        holder.phoneTextView.setText(student.getPhone());
        //Load the image using Glide
        Glide.with(baseContext.getApplicationContext())
                .load(student.getImageFirebaseUrl())
                .centerCrop()
                .override(150, 150)
                .placeholder(R.mipmap.person)
                .into(holder.personImageView);


        //            Glide.with(getBaseContext() /* context */)
        //                    .load(student.getImageFirebaseUrl())
        //                 //   .override(450,450)
        //                    .centerCrop()
        //                    .placeholder(R.mipmap.person)
        //                    .into(holder.personImageView);


    }

    @Override
    public int getItemCount() {
        return students.size();
    }

    class StudentViewHolder extends RecyclerView.ViewHolder {

        TextView nameTextView;
        TextView phoneTextView;
        ImageView personImageView;

        StudentViewHolder(View itemView) {
            super(itemView);
            personImageView = itemView.findViewById(R.id.personImageView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);

            // itemView.setOnClickListener();
            itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

                @Override
                public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
                    menu.setHeaderTitle("Select Action");

                    MenuItem delete = menu.add(Menu.NONE, 1, 1, "Delete");

                    delete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            int position = getAdapterPosition();
                            long id = students.get(position).getId();

                            Firebase_DBManager.removeStudent(id, new Firebase_DBManager.Action<Long>() {
                                @Override
                                public void onSuccess(Long obj) {

                                }

                                @Override
                                public void onFailure(Exception exception) {

                                }

                                @Override
                                public void onProgress(String status, double percent) {

                                }
                            });

                            return true;
                        }
                    });
                }
            });
        }
    }
}