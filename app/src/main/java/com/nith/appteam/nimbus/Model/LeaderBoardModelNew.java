package com.nith.appteam.nimbus.Model;

import com.nith.appteam.nimbus.Activity.LeaderBoardActivity;

/**
 * Created by LENOVO on 12-03-2019.
 */

public class LeaderBoardModelNew {

    String username,score,id,rollno,encoded;
    public LeaderBoardModelNew(String username , String score , String id ,String rollno,String encoded)
    {
        this.username = username;
        this.score = score;
        this.id = id;
        this.rollno = rollno;
        this.encoded = encoded;
    }
    public String getUsername()
    {
        return username;
    }
    public String  getScore()
    {
        return score;
    }
    public String getidd()
    {
        return id;
    }
    public String getRollNumber()
    {
        return rollno;
    }
    public String getEncoded(){
        return encoded;
    }
}
