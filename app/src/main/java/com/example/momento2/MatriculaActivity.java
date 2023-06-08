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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MatriculaActivity extends AppCompatActivity {


    EditText etEnrollmentCode, etIdCardStudent, etCourseCode;
    CheckBox checkBoxEnr;

    TextView etStudentFullName, etCourseName;

    String EnrCode, IdCard, CourseCode, StudentFullname, CourseName, StudentId, CourseId;

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matricula);
        getSupportActionBar().hide();

        etEnrollmentCode = findViewById(R.id.etenrollmentCode);
        etIdCardStudent = findViewById(R.id.etIdCardStudent);
        etCourseCode = findViewById(R.id.etCourseCode);
        etStudentFullName = findViewById(R.id.etStudentFullName);
        etCourseName = findViewById(R.id.etCourseName);

    }

    public void AddEnr(View view) {
        EnrCode = etEnrollmentCode.getText().toString();
        CourseCode = etCourseCode.getText().toString();
        IdCard = etIdCardStudent.getText().toString();
        CourseName = etCourseName.getText().toString();
        StudentFullname = etStudentFullName.getText().toString();


        if (CourseCode.isEmpty() || IdCard.isEmpty() || CourseName.isEmpty() || StudentFullname.isEmpty()) {
            Toast.makeText(this, "All of the fields are required", Toast.LENGTH_SHORT).show();

        } else {
            Map<String, Object> enrollment = new HashMap<>();
            enrollment.put("enrollmentCode", EnrCode);
            enrollment.put("ClassCode", CourseCode);
            enrollment.put("IdCardStudent", IdCard);
            //enrollment.put("StudentFullname", StudentFullname);
            //enrollment.put("ClassName", CourseName);
            enrollment.put("State", "Active");

            db.collection("Enrollments")
                    .add(enrollment)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            Toast.makeText(MatriculaActivity.this, "Document Created Successfully", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Manejar el error de consulta
                            Log.e("Firebase", "Error al intentar ingresar el documento: " + e.getMessage());
                        }
                    });

        }



    }

    public void SearchIdsEnr(View view) {
        IdCard = etIdCardStudent.getText().toString();
        CourseCode = etCourseCode.getText().toString();
        if (IdCard.isEmpty() || CourseCode.isEmpty()) {
            Toast.makeText(this, "Please provide an IdCard and  A Course Code to search", Toast.LENGTH_SHORT).show();
            etIdCardStudent.requestFocus();
            etCourseCode.requestFocus();
        } else {

            db.collection("Students")
                    .whereEqualTo("idCard", IdCard)
                    .whereEqualTo("State","Active")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());

                                    IdCard = document.getId();
                                    etStudentFullName.setText(document.getString("FullName"));
                                    Toast.makeText(MatriculaActivity.this, "Student card Founded", Toast.LENGTH_SHORT).show();                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                                Toast.makeText(MatriculaActivity.this, "Student not founded", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }) .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Manejar el error de consulta
                            Log.e("Firebase", "Error al obtener el nombre del estudiante: " + e.getMessage());
                        }
                    });


            db.collection("Courses")
                    .whereEqualTo("IdCourse", CourseCode)
                    .whereEqualTo("State","Active")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Log.d(TAG, document.getId() + " => " + document.getData());
                                    CourseCode = document.getId();
                                    etCourseName.setText(document.getString("Name"));
                                   // Toast.makeText(MatriculaActivity.this, "Course Code Founded", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Log.w(TAG, "Error getting documents.", task.getException());
                                Toast.makeText(MatriculaActivity.this, "Code Founded", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void Search_All_Registers (View view){
        try{
            Intent RegistersView = new Intent(this, EnrrollmentListActivity.class);
            startActivity(RegistersView);
        }catch(Exception e){
            Log.e(TAG, "Error al iniciar el Intent: " + e.getMessage());
        }

    }



}