package com.example.momento2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<DocumentSnapshot> dataList;

    public RecyclerViewAdapter(List<DocumentSnapshot> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DocumentSnapshot document = dataList.get(position);
        String idCourse = document.getString("IdCourse");
        String name = document.getString("Name");
        String state = document.getString("State");
        String classroom = document.getString("Classroom");
        String semesters = document.getString("Semesters");
        String shortening = document.getString("Shortening");

        holder.textViewIdCourse.setText(idCourse);
        holder.textViewName.setText(name);
        if(state == "Active"){
            holder.checkBoxState.setChecked(true);
        }else{
            holder.checkBoxState.setChecked(false);
        }
        holder.textViewClassroom.setText(classroom);
        holder.textViewSemesters.setText(semesters);
        holder.textViewShortening.setText(shortening);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewIdCourse;
        public TextView textViewName;
        public CheckBox checkBoxState;
        public TextView textViewClassroom;
        public TextView textViewSemesters;
        public TextView textViewShortening;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewIdCourse = itemView.findViewById(R.id.textViewCourseName);
            textViewName = itemView.findViewById(R.id.textViewCourseName);
            checkBoxState = itemView.findViewById(R.id.checkBoxCourseState);
            textViewClassroom = itemView.findViewById(R.id.textViewCourseClassroom);
            textViewSemesters = itemView.findViewById(R.id.textViewCourseSemesters);
            textViewShortening = itemView.findViewById(R.id.textViewCourseShortening);
        }
    }
}
