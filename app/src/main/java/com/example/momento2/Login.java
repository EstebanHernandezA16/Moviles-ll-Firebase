package com.example.momento2;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Login extends AppCompatActivity {

    EditText Vuser, Vpass;

    String user, pass;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getSupportActionBar().hide();
        Vuser = findViewById(R.id.etUserId);
        Vpass = findViewById(R.id.etPassword);
    }

    public void LogIn (View view){
        user = Vuser.getText().toString();
        pass = Vpass.getText().toString();

        if(!user.isEmpty() && !pass.isEmpty()){

            db.collection("Users")
                    .whereEqualTo("IdUser",user)
                    .whereEqualTo("Password",pass)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                if(task.getResult().isEmpty()){
                                    Toast.makeText(Login.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                                }else{
                                    OpenMain(view);
                                }

                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                                Toast.makeText(Login.this, "Error on firestore Database", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Error: " + e.getMessage());
                            Toast.makeText(Login.this, "Error on Firestore Database: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });




        }else{
            Toast.makeText(this, "User and Password are required to logIn, please provide both", Toast.LENGTH_SHORT).show();
            Vuser.requestFocus();
            Vpass.requestFocus();
        }

    }


    public void OpenMain(View view){
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }





}