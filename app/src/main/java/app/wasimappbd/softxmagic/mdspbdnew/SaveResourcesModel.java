package app.wasimappbd.softxmagic.mdspbdnew;

public class SaveResourcesModel {

    // @JsonProperty("totalform")
    private Integer id;
    private String shelter;
    private String materials;
    private String sdate;


    //@JsonProperty("id")
    public Integer getId() {
        return id;
    }
    //@JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    //@JsonProperty("shelter")
    public String getShelter() {
        return shelter;
    }
    // @JsonProperty("shelter")
    public void setShelter(String shelter) {
        this.shelter = shelter;
    }

    //@JsonProperty("shelter")
    public String getMaterials() {
        return materials;
    }
   // @JsonProperty("shelter")
    public void setMaterials(String materials) {
        this.materials = materials;
    }

    //@JsonProperty("sdate")
    public String getDate() {
        return sdate;
    }

    // @JsonProperty("ufeed1")
    public void setDate(String sdate) {
        this.sdate = sdate;
    }

}