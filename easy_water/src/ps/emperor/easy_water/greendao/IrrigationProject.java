package ps.emperor.easy_water.greendao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table IRRIGATION_PROJECT.
 */
public class IrrigationProject {

    private Long id;
    private String irrigation;
    private String round;
    private String marshalling;
    private String projectstart;
    private String projectduration;
    private String projectend;
    private String projectrest;
    private Integer modification;
    private Integer projectsole;

    public IrrigationProject() {
    }

    public IrrigationProject(Long id) {
        this.id = id;
    }

    public IrrigationProject(Long id, String irrigation, String round, String marshalling, String projectstart, String projectduration, String projectend, String projectrest, Integer modification, Integer projectsole) {
        this.id = id;
        this.irrigation = irrigation;
        this.round = round;
        this.marshalling = marshalling;
        this.projectstart = projectstart;
        this.projectduration = projectduration;
        this.projectend = projectend;
        this.projectrest = projectrest;
        this.modification = modification;
        this.projectsole = projectsole;
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

    public String getRound() {
        return round;
    }

    public void setRound(String round) {
        this.round = round;
    }

    public String getMarshalling() {
        return marshalling;
    }

    public void setMarshalling(String marshalling) {
        this.marshalling = marshalling;
    }

    public String getProjectstart() {
        return projectstart;
    }

    public void setProjectstart(String projectstart) {
        this.projectstart = projectstart;
    }

    public String getProjectduration() {
        return projectduration;
    }

    public void setProjectduration(String projectduration) {
        this.projectduration = projectduration;
    }

    public String getProjectend() {
        return projectend;
    }

    public void setProjectend(String projectend) {
        this.projectend = projectend;
    }

    public String getProjectrest() {
        return projectrest;
    }

    public void setProjectrest(String projectrest) {
        this.projectrest = projectrest;
    }

    public Integer getModification() {
        return modification;
    }

    public void setModification(Integer modification) {
        this.modification = modification;
    }

    public Integer getProjectsole() {
        return projectsole;
    }

    public void setProjectsole(Integer projectsole) {
        this.projectsole = projectsole;
    }

}
