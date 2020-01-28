package com.nith.appteam.nimbus.Model;

public class ContestantInfo
{
    private String name;
    private String rollNumber;

    public ContestantInfo(String name, String rollNumber) {
        this.name = name;
        this.rollNumber = rollNumber;
    }

    public String getName() {
        return name;
    }

    public String getRollNumber() {
        return rollNumber;
    }
}
