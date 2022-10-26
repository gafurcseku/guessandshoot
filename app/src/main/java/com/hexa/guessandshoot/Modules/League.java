package com.hexa.guessandshoot.Modules;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hexa.guessandshoot.Modules.last_db.User;


public class League {
@SerializedName("id")
@Expose
private Integer id;
@SerializedName("name")
@Expose
private String name;
@SerializedName("code")
@Expose
private String code;
@SerializedName("user_id")
@Expose
private Integer userId;
@SerializedName("user_count")
@Expose
private Integer userCount;
@SerializedName("is_private")
@Expose
private Integer isPrivate;
@SerializedName("image")
@Expose
private String image;
@SerializedName("status")
@Expose
private String status;
@SerializedName("created_at")
@Expose
private String createdAt;
    @SerializedName("total_points")
    @Expose
    public String total_points;

    @SerializedName("rank")
    @Expose
    public String rank;



@SerializedName("players")
@Expose
private List<Player> players = null;

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

public String getCode() {
return code;
}

public void setCode(String code) {
this.code = code;
}

public Integer getUserId() {
return userId;
}

public void setUserId(Integer userId) {
this.userId = userId;
}

public Integer getUserCount() {
return userCount;
}

public void setUserCount(Integer userCount) {
this.userCount = userCount;
}

public Integer getIsPrivate() {
return isPrivate;
}

public void setIsPrivate(Integer isPrivate) {
this.isPrivate = isPrivate;
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

public String getCreatedAt() {
return createdAt;
}

public void setCreatedAt(String createdAt) {
this.createdAt = createdAt;
}

public List<Player> getPlayers() {
return players;
}

public void setPlayers(List<Player> players) {
this.players = players;
}

}

