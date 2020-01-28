package com.nith.appteam.nimbus.Model;

public class DepartmentalClubs
{
    private String clubId;
    private String nameClub;
    private String logo;
    private String info;

    public DepartmentalClubs(String clubId, String nameClub, String logo,String info) {
        this.clubId = clubId;
        this.nameClub = nameClub;
        this.logo = logo;
        this.info = info;
    }

    public String getClubId() {
        return clubId;
    }

    public String getNameClub() {
        return nameClub;
    }

    public String getLogo() {
        return logo;
    }

    public String getInfo() {
        return info;
    }
}
