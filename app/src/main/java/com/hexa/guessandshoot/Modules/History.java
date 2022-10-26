package com.hexa.guessandshoot.Modules;

import android.service.autofill.FieldClassification;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class History {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("champion_id")
    @Expose
    private String championId;
    @SerializedName("club_id")
    @Expose
    private String clubId;
    @SerializedName("match_id")
    @Expose
    private String matchId;
    @SerializedName("home_goals")
    @Expose
    private String homeGoals;
    @SerializedName("away_goals")
    @Expose
    private String awayGoals;
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
    private String createdAt;
    @SerializedName("match")
    @Expose
    private Match match;
    @SerializedName("champion")
    @Expose
    private Db_My_Expections_My_Account.Champion champion;


    public Db_My_Expections_My_Account.Champion getChampion() {
        return champion;
    }

    public void setChampion(Db_My_Expections_My_Account.Champion champion) {
        this.champion = champion;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getChampionId() {
        return championId;
    }

    public void setChampionId(String championId) {
        this.championId = championId;
    }

    public String getClubId() {
        return clubId;
    }

    public void setClubId(String clubId) {
        this.clubId = clubId;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public String getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(String homeGoals) {
        this.homeGoals = homeGoals;
    }

    public String getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(String awayGoals) {
        this.awayGoals = awayGoals;
    }

    public String getGuess() {
        return guess;
    }

    public void setGuess(String guess) {
        this.guess = guess;
    }

    public String getPrize() {
        return prize;
    }

    public void setPrize(String prize) {
        this.prize = prize;
    }

    public String getUsed() {
        return used;
    }

    public void setUsed(String used) {
        this.used = used;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Match getMatch() {
        return match;
    }

    public void setMatch(Match match) {
        this.match = match;
    }

}
