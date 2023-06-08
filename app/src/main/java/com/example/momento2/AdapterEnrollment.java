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

    //ArrayList

    ArrayList<ClsEnrollment> enrollmentList;

    public AdapterEnrollment(ArrayList<ClsEnrollment> enrollmentList){
        this.enrollmentList = enrollmentList;
    }


    @NonNull
    @Override
    public AdapterEnrollment.enrollmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.enrollmentresource,null,false);
        return new AdapterEnrollment.enrollmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterEnrollment.enrollmentViewHolder holder, int position) {
        holder.tvenrollmentCode.setText(enrollmentList.get(position).getEnrollmentCode());
        holder.tvclassid.setText(enrollmentList.get(position).getClassCode());
        holder.tvclass.setText(enrollmentList.get(position).getClassname());
        holder.tvstudentid.setText(enrollmentList.get(position).getStudentCode());
        holder.tvstudentname.setText(enrollmentList.get(position).getStudentFullName());
        String enrollmentCheckBox = enrollmentList.get(position).getEnrollmentCheckBox();
        if (enrollmentCheckBox != null && enrollmentCheckBox.equals("Active")) {
            holder.cbactive.setChecked(true);
        } else {
            holder.cbactive.setChecked(false);
        }


    }

    @Override
    public int getItemCount() {
        return enrollmentList.size();
    }

    public static class  enrollmentViewHolder extends RecyclerView.ViewHolder{

        TextView tvenrollmentCode, tvstudentname, tvclass, tvclassid, tvstudentid;
        CheckBox cbactive;
        public enrollmentViewHolder(@NonNull View itemView) {
            super(itemView);
            tvenrollmentCode = itemView.findViewById(R.id.tvenrollmentCode);
            tvstudentname = itemView.findViewById(R.id.tvstudentname);
            tvclassid = itemView.findViewById(R.id.tvclassid);
            tvclass = itemView.findViewById(R.id.tvclass);
            tvstudentid = itemView.findViewById(R.id.tvstudentid);
            cbactive = itemView.findViewById(R.id.cbactive);
        }
    }
}