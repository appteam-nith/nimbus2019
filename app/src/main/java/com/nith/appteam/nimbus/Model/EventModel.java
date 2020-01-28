package com.nith.appteam.nimbus.Model;


import java.util.ArrayList;

public class EventModel
{
    private String id;
    private String name;
    private String organizedBy;
    private String description;
    private String venue;
    private String date;
    private ArrayList<PrizeModel>prizeModels;
    private ArrayList<String>rules;
    boolean showDates;
    public EventModel(String id, String name, String organizedBy, String description, String venue, String date, ArrayList<PrizeModel> prizeModels, ArrayList<String> rules,boolean showDates) {
        this.id = id;
        this.showDates = showDates;
        this.name = name;
        this.organizedBy = organizedBy;
        this.description = description;
        this.venue = venue;
        this.date = date;
        this.prizeModels = prizeModels;
        this.rules = rules;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOrganizedBy() {
        return organizedBy;
    }

    public String getDescription() {
        return description;
    }

    public String getVenue() {
        return venue;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<PrizeModel> getPrizeModels() {
        return prizeModels;
    }

    public ArrayList<String> getRules() {
        return rules;
    }
    public boolean getShowDates()
    {
        return showDates;
    }
}
