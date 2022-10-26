package com.hexa.guessandshoot.Modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Clubs implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("champion_id")
    @Expose
    private String championId;
    @SerializedName("club_id")
    @Expose
    private String clubId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("group_id")
    @Expose
    public String group_id;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("is_choose")
    @Expose
    private String isChoose;
    @SerializedName("club")
    @Expose
    public Club club;
    public Clubs() {

    }
    public Clubs(Club club) {
        this.club = club;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getIsChoose() {
        return isChoose;
    }

    public void setIsChoose(String isChoose) {
        this.isChoose = isChoose;
    }


}
