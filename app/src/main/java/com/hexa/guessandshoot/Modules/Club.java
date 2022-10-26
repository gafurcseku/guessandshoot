package com.hexa.guessandshoot.Modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Club implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("is_club")
    @Expose
    private String isClub;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("country_id")
    @Expose
    private String countryId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("pivot")
    @Expose
    private pivot pivot;

    @SerializedName("is_choose")
    @Expose
    private String isChoose;

    public Club() {
    }

    public Club(String name ,int id) {
        this.name = name;
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public pivot getPivot() {
        return pivot;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIsClub() {
        return isClub;
    }

    public void setIsClub(String isClub) {
        this.isClub = isClub;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setIsChoose(String isChoose) {
        this.isChoose = isChoose;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIsChoose() {
        return isChoose;
    }

    public class pivot {

        @SerializedName("champion_id")
        @Expose
        private String champion_id;

        @SerializedName("club_id")
        @Expose
        private String club_id;

        public String getClub_id() {
            return club_id;
        }

        public String getChampion_id() {
            return champion_id;
        }
    }
}
