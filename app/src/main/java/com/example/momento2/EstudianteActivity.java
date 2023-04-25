package com.example.momento2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

    EditText Vcarnet, Vnombre, Vcarrera, Vsemestre;
    CheckBox Vactivo;
    String carnet, nombre, carrera, semestre, activo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estudiante);
        Vcarnet = findViewById(R.id.etCarnet);
        Vnombre = findViewById(R.id.etNombre);
        Vcarrera = findViewById(R.id.etCarrera);
        Vsemestre = findViewById(R.id.etSemestre);
        Vactivo = findViewById(R.id.cbActivo);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
    }

    public void AdicionarEstudiante(View view) {
        carnet = Vcarnet.getText().toString();
        nombre = Vnombre.getText().toString();
        carrera = Vcarrera.getText().toString();
        semestre = Vsemestre.getText().toString();
        activo = Vactivo.getText().toString();//Esto que me llega?

        // Create a new user with a first and last name
        Map<String, Object> Estudiante = new HashMap<>();
        Estudiante.put("Primero", "Esteban");
        Estudiante.put("last", "Hernandez");
        Estudiante.put("born", 2003);

        // Add a new document with a generated ID
        db.collection("Estudiantes")
                .add(Estudiante)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        // Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                        Toast.makeText(EstudianteActivity.this, "Enviado form", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Log.w(TAG, "Error adding document", e);
                        Toast.makeText(EstudianteActivity.this, "No Enviado", Toast.LENGTH_SHORT).show();
                    }
                });


    }
}