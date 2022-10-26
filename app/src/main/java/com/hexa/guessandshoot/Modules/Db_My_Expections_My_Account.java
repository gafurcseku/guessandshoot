package com.hexa.guessandshoot.Modules;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Db_My_Expections_My_Account {

    @Override
    public String toString() {
        return "Db_My_Expections_My_Account{" +
                "id=" + id +
                ", championId='" + championId + '\'' +
                ", matchDate='" + matchDate + '\'' +
                ", matchTime='" + matchTime + '\'' +
                ", homeClub='" + homeClub + '\'' +
                ", awayClub='" + awayClub + '\'' +
                ", winnerPoint='" + winnerPoint + '\'' +
                ", winnerPrize='" + winnerPrize + '\'' +
                ", guessPoint='" + guessPoint + '\'' +
                ", homeGoals='" + homeGoals + '\'' +
                ", awayGoals='" + awayGoals + '\'' +
                ", status='" + status + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", homeClubName='" + homeClubName + '\'' +
                ", awayClubName='" + awayClubName + '\'' +
                ", championName='" + championName + '\'' +
                ", match_date_time='" + match_date_time + '\'' +
                ", clubHome=" + clubHome +
                ", clubAway=" + clubAway +
                ", champion=" + champion +
                ", user_guess=" + user_guess +
                '}';
    }

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
    @SerializedName("match_date_time")
    @Expose
    public String match_date_time;
    @SerializedName("matche_text_name")
    @Expose
    public String matche_text_name;

    @SerializedName("club_home")
    @Expose
    private ClubHome clubHome;
    @SerializedName("club_away")
    @Expose
    private ClubAway clubAway;
    @SerializedName("champion")
    @Expose
    private Champion champion;

    @SerializedName("user_guess")
    @Expose
    private List<String> user_guess;

    public String getMatch_date_time() {
        return match_date_time;
    }

    public void setMatch_date_time(String match_date_time) {
        this.match_date_time = match_date_time;
    }

    public List<String> getUser_guess() {
        return user_guess;
    }

    public void setUser_guess(List<String> user_guess) {
        this.user_guess = user_guess;
    }

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
        if (homeGoals==null){
            return "-";
        }else {
            return homeGoals;
        }

    }

    public void setHomeGoals(String homeGoals) {
        this.homeGoals = homeGoals;
    }

    public String getAwayGoals() {

        if (awayGoals==null){
            return "-";
        }else {
            return awayGoals;
        }

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

    public ClubHome getClubHome() {
        return clubHome;
    }

    public void setClubHome(ClubHome clubHome) {
        this.clubHome = clubHome;
    }

    public ClubAway getClubAway() {
        return clubAway;
    }

    public void setClubAway(ClubAway clubAway) {
        this.clubAway = clubAway;
    }

    public Champion getChampion() {
        return champion;
    }

    public void setChampion(Champion champion) {
        this.champion = champion;
    }





    public class Champion {

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
        @SerializedName("guess_point")
        @Expose
        private String guessPoint;
        @SerializedName("champion_id")
        @Expose
        private String championId;
        @SerializedName("image")
        @Expose
        private String image;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("is_choose")
        @Expose
        private String isChoose;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("translations")
        @Expose
        private List<Translation__> translations = null;

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

        public String getGuessPoint() {
            return guessPoint;
        }

        public void setGuessPoint(String guessPoint) {
            this.guessPoint = guessPoint;
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

        public List<Translation__> getTranslations() {
            return translations;
        }

        public void setTranslations(List<Translation__> translations) {
            this.translations = translations;
        }

    }

    public class ClubAway {

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
        private List<Translation_> translations = null;

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

        public List<Translation_> getTranslations() {
            return translations;
        }

        public void setTranslations(List<Translation_> translations) {
            this.translations = translations;
        }

    }

    public class ClubHome {

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
        private List<Translation> translations = null;

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

        public List<Translation> getTranslations() {
            return translations;
        }

        public void setTranslations(List<Translation> translations) {
            this.translations = translations;
        }

    }

    public class Translation {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("club_id")
        @Expose
        private String clubId;
        @SerializedName("locale")
        @Expose
        private String locale;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("deleted_at")
        @Expose
        private Object deletedAt;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getClubId() {
            return clubId;
        }

        public void setClubId(String clubId) {
            this.clubId = clubId;
        }

        public String getLocale() {
            return locale;
        }

        public void setLocale(String locale) {
            this.locale = locale;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getDeletedAt() {
            return deletedAt;
        }

        public void setDeletedAt(Object deletedAt) {
            this.deletedAt = deletedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }

    public class Translation_ {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("club_id")
        @Expose
        private String clubId;
        @SerializedName("locale")
        @Expose
        private String locale;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("deleted_at")
        @Expose
        private Object deletedAt;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getClubId() {
            return clubId;
        }

        public void setClubId(String clubId) {
            this.clubId = clubId;
        }

        public String getLocale() {
            return locale;
        }

        public void setLocale(String locale) {
            this.locale = locale;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getDeletedAt() {
            return deletedAt;
        }

        public void setDeletedAt(Object deletedAt) {
            this.deletedAt = deletedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }

    public class Translation__ {

        @SerializedName("id")
        @Expose
        private Integer id;
        @SerializedName("champion_id")
        @Expose
        private String championId;
        @SerializedName("locale")
        @Expose
        private String locale;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("deleted_at")
        @Expose
        private Object deletedAt;
        @SerializedName("created_at")
        @Expose
        private String createdAt;
        @SerializedName("updated_at")
        @Expose
        private String updatedAt;

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

        public String getLocale() {
            return locale;
        }

        public void setLocale(String locale) {
            this.locale = locale;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Object getDeletedAt() {
            return deletedAt;
        }

        public void setDeletedAt(Object deletedAt) {
            this.deletedAt = deletedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

    }
}
