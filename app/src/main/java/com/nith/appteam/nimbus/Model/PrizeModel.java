package com.nith.appteam.nimbus.Model;

public class PrizeModel
{
    private int position;
    private String prize;

    public PrizeModel(int position, String prize) {
        this.position = position;
        this.prize = prize;
    }

    public int getPosition() {
        return position;
    }

    public String getPrize() {
        return prize;
    }
}
