package com.example.ie_courses;

public class Course {
    private String term;
    private String courseCode;
    private String courseName;
    private int credits;
    private double ects;
    private String category;
    private String status;
    private String letterGrade;
    private String termTaken;
    private boolean graduationContribution;
    private String courseUrl;

    public Course(String term, String courseCode, String courseName, int credits, double ects, String category,
                  String status, String letterGrade, String termTaken, boolean graduationContribution, String courseUrl) {
        this.term = term;
        this.courseCode = courseCode;
        this.courseName = courseName;
        this.credits = credits;
        this.ects = ects;
        this.category = category;
        this.status = status;
        this.letterGrade = letterGrade;
        this.termTaken = termTaken;
        this.graduationContribution = graduationContribution;
        this.courseUrl = courseUrl;
    }

    public String getTerm() { return term; }
    public String getCourseCode() { return courseCode; }
    public String getCourseName() { return courseName; }
    public int getCredits() { return credits; }
    public double getEcts() { return ects; }
    public String getCategory() { return category; }
    public String getStatus() { return status; }
    public String getLetterGrade() { return letterGrade; }
    public String getTermTaken() { return termTaken; }
    public boolean isGraduationContribution() { return graduationContribution; }
    public String getCourseUrl() { return courseUrl; }
}
