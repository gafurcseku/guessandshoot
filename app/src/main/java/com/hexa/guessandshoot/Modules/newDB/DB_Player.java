package com.hexa.guessandshoot.Modules.newDB;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DB_Player {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("champion_id")
    @Expose
    private String championId;
    @SerializedName("player_id")
    @Expose
    private String playerId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("is_player_choose")
    @Expose
    private String isPlayerChoose;
    @SerializedName("player")
    @Expose
    private DB_PlayerDetails player;

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

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
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

    public String getIsPlayerChoose() {
        return isPlayerChoose;
    }

    public void setIsPlayerChoose(String isPlayerChoose) {
        this.isPlayerChoose = isPlayerChoose;
    }

    public DB_PlayerDetails getPlayer() {
        return player;
    }

    public void setPlayer(DB_PlayerDetails player) {
        this.player = player;
    }
}

