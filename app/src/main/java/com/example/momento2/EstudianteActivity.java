package com.example.momento2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class EstudianteActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText VidCard, VfullName, Vmajor, Vsemester;
    CheckBox Vactive;
    String idCard, fullName, major, semester, active;

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


    public void Add(View view) {
        idCard = VidCard.getText().toString();
        fullName = VfullName.getText().toString();
        major = Vmajor.getText().toString();
        semester = Vsemester.getText().toString();
        active = Vactive.getText().toString();//Esto que me llega?

        // Create a new user with a first and last name
        Map<String, Object> Estudiante = new HashMap<>();
        Estudiante.put("idCard", idCard);
        Estudiante.put("FullName", fullName);
        Estudiante.put("Major", major);
        Estudiante.put("Semester", semester);

        if(Vactive.isChecked() == true){
            Estudiante.put("State","Active");
        }else if(Vactive.isChecked() == false){
            Estudiante.put("State", "Inactive");
        }else{
            Toast.makeText(this, "Please check Active CheckBox, missing Error", Toast.LENGTH_SHORT).show();
        }

        // Add a new document with a generated ID
        db.collection("Estudiantes")
                .add(Estudiante)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(EstudianteActivity.this, "Form submitted successfully", Toast.LENGTH_SHORT).show();
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

    public void Regresar(View view){
       Intent intMain = new Intent(this, MainActivity.class);
       startActivity(intMain);
    }




}