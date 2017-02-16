package ps.emperor.easy_water.greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table IRRIGATION.
 */
public class Irrigation {

    private Long id;
    private String irrigation;
    private Integer isrelevance;
    private Integer groupnumber;
    private Integer valuenumber;
    private Integer filterHour;
    private Integer filterMinute;
    private String userinfo;
    private String userphone;
    private String cropsinfo;
    private String cropstime;
    private Integer isNightStartHour;
    private Integer isNightStartMinute;
    private Integer isNightContinueHour;
    private Integer isNightContinueMinute;
    private Integer isNightEndHour;
    private Integer isNightEndMinute;
    private Integer isTimeLong;
    private Integer nHour;
    private Integer nMinutes;
    private Integer nNumber;
    private Integer nRound;
    private String seasonStrat;
    private String seasonEnd;
    private Integer seasonRound;

    public Irrigation() {
    }

    public Irrigation(Long id) {
        this.id = id;
    }

    public Irrigation(Long id, String irrigation, Integer isrelevance, Integer groupnumber, Integer valuenumber, Integer filterHour, Integer filterMinute, String userinfo, String userphone, String cropsinfo, String cropstime, Integer isNightStartHour, Integer isNightStartMinute, Integer isNightContinueHour, Integer isNightContinueMinute, Integer isNightEndHour, Integer isNightEndMinute, Integer isTimeLong, Integer nHour, Integer nMinutes, Integer nNumber, Integer nRound, String seasonStrat, String seasonEnd, Integer seasonRound) {
        this.id = id;
        this.irrigation = irrigation;
        this.isrelevance = isrelevance;
        this.groupnumber = groupnumber;
        this.valuenumber = valuenumber;
        this.filterHour = filterHour;
        this.filterMinute = filterMinute;
        this.userinfo = userinfo;
        this.userphone = userphone;
        this.cropsinfo = cropsinfo;
        this.cropstime = cropstime;
        this.isNightStartHour = isNightStartHour;
        this.isNightStartMinute = isNightStartMinute;
        this.isNightContinueHour = isNightContinueHour;
        this.isNightContinueMinute = isNightContinueMinute;
        this.isNightEndHour = isNightEndHour;
        this.isNightEndMinute = isNightEndMinute;
        this.isTimeLong = isTimeLong;
        this.nHour = nHour;
        this.nMinutes = nMinutes;
        this.nNumber = nNumber;
        this.nRound = nRound;
        this.seasonStrat = seasonStrat;
        this.seasonEnd = seasonEnd;
        this.seasonRound = seasonRound;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIrrigation() {
        return irrigation;
    }

    public void setIrrigation(String irrigation) {
        this.irrigation = irrigation;
    }

    public Integer getIsrelevance() {
        return isrelevance;
    }

    public void setIsrelevance(Integer isrelevance) {
        this.isrelevance = isrelevance;
    }

    public Integer getGroupnumber() {
        return groupnumber;
    }

    public void setGroupnumber(Integer groupnumber) {
        this.groupnumber = groupnumber;
    }

    public Integer getValuenumber() {
        return valuenumber;
    }

    public void setValuenumber(Integer valuenumber) {
        this.valuenumber = valuenumber;
    }

    public Integer getFilterHour() {
        return filterHour;
    }

    public void setFilterHour(Integer filterHour) {
        this.filterHour = filterHour;
    }

    public Integer getFilterMinute() {
        return filterMinute;
    }

    public void setFilterMinute(Integer filterMinute) {
        this.filterMinute = filterMinute;
    }

    public String getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(String userinfo) {
        this.userinfo = userinfo;
    }

    public String getUserphone() {
        return userphone;
    }

    public void setUserphone(String userphone) {
        this.userphone = userphone;
    }

    public String getCropsinfo() {
        return cropsinfo;
    }

    public void setCropsinfo(String cropsinfo) {
        this.cropsinfo = cropsinfo;
    }

    public String getCropstime() {
        return cropstime;
    }

    public void setCropstime(String cropstime) {
        this.cropstime = cropstime;
    }

    public Integer getIsNightStartHour() {
        return isNightStartHour;
    }

    public void setIsNightStartHour(Integer isNightStartHour) {
        this.isNightStartHour = isNightStartHour;
    }

    public Integer getIsNightStartMinute() {
        return isNightStartMinute;
    }

    public void setIsNightStartMinute(Integer isNightStartMinute) {
        this.isNightStartMinute = isNightStartMinute;
    }

    public Integer getIsNightContinueHour() {
        return isNightContinueHour;
    }

    public void setIsNightContinueHour(Integer isNightContinueHour) {
        this.isNightContinueHour = isNightContinueHour;
    }

    public Integer getIsNightContinueMinute() {
        return isNightContinueMinute;
    }

    public void setIsNightContinueMinute(Integer isNightContinueMinute) {
        this.isNightContinueMinute = isNightContinueMinute;
    }

    public Integer getIsNightEndHour() {
        return isNightEndHour;
    }

    public void setIsNightEndHour(Integer isNightEndHour) {
        this.isNightEndHour = isNightEndHour;
    }

    public Integer getIsNightEndMinute() {
        return isNightEndMinute;
    }

    public void setIsNightEndMinute(Integer isNightEndMinute) {
        this.isNightEndMinute = isNightEndMinute;
    }

    public Integer getIsTimeLong() {
        return isTimeLong;
    }

    public void setIsTimeLong(Integer isTimeLong) {
        this.isTimeLong = isTimeLong;
    }

    public Integer getNHour() {
        return nHour;
    }

    public void setNHour(Integer nHour) {
        this.nHour = nHour;
    }

    public Integer getNMinutes() {
        return nMinutes;
    }

    public void setNMinutes(Integer nMinutes) {
        this.nMinutes = nMinutes;
    }

    public Integer getNNumber() {
        return nNumber;
    }

    public void setNNumber(Integer nNumber) {
        this.nNumber = nNumber;
    }

    public Integer getNRound() {
        return nRound;
    }

    public void setNRound(Integer nRound) {
        this.nRound = nRound;
    }

    public String getSeasonStrat() {
        return seasonStrat;
    }

    public void setSeasonStrat(String seasonStrat) {
        this.seasonStrat = seasonStrat;
    }

    public String getSeasonEnd() {
        return seasonEnd;
    }

    public void setSeasonEnd(String seasonEnd) {
        this.seasonEnd = seasonEnd;
    }

    public Integer getSeasonRound() {
        return seasonRound;
    }

    public void setSeasonRound(Integer seasonRound) {
        this.seasonRound = seasonRound;
    }

}
