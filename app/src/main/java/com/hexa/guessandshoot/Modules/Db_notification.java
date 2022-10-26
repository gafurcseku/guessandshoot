package com.hexa.guessandshoot.Modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Db_notification {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("order_id")
    @Expose
    private String order_id;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("messag_type")
    @Expose
    private String messag_type;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("image_user")
    @Expose
    private String image_user;
    @SerializedName("name_user")
    @Expose
    private String name_user;
    @SerializedName("total_order")
    @Expose
    private String total_order;
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("created_at")
    @Expose
    private String created_at;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }

    public String getUser_id() {
        return user_id;
    }

    public String getMessage() {
        return message;
    }

    public String getImage_user() {
        return image_user;
    }

    public String getMessag_type() {
        return messag_type;
    }

    public String getName_user() {
        return name_user;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getTotal_order() {
        return total_order;
    }

    public String getCreated_at() {
        return created_at;
    }
}
