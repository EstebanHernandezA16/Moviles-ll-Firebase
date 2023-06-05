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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MatriculaActivity extends AppCompatActivity {


    EditText etEnrollmentCode,etIdCardStudent, etCourseCode;
    CheckBox checkBoxEnr;

    TextView etStudentFullName,etCourseName;

    String EnrCode, IdCard,CourseCode,StudentFullname, CourseName, StudentId, CourseId;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matricula);
        getSupportActionBar().hide();

        etEnrollmentCode= findViewById(R.id.etenrollmentCode);
        etIdCardStudent= findViewById(R.id.etIdCardStudent);
        etCourseCode= findViewById(R.id.etCourseCode);
        etStudentFullName= findViewById(R.id.etStudentFullName);
        etCourseName= findViewById(R.id.etCourseName);

    }

    public void SearchIds(){
    IdCard = etIdCardStudent.getText().toString();
    CourseCode = etCourseCode.getText().toString();
       if(IdCard.isEmpty() || CourseCode.isEmpty()){
        Toast.makeText(this, "Please provide an IdCard and  A Course Code to search", Toast.LENGTH_SHORT).show();
        etIdCardStudent.requestFocus();
        etCourseCode.requestFocus();
       }else{
            db.collection("Students")
                    .whereEqualTo("idCard",IdCard)
                    //.whereEqualTo("State","Active")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                     StudentId= document.getId();
                                    etStudentFullName.setText(document.getString("FullName"));
                                    Toast.makeText(MatriculaActivity.this, "Student Founded!!", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                                Toast.makeText(MatriculaActivity.this, "Student not founded", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            //falta consulta Course
     }
    }


}