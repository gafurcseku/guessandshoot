package com.hexa.guessandshoot.Modules.newDB;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hexa.guessandshoot.Modules.last_db.ClubTranslation;

import java.util.List;

public class DB_Club {

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
    @SerializedName("translations")
    @Expose
    private List<ClubTranslation> DBClubTranslations = null;

    public Integer getId() {
        return id;
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

    public List<ClubTranslation> getDBClubTranslations() {
        return DBClubTranslations;
    }

    public void setDBClubTranslations(List<ClubTranslation> DBClubTranslations) {
        this.DBClubTranslations = DBClubTranslations;
    }

}
