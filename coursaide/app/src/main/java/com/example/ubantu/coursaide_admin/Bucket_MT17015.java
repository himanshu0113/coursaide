package com.example.ubantu.coursaide_admin;

/**
 * Created by khushboo on 2/3/18.
 */

public class Bucket_MT17015 {
    private String bName;
    private String branchName;

    public Bucket_MT17015() {
    }

    public Bucket_MT17015(String bName, String branchName) {
        this.bName = bName;
        this.branchName = branchName;
    }

    public String getbName() {
        return bName;
    }

    public void setbName(String bName) {
        this.bName = bName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }
}