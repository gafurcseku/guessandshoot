package com.hexa.guessandshoot.Modules.last_db;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Winner {

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
    @SerializedName("player_id")
    @Expose
    private String playerId;
    @SerializedName("goalkeeper_id")
    @Expose
    private String goalkeeperId;
    @SerializedName("match_id")
    @Expose
    private String matchId;
    @SerializedName("match_date_time")
    @Expose
    private Object matchDateTime;
    @SerializedName("home_goals")
    @Expose
    private Object homeGoals;
    @SerializedName("away_goals")
    @Expose
    private Object awayGoals;
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

    @SerializedName("champion")
    @Expose
    private Champion champion;

    @SerializedName("club")
    @Expose
    private Club club;

    @SerializedName("user")
    @Expose
    private User user;

    @SerializedName("player")
    @Expose
    private Player player;

    @SerializedName("goalkeeper")
    @Expose
    private Player goalkeeper;


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

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getGoalkeeperId() {
        return goalkeeperId;
    }

    public void setGoalkeeperId(String goalkeeperId) {
        this.goalkeeperId = goalkeeperId;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public Object getMatchDateTime() {
        return matchDateTime;
    }

    public void setMatchDateTime(Object matchDateTime) {
        this.matchDateTime = matchDateTime;
    }

    public Object getHomeGoals() {
        return homeGoals;
    }

    public void setHomeGoals(Object homeGoals) {
        this.homeGoals = homeGoals;
    }

    public Object getAwayGoals() {
        return awayGoals;
    }

    public void setAwayGoals(Object awayGoals) {
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

    public Champion getChampion() {
        return champion;
    }

    public void setChampion(Champion champion) {
        this.champion = champion;
    }

    public Club getClub() {
        return club;
    }

    public void setClub(Club club) {
        this.club = club;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getGoalkeeper() {
        return goalkeeper;
    }

    public void setGoalkeeper(Player goalkeeper) {
        this.goalkeeper = goalkeeper;
    }
}
