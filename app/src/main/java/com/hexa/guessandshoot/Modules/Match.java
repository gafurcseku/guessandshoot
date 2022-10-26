package com.hexa.guessandshoot.Modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Match {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("champion_id")
@Expose
private String championId;
@SerializedName("match_date")
@Expose
private String matchDate;
@SerializedName("match_time")
@Expose
private String matchTime;
@SerializedName("match_date_time")
@Expose
private String matchDateTime;
@SerializedName("home_club")
@Expose
private String homeClub;
@SerializedName("away_club")
@Expose
private String awayClub;
@SerializedName("winner_point")
@Expose
private String winnerPoint;
@SerializedName("winner_prize")
@Expose
private String winnerPrize;
@SerializedName("guess_point")
@Expose
private String guessPoint;
@SerializedName("home_goals")
@Expose
private String homeGoals;
@SerializedName("away_goals")
@Expose
private String awayGoals;
@SerializedName("status")
@Expose
private String status;
@SerializedName("created_at")
@Expose
private String createdAt;
@SerializedName("home_club_name")
@Expose
private String homeClubName;
@SerializedName("away_club_name")
@Expose
private String awayClubName;
@SerializedName("champion_name")
@Expose
private String championName;
@SerializedName("club_home")
@Expose
private Db_My_Expections_My_Account.ClubHome clubHome;
@SerializedName("club_away")
@Expose
private Db_My_Expections_My_Account.ClubAway clubAway;
@SerializedName("champion")
@Expose
private Db_My_Expections_My_Account.Champion champion;

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

public String getMatchDate() {
return matchDate;
}

public void setMatchDate(String matchDate) {
this.matchDate = matchDate;
}

public String getMatchTime() {
return matchTime;
}

public void setMatchTime(String matchTime) {
this.matchTime = matchTime;
}

public String getMatchDateTime() {
return matchDateTime;
}

public void setMatchDateTime(String matchDateTime) {
this.matchDateTime = matchDateTime;
}

public String getHomeClub() {
return homeClub;
}

public void setHomeClub(String homeClub) {
this.homeClub = homeClub;
}

public String getAwayClub() {
return awayClub;
}

public void setAwayClub(String awayClub) {
this.awayClub = awayClub;
}

public String getWinnerPoint() {
return winnerPoint;
}

public void setWinnerPoint(String winnerPoint) {
this.winnerPoint = winnerPoint;
}

public String getWinnerPrize() {
return winnerPrize;
}

public void setWinnerPrize(String winnerPrize) {
this.winnerPrize = winnerPrize;
}

public String getGuessPoint() {
return guessPoint;
}

public void setGuessPoint(String guessPoint) {
this.guessPoint = guessPoint;
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

public String getHomeClubName() {
return homeClubName;
}

public void setHomeClubName(String homeClubName) {
this.homeClubName = homeClubName;
}

public String getAwayClubName() {
return awayClubName;
}

public void setAwayClubName(String awayClubName) {
this.awayClubName = awayClubName;
}

public String getChampionName() {
return championName;
}

public void setChampionName(String championName) {
this.championName = championName;
}

public Db_My_Expections_My_Account.ClubHome getClubHome() {
return clubHome;
}

public void setClubHome(Db_My_Expections_My_Account.ClubHome clubHome) {
this.clubHome = clubHome;
}

public Db_My_Expections_My_Account.ClubAway getClubAway() {
return clubAway;
}

public void setClubAway(Db_My_Expections_My_Account.ClubAway clubAway) {
this.clubAway = clubAway;
}

public Db_My_Expections_My_Account.Champion getChampion() {
return champion;
}

public void setChampion(Db_My_Expections_My_Account.Champion champion) {
this.champion = champion;
}

}