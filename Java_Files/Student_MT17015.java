package com.example.ubantu.coursaide_admin;

import java.util.ArrayList;

/**
 * Created by khushboo on 9/4/18.
 */

public class Student_MT17015 {
    String username;
    ArrayList<String> subjects=new ArrayList<>();
    ArrayList<String> semsubjects=new ArrayList<>();
    String credits;
    String degree;
    String specialization;
    String branch;

    public Student_MT17015() {
    }

    public Student_MT17015(String username, ArrayList<String> subjects, String credits, String degree, String specialization, String branch) {
        this.username = username;
        for(int i=0;i<subjects.size();i++)
        {
            this.subjects.add(subjects.get(i));
        }
        semsubjects.add("None");
        this.credits = credits;
        this.degree = degree;
        this.specialization = specialization;
        this.branch = branch;
    }
    public Student_MT17015(String username, ArrayList<String> subjects, String credits, String degree, String specialization, String branch, ArrayList<String> thisSem) {
        this.username = username;
        for(int i=0;i<subjects.size();i++)
        {
            this.subjects.add(subjects.get(i));
        }
        this.credits = credits;
        this.degree = degree;
        this.specialization = specialization;
        this.branch = branch;
        for(int i=0;i<thisSem.size();i++)
        {
            semsubjects.add(thisSem.get(i));
        }
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ArrayList<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(ArrayList<String> subjects) {
        this.subjects = subjects;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }

    public ArrayList<String> getSemsubjects() {
        return semsubjects;
    }

    public void setSemsubjects(ArrayList<String> semsubjects) {
        this.semsubjects = semsubjects;
    }

}