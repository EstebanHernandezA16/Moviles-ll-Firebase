package com.example.momento2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void ModalEstudiantes(View view){
        Intent intEstudiante = new Intent(this,EstudianteActivity.class);
        startActivity(intEstudiante);
    }

    public void ModalMatricula(View view){
        Intent intMatricula = new Intent(this, MatriculaActivity.class);
    }

    public void ModalMateria(View view){
        Intent intMateria = new Intent(this, MatriculaActivity.class);
    }
}