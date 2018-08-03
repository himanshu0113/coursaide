package com.example.ubantu.coursaide_admin;

/**
 * Created by hp on 02-03-2018.
 */

public class Courses_MT17015
{
    private String Ccode;
    private String Cname;
    private String Cspec;
    private String Cbuck;
    private String Cbranch;
    private String Credits;
    public Courses_MT17015()
    {

    }

    public Courses_MT17015(String ccode, String cname, String cspec, String cbuck, String cbranch, String credits) {
        Ccode = ccode;
        Cname = cname;
        Cspec = cspec;
        Cbuck = cbuck;
        Cbranch = cbranch;
        Credits = credits;
    }

    public String getCcode() {
        return Ccode;
    }

    public void setCcode(String ccode) {
        Ccode = ccode;
    }

    public String getCname() {
        return Cname;
    }

    public void setCname(String cname) {
        Cname = cname;
    }

    public String getCspec() {
        return Cspec;
    }

    public void setCspec(String cspec) {
        Cspec = cspec;
    }

    public String getCbuck() {
        return Cbuck;
    }

    public void setCbuck(String cbuck) {
        Cbuck = cbuck;
    }

    public String getCbranch() {
        return Cbranch;
    }

    public void setCbranch(String cbranch) {
        Cbranch = cbranch;
    }

    public String getCredits() {
        return Credits;
    }

    public void setCredits(String credits) {
        Credits = credits;
    }
}