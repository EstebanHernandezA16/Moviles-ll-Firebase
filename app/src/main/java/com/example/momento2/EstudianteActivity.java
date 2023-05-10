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

public class EstudianteActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText VidCard, VfullName, Vmajor, Vsemester;
    CheckBox Vactive;
    String idCard, fullName, major, semester, active, idDocument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiante);
        getSupportActionBar().hide();

        VidCard = findViewById(R.id.etCarnet);
        VfullName = findViewById(R.id.etNombre);
        Vmajor = findViewById(R.id.etCarrera);
        Vsemester = findViewById(R.id.etSemestre);
        Vactive = findViewById(R.id.cbActivo);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
    }


    public void AddStudent(View view) {
        idCard = VidCard.getText().toString();
        fullName = VfullName.getText().toString();
        major = Vmajor.getText().toString();
        semester = Vsemester.getText().toString();
        active = Vactive.getText().toString();

        // Create a new user with a first and last name
        Map<String, Object> Student = new HashMap<>();
        Student.put("idCard", idCard);
        Student.put("FullName", fullName);
        Student.put("Major", major);
        Student.put("Semester", semester);


        if(Vactive.isChecked() == true){
            Student.put("State","Active");
        }else if(Vactive.isChecked() == false){
            Student.put("State", "Inactive");
        }else{
            Toast.makeText(this, "Error on checkbox, missing?", Toast.LENGTH_SHORT).show();
        }
        // Add a new document with a generated ID
        db.collection("Students")
                .add(Student)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(EstudianteActivity.this, "Form submitted successfully", Toast.LENGTH_SHORT).show();
                        Clear_fields();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Log.w(TAG, "Error adding document", e);
                        Toast.makeText(EstudianteActivity.this, "Error on submit Form", Toast.LENGTH_SHORT).show();
                    }
                });


    }//end Adicionar


    public void SearchStudent(View view){
        idCard = VidCard.getText().toString();
        if(!idCard.isEmpty() || idCard!=""){
        db.collection("Students")
                .whereEqualTo("idCard",idCard)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                idDocument  = document.getId();
                                VfullName.setText(document.getString("FullName"));
                                Vmajor.setText(document.getString("Major"));
                                Vsemester.setText(document.getString("Semester"));

                                if(document.getString("State").equals("Active")){
                                    Vactive.setChecked(true);
                                }else{
                                    Vactive.setChecked(false);

                                }
                                Toast.makeText(EstudianteActivity.this, "Student finded!!!", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                           // Log.w(TAG, "Error getting documents.", task.getException());
                            Toast.makeText(EstudianteActivity.this, "Student doesnt exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        }else{
            Toast.makeText(this, "Please provide an id for continue", Toast.LENGTH_SHORT).show();
            VidCard.requestFocus();
        }
    }


    public void ModifyStudent(View view){
        if(!idDocument.equals("") || !idDocument.isEmpty()){
            idCard= VidCard.getText().toString();
            fullName = VfullName.getText().toString();
            major = Vmajor.getText().toString();
            semester = Vsemester.getText().toString();

            if(idCard.isEmpty() || fullName.isEmpty() || major.isEmpty() || semester.isEmpty()){
                Toast.makeText(this, "All the fields are required to Modify", Toast.LENGTH_SHORT).show();
                VidCard.requestFocus();
            }else{
                Map<String, Object> Student = new HashMap<>();
                Student.put("idCard",idCard);
                Student.put("FullName",fullName);
                Student.put("Major",major);
                Student.put("Semester",semester);
                if(Vactive.isChecked() == true){
                    Student.put("State","Active");
                }else{
                    Student.put("State","Inactive");
                }

                db.collection("Students")
                        .document(idDocument).set(Student)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EstudianteActivity.this, "Student updated Successfully", Toast.LENGTH_SHORT).show();
                                Clear_fields();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EstudianteActivity.this, "Student could not be updated....", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }else{
            Toast.makeText(this, "Search for an Student first before Modify", Toast.LENGTH_SHORT).show();
        }
    }


    public void DeleteStudent(View view){
        if(!idDocument.equals("") || !idDocument.isEmpty()){
        db.collection("Students")
                .document(idDocument)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Clear_fields();
                        Toast.makeText(EstudianteActivity.this,"Student Deteled Successfully",Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EstudianteActivity.this,"Error on delete process",Toast.LENGTH_SHORT).show();
                    }
                });
        }else{
            Toast.makeText(this, "Please Search for an Student first before delete", Toast.LENGTH_SHORT).show();
        }

    }

    public void EnableStudent(View view){
    if(!idDocument.isEmpty() || idDocument!=""){
        idCard = VidCard.getText().toString();
        if(!idCard.isEmpty() || idCard!="" ){
            Map<String, Object> Student = new HashMap<>();
            //consultar primero el estado del estudiante
            Student.put("State","Active");
            db.collection("Students")
                    .document(idDocument).update(Student)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(EstudianteActivity.this, "Student Enabled Successfully", Toast.LENGTH_SHORT).show();
                            Clear_fields();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EstudianteActivity.this, "Student could not be Enabled....", Toast.LENGTH_SHORT).show();
                        }
                    });

        }else{
            Toast.makeText(this, "Please provide an id to coninue", Toast.LENGTH_SHORT).show();
        }

    }else{
        Toast.makeText(this, "Search for an Student first before Enable it", Toast.LENGTH_SHORT).show();
    }

    }



    public void DisableStudent(View view){
        if(!idDocument.isEmpty() || idDocument!=""){
            idCard = VidCard.getText().toString();
            if(!idCard.isEmpty() || idCard!="" ){
                Map<String, Object> Student = new HashMap<>();
                //consultar primero el estado del estudiante
                Student.put("State","Inactive");
                db.collection("Students")
                        .document(idDocument).update(Student)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(EstudianteActivity.this, "Student Disabled Successfully", Toast.LENGTH_SHORT).show();
                                Clear_fields();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(EstudianteActivity.this, "Student could not be Disabled....", Toast.LENGTH_SHORT).show();
                            }
                        });

            }else{
                Toast.makeText(this, "Please provide an id to coninue", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Search for an Student first before Enable it", Toast.LENGTH_SHORT).show();
        }
    }

    public void Regresar(View view){
       Intent intMain = new Intent(this, MainActivity.class);
       startActivity(intMain);
    }
    public void Clear_fields(){
        Vactive.setChecked(false);
        Vmajor.setText("");
        Vsemester.setText("");
        VfullName.setText("");
    }




}