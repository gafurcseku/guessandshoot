package com.hexa.guessandshoot.Modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Db_My_Expections_profile {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("champion_id")
    @Expose
    private String champion_id;
    @SerializedName("club_id")
    @Expose
    private String club_id;
    @SerializedName("match_id")
    @Expose
    private String match_id;
    @SerializedName("home_goals")
    @Expose
    private String home_goals;
    @SerializedName("away_goals")
    @Expose
    private String away_goals;
    @SerializedName("guess")
    @Expose
    private String guess;
    @SerializedName("prize")
    @Expose
    private String prize;
    @SerializedName("used")
    @Expose
    private String used;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String created_at;
    @SerializedName("match")
    @Expose
    private Db_My_Expections_My_Account match;


    public String getCreated_at() {
        return created_at;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getStatus() {
        return status;
    }

    public Integer getId() {
        return id;
    }

    public String getAway_goals() {
        return away_goals;
    }

    public String getChampion_id() {
        return champion_id;
    }

    public String getClub_id() {
        return club_id;
    }

    public String getGuess() {
        return guess;
    }

    public String getHome_goals() {
        return home_goals;
    }

    public String getMatch_id() {
        return match_id;
    }

    public String getPrize() {
        return prize;
    }

    public String getUsed() {
        return used;
    }

    public Db_My_Expections_My_Account getMatch() {
        return match;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public void setChampion_id(String champion_id) {
        this.champion_id = champion_id;
    }

    public void setClub_id(String club_id) {
        this.club_id = club_id;
    }

    public void setMatch_id(String match_id) {
        this.match_id = match_id;
    }

    public void setHome_goals(String home_goals) {
        this.home_goals = home_goals;
    }

    public void setAway_goals(String away_goals) {
        this.away_goals = away_goals;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setMatch(Db_My_Expections_My_Account match) {
        this.match = match;
    }
}
