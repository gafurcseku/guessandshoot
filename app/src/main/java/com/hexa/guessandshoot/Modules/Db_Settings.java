package com.hexa.guessandshoot.Modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Db_Settings {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("yearly_prize")
    @Expose
    private String yearlyPrize;
    @SerializedName("yearly_prize2")
    @Expose
    public String yearlyPrize2;
    @SerializedName("yearly_prize3")
    @Expose
    public String yearlyPrize3;
    @SerializedName("monthly_prize1")
    @Expose
    private String monthlyPrize1;
    @SerializedName("monthly_prize2")
    @Expose
    private String monthlyPrize2;
    @SerializedName("monthly_prize3")
    @Expose
    private String monthlyPrize3;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("logo")
    @Expose
    private String logo;
    @SerializedName("app_store_url")
    @Expose
    private String appStoreUrl;
    @SerializedName("play_store_url")
    @Expose
    private String playStoreUrl;
    @SerializedName("info_email")
    @Expose
    private String infoEmail;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("facebook")
    @Expose
    private String facebook;
    @SerializedName("twitter")
    @Expose
    private String twitter;
    @SerializedName("linked_in")
    @Expose
    private String linkedIn;
    @SerializedName("instagram")
    @Expose
    private String instagram;
    @SerializedName("google_plus")
    @Expose
    private String googlePlus;
    @SerializedName("min_order")
    @Expose
    private String minOrder;
    @SerializedName("from_hour")
    @Expose
    private String fromHour;
    @SerializedName("to_hour")
    @Expose
    private String toHour;
    @SerializedName("latitude")
    @Expose
    private String latitude;
    @SerializedName("longitude")
    @Expose
    private String longitude;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("created_at")
    @Expose
    private Object createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("join_description")
    @Expose
    private String joinDescription;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("key_words")
    @Expose
    private String keyWords;
    @SerializedName("note_01")
    @Expose
    private String note_01;

    @SerializedName("note_02")
    @Expose
    private String note_02;
    @SerializedName("note_03")
    @Expose
    private String note_03;
    @SerializedName("news")
    @Expose
    public String news;
    @SerializedName("leage_end_date")
    @Expose
    public String textDateEnd;

    @SerializedName("leage_1_prize")
    @Expose
    public String leage_1_prize;
    @SerializedName("leage_2_prize")
    @Expose
    public String leage_2_prize;
    @SerializedName("leage_3_prize")
    @Expose
    public String leage_3_prize;

    @SerializedName("leage_prizae_text_1")
    @Expose
    public String leage_prizae_text_1;
    @SerializedName("leage_prizae_text_2")
    @Expose
    public String leage_prizae_text_2;
    @SerializedName("leage_prizae_text_3")
    @Expose
    public String leage_prizae_text_3;


    public String getNote_01() {
        return note_01;
    }

    public void setNote_01(String note_01) {
        this.note_01 = note_01;
    }

    public String getNote_02() {
        return note_02;
    }

    public void setNote_02(String note_02) {
        this.note_02 = note_02;
    }

    public String getNote_03() {
        return note_03;
    }

    public void setNote_03(String note_03) {
        this.note_03 = note_03;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getYearlyPrize() {
        return yearlyPrize;
    }

    public void setYearlyPrize(String yearlyPrize) {
        this.yearlyPrize = yearlyPrize;
    }

    public String getMonthlyPrize1() {
        return monthlyPrize1;
    }

    public void setMonthlyPrize1(String monthlyPrize1) {
        this.monthlyPrize1 = monthlyPrize1;
    }

    public String getMonthlyPrize2() {
        return monthlyPrize2;
    }

    public void setMonthlyPrize2(String monthlyPrize2) {
        this.monthlyPrize2 = monthlyPrize2;
    }

    public String getMonthlyPrize3() {
        return monthlyPrize3;
    }

    public void setMonthlyPrize3(String monthlyPrize3) {
        this.monthlyPrize3 = monthlyPrize3;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getAppStoreUrl() {
        return appStoreUrl;
    }

    public void setAppStoreUrl(String appStoreUrl) {
        this.appStoreUrl = appStoreUrl;
    }

    public String getPlayStoreUrl() {
        return playStoreUrl;
    }

    public void setPlayStoreUrl(String playStoreUrl) {
        this.playStoreUrl = playStoreUrl;
    }

    public String getInfoEmail() {
        return infoEmail;
    }

    public void setInfoEmail(String infoEmail) {
        this.infoEmail = infoEmail;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }

    public String getInstagram() {
        return instagram;
    }

    public void setInstagram(String instagram) {
        this.instagram = instagram;
    }

    public String getGooglePlus() {
        return googlePlus;
    }

    public void setGooglePlus(String googlePlus) {
        this.googlePlus = googlePlus;
    }

    public String getMinOrder() {
        return minOrder;
    }

    public void setMinOrder(String minOrder) {
        this.minOrder = minOrder;
    }

    public String getFromHour() {
        return fromHour;
    }

    public void setFromHour(String fromHour) {
        this.fromHour = fromHour;
    }

    public String getToHour() {
        return toHour;
    }

    public void setToHour(String toHour) {
        this.toHour = toHour;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Object getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Object createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getJoinDescription() {
        return joinDescription;
    }

    public void setJoinDescription(String joinDescription) {
        this.joinDescription = joinDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Object getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(String keyWords) {
        this.keyWords = keyWords;
    }

}