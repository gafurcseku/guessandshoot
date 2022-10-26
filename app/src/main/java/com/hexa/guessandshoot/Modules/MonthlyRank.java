package com.hexa.guessandshoot.Modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MonthlyRank {

@SerializedName("id")
@Expose
private Integer id;
@SerializedName("user_id")
@Expose
private String userId;
@SerializedName("month")
@Expose
private String month;
@SerializedName("year")
@Expose
private String year;
@SerializedName("rank")
@Expose
private String rank;
@SerializedName("amount")
@Expose
private String amount;
@SerializedName("points")
@Expose
private String points;
@SerializedName("created_at")
@Expose
private String createdAt;
@SerializedName("user")
@Expose
private User user;

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

public String getMonth() {
return month;
}

public void setMonth(String month) {
this.month = month;
}

public String getYear() {
return year;
}

public void setYear(String year) {
this.year = year;
}

public String getRank() {
return rank;
}

public void setRank(String rank) {
this.rank = rank;
}

public String getAmount() {
return amount;
}

public void setAmount(String amount) {
this.amount = amount;
}

public String getPoints() {
return points;
}

public void setPoints(String points) {
this.points = points;
}

public String getCreatedAt() {
return createdAt;
}

public void setCreatedAt(String createdAt) {
this.createdAt = createdAt;
}

public User getUser() {
return user;
}

public void setUser(User user) {
this.user = user;
}
    public class User {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("image_profile")
        @Expose
        private String imageProfile;
        @SerializedName("remember_token")
        @Expose
        private Object rememberToken;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("payment_json")
        @Expose
        private String paymentJson;
        @SerializedName("signature")
        @Expose
        private Object signature;
        @SerializedName("points")
        @Expose
        private String points;
        @SerializedName("lang")
        @Expose
        private String lang;
        @SerializedName("payment_token")
        @Expose
        private String paymentToken;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getImageProfile() {
            return imageProfile;
        }

        public void setImageProfile(String imageProfile) {
            this.imageProfile = imageProfile;
        }

        public Object getRememberToken() {
            return rememberToken;
        }

        public void setRememberToken(Object rememberToken) {
            this.rememberToken = rememberToken;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPaymentJson() {
            return paymentJson;
        }

        public void setPaymentJson(String paymentJson) {
            this.paymentJson = paymentJson;
        }

        public Object getSignature() {
            return signature;
        }

        public void setSignature(Object signature) {
            this.signature = signature;
        }

        public String getPoints() {
            return points;
        }

        public void setPoints(String points) {
            this.points = points;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getPaymentToken() {
            return paymentToken;
        }

        public void setPaymentToken(String paymentToken) {
            this.paymentToken = paymentToken;
        }

    }
}



