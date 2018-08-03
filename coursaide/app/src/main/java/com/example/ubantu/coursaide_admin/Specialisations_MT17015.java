package com.example.ubantu.coursaide_admin;

/**
 * Created by hp on 02-03-2018.
 */

public class Specialisations_MT17015
{
    private String specialisation;
    private String branch;

    public Specialisations_MT17015()
    {

    }

    public Specialisations_MT17015(String specialisation, String branch)
    {
        this.specialisation = specialisation;
        this.branch = branch;
    }

    public String getSpecialisation()
    {
        return specialisation;
    }

    public void setSpecialisation(String specialisation)
    {
        this.specialisation = specialisation;
    }

    public String getBranch()
    {
        return branch;
    }

    public void setBranch(String branch)
    {
        this.branch = branch;
    }
}

