package com.nith.appteam.nimbus.Model;

/**
 * Created by LENOVO on 07-03-2019.
 */

public class CategoryQuiz
{
    public String Event;
    public String HostedBy;
    public String QuizId;
    public String ImageUrl;
//    public String
    public CategoryQuiz(String Event, String HostedBy,String QuizId,String ImageUrl)
    {
        this.Event = Event;
        this.HostedBy = HostedBy;
        this.QuizId = QuizId;
    }
    public String getEvent()
    {
        return Event;
    }
    public String getHostedBy()
    {
        return HostedBy;
    }
    public String getQuizId()
    {
        return QuizId;
    }
    public String getImageUrl()
    {
        return ImageUrl;
    }

}

