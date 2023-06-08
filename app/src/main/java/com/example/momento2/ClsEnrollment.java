package com.example.momento2;

public class ClsEnrollment {

    private String CourseCode;
    private String Name;
    private String State;
    private String IdCard;
    private String FullName;
    private String enrollmentCode;

    public String getCourseCode() {
        return CourseCode;
    }

    public void setCourseCode(String courseCode) {
        CourseCode = courseCode;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getIdCard() {
        return IdCard;
    }

    public void setIdCard(String idCard) {
        IdCard = idCard;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEnrollmentCode() {
        return enrollmentCode;
    }


    public void setEnrollmentCode(String enrollmentCode) {
        this.enrollmentCode = enrollmentCode;
    }

    public ClsEnrollment(String courseCode, String name, String state, String idCard, String fullName, String enrollmentCode) {
        CourseCode = courseCode;
        Name = name;
        State = state;
        IdCard = idCard;
        FullName = fullName;
        this.enrollmentCode = enrollmentCode;
    }
    public ClsEnrollment() {
    }




}
