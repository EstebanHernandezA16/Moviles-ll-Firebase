package com.example.momento2;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.momento2.AdapterEnrollment;
import com.example.momento2.MainActivity;
import com.example.momento2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class EnrrollmentListActivity extends AppCompatActivity {

    RecyclerView jrvenrollments;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ArrayList<ClsEnrollment> arrayListsEnrollments;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enrrollment_list);

        // Hide the title bar by default, asociate objects
        // Java with xmlw objects and initialize the ArrayList

        getSupportActionBar().hide();
        jrvenrollments = findViewById(R.id.rvenrollments);
        arrayListsEnrollments = new ArrayList<>();

        // Error Handler
        jrvenrollments.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        jrvenrollments.setHasFixedSize(true);
        LoadData();
    }


    private void LoadData(){
        db.collection("Enrollments")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Log.d(TAG, document.getId() + " => " + document.getData());
                                ClsEnrollment objenrollments = new ClsEnrollment();
                                objenrollments.setEnrollmentCode(document.getString("enrollmentCode"));
                                objenrollments.setClassCode(document.getString("ClassCode"));
                                objenrollments.setClassname(document.getString("ClassName"));
                                objenrollments.setStudentCode(document.getString("StudentCode"));
                                objenrollments.setStudentFullName(document.getString("StudentFullName"));
                                objenrollments.setEnrollmentCheckBox(document.getString("EnrollmentCheckBox"));
                                arrayListsEnrollments.add(objenrollments);
                            }
                            AdapterEnrollment addEnrollment = new AdapterEnrollment(arrayListsEnrollments);
                            jrvenrollments.setAdapter(addEnrollment);
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });
    }

    public void Back(View view){
        Intent intmain=new Intent(this, MainActivity.class);
        startActivity(intmain);
    }
}