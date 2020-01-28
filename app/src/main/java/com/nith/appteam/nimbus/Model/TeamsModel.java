package com.nith.appteam.nimbus.Model;

import java.util.ArrayList;

public class TeamsModel
{
    private String id;
    private ArrayList<ContestantInfo> memebers;
    private String name;

    public TeamsModel(String id, ArrayList<ContestantInfo> memebers, String name) {
        this.id = id;
        this.memebers = memebers;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public ArrayList<ContestantInfo> getMemebers() {
        return memebers;
    }

    public String getName() {
        return name;
    }
}