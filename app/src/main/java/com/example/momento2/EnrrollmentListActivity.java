package com.example.momento2;

import androidx.appcompat.app.AppCompatActivity;


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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import android.os.Bundle;

public class EnrrollmentListActivity extends AppCompatActivity {

    RecyclerView jrvenrollments;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    ArrayList<ClsEnrollment> arrayListsEnrollments;


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
        db.collection("Enrrollments")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                // Log.d(TAG, document.getId() + " => " + document.getData());
                                ClsEnrollment objenrollments = new ClsEnrollment();
                                objenrollments.setEnrollmentCode(document.getString("enrollmentCode"));
                                objenrollments.setCourseCode(document.getString("ClassCode"));
                                objenrollments.setName(document.getString("ClassName"));
                                objenrollments.setIdCard(document.getString("IdCardStudent"));
                                objenrollments.setFullName(document.getString("StudentFullname"));
                                objenrollments.setState(document.getString("State"));
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
        Intent intmain=new Intent(this,MainActivity.class);
        startActivity(intmain);
    }




}