package com.hexa.guessandshoot.Modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Db_ContactUs {


        @SerializedName("id")
        @Expose
        private Integer id;
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
        private Object joinDescription;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("key_words")
        @Expose
        private Object keyWords;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
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

        public void setJoinDescription(Object joinDescription) {
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

        public void setKeyWords(Object keyWords) {
            this.keyWords = keyWords;
        }

    }