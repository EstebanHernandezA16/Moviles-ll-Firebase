package com.example.momento2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterEnrollment extends RecyclerView.Adapter<AdapterEnrollment.enrollmentViewHolder> {

    ArrayList<ClsEnrollment> enrollmentList;

    public AdapterEnrollment(ArrayList<ClsEnrollment> enrollmentList) {
        this.enrollmentList = enrollmentList;
    }

    @NonNull
    @Override
    public enrollmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.enrollmentresource, parent, false);
        return new enrollmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull enrollmentViewHolder holder, int position) {
        holder.tvenrollmentCode.setText(enrollmentList.get(position).getEnrollmentCode());
        holder.tvclassid.setText(enrollmentList.get(position).getCourseCode());
        holder.tvclass.setText(enrollmentList.get(position).getFullName());
        holder.tvstudentid.setText(enrollmentList.get(position).getIdCard());
        holder.tvstudentname.setText(enrollmentList.get(position).getFullName());
        if (enrollmentList.get(position).getState().equals("Yes")) {
            holder.cbactive.setChecked(true);
        } else {
            holder.cbactive.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return enrollmentList.size();
    }

    public static class enrollmentViewHolder extends RecyclerView.ViewHolder {
        TextView tvenrollmentCode, tvstudentname, tvclass, tvclassid, tvstudentid;
        CheckBox cbactive;

        public enrollmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvenrollmentCode = itemView.findViewById(R.id.etenrollmentCode);
            tvstudentname = itemView.findViewById(R.id.etStudentFullName);
            tvclassid = itemView.findViewById(R.id.etCourseCode);
            tvclass = itemView.findViewById(R.id.etCourseName);
            tvstudentid = itemView.findViewById(R.id.etIdCardStudent);
            cbactive = itemView.findViewById(R.id.checkBoxEnr);
        }
    }
}
