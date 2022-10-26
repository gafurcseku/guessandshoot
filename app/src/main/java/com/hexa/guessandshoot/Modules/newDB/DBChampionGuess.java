package com.hexa.guessandshoot.Modules.newDB;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hexa.guessandshoot.Modules.last_db.ChampionTranslation;

import java.util.List;

public class DBChampionGuess {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("start_date")
    @Expose
    private String startDate;
    @SerializedName("end_guess")
    @Expose
    private String endGuess;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("winner_point")
    @Expose
    private String winnerPoint;
    @SerializedName("winner_prize")
    @Expose
    private String winnerPrize;
    @SerializedName("player_point")
    @Expose
    private String playerPoint;
    @SerializedName("player_prize")
    @Expose
    private String playerPrize;
    @SerializedName("goalkeeper_point")
    @Expose
    private String goalkeeperPoint;
    @SerializedName("goalkeeper_prize")
    @Expose
    private String goalkeeperPrize;
    @SerializedName("champion_id")
    @Expose
    private String championId;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("order_priority")
    @Expose
    private String orderPriority;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("is_choose")
    @Expose
    private String isChoose;
    @SerializedName("is_player_choose")
    @Expose
    private String isPlayerChoose;
    @SerializedName("is_goalkeeper_choose")
    @Expose
    private String isGoalkeeperChoose;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("players")
    @Expose
    private List<DB_Player> players = null;

    @SerializedName("goalkeepers")
    @Expose
    private List<DB_Player> goalkeepers = null;

    @SerializedName("translations")
    @Expose
    private List<ChampionTranslation> translations = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndGuess() {
        return endGuess;
    }

    public void setEndGuess(String endGuess) {
        this.endGuess = endGuess;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
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

    public String getPlayerPoint() {
        return playerPoint;
    }

    public void setPlayerPoint(String playerPoint) {
        this.playerPoint = playerPoint;
    }

    public String getPlayerPrize() {
        return playerPrize;
    }

    public void setPlayerPrize(String playerPrize) {
        this.playerPrize = playerPrize;
    }

    public String getGoalkeeperPoint() {
        return goalkeeperPoint;
    }

    public void setGoalkeeperPoint(String goalkeeperPoint) {
        this.goalkeeperPoint = goalkeeperPoint;
    }

    public String getGoalkeeperPrize() {
        return goalkeeperPrize;
    }

    public void setGoalkeeperPrize(String goalkeeperPrize) {
        this.goalkeeperPrize = goalkeeperPrize;
    }

    public String getChampionId() {
        return championId;
    }

    public void setChampionId(String championId) {
        this.championId = championId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderPriority() {
        return orderPriority;
    }

    public void setOrderPriority(String orderPriority) {
        this.orderPriority = orderPriority;
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

    public String getIsPlayerChoose() {
        return isPlayerChoose;
    }

    public void setIsPlayerChoose(String isPlayerChoose) {
        this.isPlayerChoose = isPlayerChoose;
    }

    public String getIsGoalkeeperChoose() {
        return isGoalkeeperChoose;
    }

    public void setIsGoalkeeperChoose(String isGoalkeeperChoose) {
        this.isGoalkeeperChoose = isGoalkeeperChoose;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<DB_Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<DB_Player> players) {
        this.players = players;
    }

    public List<ChampionTranslation> getTranslations() {
        return translations;
    }

    public void setTranslations(List<ChampionTranslation> translations) {
        this.translations = translations;
    }

    public List<DB_Player> getGoalkeepers() {
        return goalkeepers;
    }

    public void setGoalkeepers(List<DB_Player> goalkeepers) {
        this.goalkeepers = goalkeepers;
    }

    @Override
    public String toString() {
        return "DB_ChampionWinner{" +
                "id=" + id +
                ", startDate='" + startDate + '\'' +
                ", endGuess='" + endGuess + '\'' +
                ", endDate='" + endDate + '\'' +
                ", winnerPoint='" + winnerPoint + '\'' +
                ", winnerPrize='" + winnerPrize + '\'' +
                ", playerPoint='" + playerPoint + '\'' +
                ", playerPrize='" + playerPrize + '\'' +
                ", goalkeeperPoint='" + goalkeeperPoint + '\'' +
                ", goalkeeperPrize='" + goalkeeperPrize + '\'' +
                ", championId='" + championId + '\'' +
                ", image='" + image + '\'' +
                ", status='" + status + '\'' +
                ", orderPriority='" + orderPriority + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", isChoose='" + isChoose + '\'' +
                ", isPlayerChoose='" + isPlayerChoose + '\'' +
                ", isGoalkeeperChoose='" + isGoalkeeperChoose + '\'' +
                ", name='" + name + '\'' +
                ", players=" + players +
                ", goalkeepers=" + goalkeepers +
                ", translations=" + translations +
                '}';
    }
}
