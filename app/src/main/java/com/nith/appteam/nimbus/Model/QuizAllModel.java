package com.nith.appteam.nimbus.Model;

import java.util.List;

/**
 * Created by LENOVO on 12-03-2019.
 */

public class QuizAllModel {
    String quizid,quizname,organisedBy;
    int time;
    public QuizAllModel (String quizid , String quizname , String organisedBy,int time)
    {
        this.quizid = quizid;
        this.quizname = quizname;
        this.organisedBy = organisedBy;
        this.time = time;
    }
    public String getQuizid()
    {
        return quizid;
    }
    public String getQuizname()
    {
        return quizname;
    }
    public String getOrganisedBy()
    {
        return organisedBy;
    }
    public int getTime(){return time;}
}
