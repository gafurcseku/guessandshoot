package com.hexa.guessandshoot.Modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Db_champions implements Serializable {
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
    @SerializedName("champion_id")
    @Expose
    private String championId;
    @SerializedName("is_group")
    @Expose
    public String is_group;
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
    @SerializedName("rank_points")
    @Expose
    public String rank_points;
    @SerializedName("rank_prize")
    @Expose
    public String rank_prize;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("rank_end_date")
    @Expose
    public String rank_end_date;
    @SerializedName("clubs")
    @Expose
    private List<Clubs> clubs;
    @SerializedName("is_rank")
    @Expose
    public List<IsRank> isRanks;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStartDate() {
        return startDate;
    }

    public List<Clubs> getClub() {
        return clubs;
    }

    public void setClub(List<Clubs> club) {
        this.clubs = club;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return "Db_champions{" +
                "id=" + id +
                ", startDate='" + startDate + '\'' +
                ", endGuess='" + endGuess + '\'' +
                ", endDate='" + endDate + '\'' +
                ", winnerPoint='" + winnerPoint + '\'' +
                ", winnerPrize='" + winnerPrize + '\'' +
                ", championId='" + championId + '\'' +
                ", image='" + image + '\'' +
                ", status='" + status + '\'' +
                ", orderPriority='" + orderPriority + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", isChoose='" + isChoose + '\'' +
                ", name='" + name + '\'' +
                ", clubs=" + clubs +
                '}';
    }


    public class IsRank {

        @SerializedName("group_id")
        @Expose
        private String groupId;
        @SerializedName("ranks")
        @Expose
        private String ranks;

        public String getGroupId() {
            return groupId;
        }

        public void setGroupId(String groupId) {
            this.groupId = groupId;
        }

        public String getRanks() {
            return ranks;
        }

        public void setRanks(String ranks) {
            this.ranks = ranks;
        }

    }
}
