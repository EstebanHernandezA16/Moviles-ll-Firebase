package com.example.momento2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.firestore.FirebaseFirestore;

public class MateriaActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_materia);
        getSupportActionBar().hide();
    }

    public void Add(View view){

    }

    public void BackToMain(View view){
        Intent intMain = new Intent(this, MainActivity.class);
        startActivity(intMain);
    }
}