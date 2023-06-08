package com.example.momento2;

public class ClsEnrollment {


    //Atributes


    private String classCode;
    private String classname;
    private String enrollmentCheckBox;
    private String studentCode;
    private String studentFullName;
    private String enrollmentCode;


    // Getters and Setters


    public String getClassCode() {
        return classCode;
    }

    public void setClassCode(String classCode) {
        this.classCode = classCode;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public String getEnrollmentCheckBox() {
        return enrollmentCheckBox;
    }

    public void setEnrollmentCheckBox(String enrollmentCheckBox) {
        this.enrollmentCheckBox = enrollmentCheckBox;
    }

    public String getStudentCode() {
        return studentCode;
    }

    public void setStudentCode(String studentCode) {
        this.studentCode = studentCode;
    }

    public String getStudentFullName() {
        return studentFullName;
    }

    public void setStudentFullName(String studentFullName) {
        this.studentFullName = studentFullName;
    }

    public String getEnrollmentCode() {
        return enrollmentCode;
    }

    public void setEnrollmentCode(String enrollmentCode) {
        this.enrollmentCode = enrollmentCode;
    }

    // Constructor


    public ClsEnrollment() {
    }

    public ClsEnrollment(String classCode, String classname, String enrollmentCheckBox, String studentCode, String studentFullName, String enrollmentCode) {
        this.classCode = classCode;
        this.classname = classname;
        this.enrollmentCheckBox = enrollmentCheckBox;
        this.studentCode = studentCode;
        this.studentFullName = studentFullName;
        this.enrollmentCode = enrollmentCode;
    }
}