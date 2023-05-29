package com.example.momento2;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MateriaActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText VetcodeCourse, VetcourseName, VetcourseDuration, VetcourseShortening,VetcourseClassroom;

    CheckBox Vactive;

    String idCourse, courseName, courseDuration, courseShortening, courseClassroom, idDocument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia);
        getSupportActionBar().hide();
        VetcodeCourse = findViewById(R.id.etcodeCourse);
        VetcourseName = findViewById(R.id.etcourseName);
        VetcourseDuration = findViewById(R.id.etcourseDuration);
        VetcourseShortening = findViewById(R.id.etcourseShortening);
        VetcourseClassroom = findViewById(R.id.etcourseClassroom);
        Vactive = findViewById(R.id.cbActive);
    }

    public void AddCourse(View view){
        idCourse = VetcodeCourse.getText().toString();
        courseName = VetcourseName.getText().toString();
        courseDuration = VetcourseDuration.getText().toString();
        courseShortening = VetcourseShortening.getText().toString();
        courseClassroom = VetcourseClassroom.getText().toString();
        if(!idCourse.isEmpty() || idCourse!=""){
            Map<String, Object> Course = new HashMap<>();
            Course.put("IdCourse",idCourse);
            Course.put("Name", courseName);
            Course.put("Semesters", courseDuration);
            Course.put("Shortening", courseShortening);
            Course.put("Classroom", courseClassroom);
            if(Vactive.isChecked()==true){
                Course.put("State", "Active");
            }else if(Vactive.isChecked()==false){
                Course.put("State", "Inactive");
            }else{
                Toast.makeText(this, "Error on Check on Course module, maybe is missing?", Toast.LENGTH_SHORT).show();
            }
            db.collection("Courses")
                    .add(Course)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            // Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            Toast.makeText(MateriaActivity.this, "Form submitted successfully", Toast.LENGTH_SHORT).show();
                            ClearFields();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Log.w(TAG, "Error adding document", e);
                            Toast.makeText(MateriaActivity.this, "Error on submit Form", Toast.LENGTH_SHORT).show();
                        }
                    });

        }else{
            Toast.makeText(this, "All the fields are required", Toast.LENGTH_SHORT).show();
        }

    }

    public void SearchCourse(View view){
        idCourse = VetcodeCourse.getText().toString();
        if(!idCourse.isEmpty() || idCourse!=""){
        db.collection("Courses")
                .whereEqualTo("IdCourse",idCourse)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                idDocument  = document.getId();
                                VetcourseName.setText(document.getString("Name"));
                                VetcourseDuration.setText(document.getString("Semesters"));
                                VetcourseShortening.setText(document.getString("Shortening"));
                                VetcourseClassroom.setText(document.getString("Classroom"));

                                if(document.getString("State").equals("Active")){
                                    Vactive.setChecked(true);
                                }else{
                                    Vactive.setChecked(false);

                                }
                                Toast.makeText(MateriaActivity.this, "Course finded!!!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Log.w(TAG, "Error getting documents.", task.getException());
                            Toast.makeText(MateriaActivity.this, "Course doesnt exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


        }else{
            Toast.makeText(this, "Please provide an idCourse to continue", Toast.LENGTH_SHORT).show();
            VetcodeCourse.requestFocus();
        }

    }


    public void ModifyCourse(View view){
        if(!idDocument.equals("") || idDocument.isEmpty()){
            idCourse= VetcodeCourse.getText().toString();
            courseName = VetcourseName.getText().toString();
            courseDuration = VetcourseDuration.getText().toString();
            courseShortening = VetcourseShortening.getText().toString();
            courseClassroom = VetcourseClassroom.getText().toString();
            if(!idCourse.isEmpty() || !courseName.isEmpty() || !courseDuration.isEmpty() || !courseDuration.isEmpty() || !courseShortening.isEmpty() || !courseClassroom.isEmpty()){
                Map<String, Object> Course = new HashMap<>();
                Course.put("IdCourse",idCourse);
                Course.put("Name", courseName);
                Course.put("Semesters", courseDuration);
                Course.put("Shortening", courseShortening);
                Course.put("Classroom", courseClassroom);
                if(Vactive.isChecked()==true){
                    Course.put("State","Active");
                }else{
                    Course.put("State", "Inactive");
                }
                db.collection("Courses")
                        .document(idDocument).set(Course)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MateriaActivity.this, "Course updated Successfully", Toast.LENGTH_SHORT).show();
                                ClearFields();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MateriaActivity.this, "Course could not be updated....", Toast.LENGTH_SHORT).show();
                            }
                        });
            }else{
                Toast.makeText(this, "All the fields are required to Modify a Course", Toast.LENGTH_SHORT).show();
            }


        }else{
            Toast.makeText(this, "Please Search for a Course first before Modify", Toast.LENGTH_SHORT).show();
        }

    }

    public void DeleteCourse(View view){
        if(!idDocument.isEmpty() || idDocument!=""){
            db.collection("Courses")
                    .document(idDocument)
                    .delete()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            ClearFields();
                            Toast.makeText(MateriaActivity.this,"Course Deteled Successfully",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MateriaActivity.this,"Error on delete Course",Toast.LENGTH_SHORT).show();
                        }
                    });
        }else{
            Toast.makeText(this, "Please Search for a Course first before delete", Toast.LENGTH_SHORT).show();
        }

    }

    public void EnableCourse(View view){
        if(!idDocument.isEmpty() || idDocument!=""){
            idCourse = VetcodeCourse.getText().toString();
            if(!idCourse.isEmpty() || idCourse!="" ){
                Map<String, Object> Student = new HashMap<>();
                //consultar primero el estado del estudiante
                Student.put("State","Active");
                db.collection("Courses")
                        .document(idDocument).update(Student)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MateriaActivity.this, "Course Enabled Successfully", Toast.LENGTH_SHORT).show();
                                ClearFields();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MateriaActivity.this, "Course could not be Enabled....", Toast.LENGTH_SHORT).show();
                            }
                        });

            }else{
                Toast.makeText(this, "Please provide an id to continue", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Search for an Course first before Enable it", Toast.LENGTH_SHORT).show();
        }

    }

    public void DisableCourse(View view){
        if(!idDocument.isEmpty() || idDocument!=""){
            idCourse = VetcodeCourse.getText().toString();
            if(!idCourse.isEmpty() || idCourse!="" ){
                Map<String, Object> Student = new HashMap<>();
                //consultar primero el estado del estudiante
                Student.put("State","Inactive");
                db.collection("Courses")
                        .document(idDocument).update(Student)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MateriaActivity.this, "Course Disabled Successfully", Toast.LENGTH_SHORT).show();
                                ClearFields();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MateriaActivity.this, "Course could not be Disabled....", Toast.LENGTH_SHORT).show();
                            }
                        });

            }else{
                Toast.makeText(this, "Please provide an id to continue", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Search for an Course first before Disable it", Toast.LENGTH_SHORT).show();
        }

    }

    public void ClearFields(){
       // VetcodeCourse.setText("");
        VetcourseName.setText("");
        VetcourseDuration.setText("");
        VetcourseShortening.setText("");
        VetcourseClassroom.setText("");
        Vactive.setChecked(false);
    }
    public void BackToMain(View view){
        Intent intMain = new Intent(this, MainActivity.class);
        startActivity(intMain);
    }

    public void get_Courses(View view){
        Intent intGetCourses = new Intent(this, get_courses.class);
        startActivity(intGetCourses);
    }
}