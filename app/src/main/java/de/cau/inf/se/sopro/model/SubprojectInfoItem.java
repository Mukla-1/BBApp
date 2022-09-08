package de.cau.inf.se.sopro.model;

public class SubprojectInfoItem {

    private String subprojectName;
    private String subprojectDescription;
    private String subprojectPictureURL;
    private GeoData subprojectGeoData;

    public SubprojectInfoItem(String subprojectName, String subprojectDescription, String subprojectPictureURL, GeoData subprojectGeoData) {
        this.subprojectName = subprojectName;
        this.subprojectDescription = subprojectDescription;
        this.subprojectPictureURL = subprojectPictureURL;
        this.subprojectGeoData = subprojectGeoData;
    }

    public String getSubprojectDescription() {
        return subprojectDescription;
    }

    public void setSubprojectDescription(String subprojectDescription) {
        this.subprojectDescription = subprojectDescription;
    }

    public String getSubprojectPictureURL() {
        return subprojectPictureURL;
    }

    public void setSubprojectPictureURL(String subprojectPictureURL) {
        this.subprojectPictureURL = subprojectPictureURL;
    }

    public String getSubprojectName() {
        return subprojectName;
    }

    public void setSubprojectName(String subprojectName) {
        this.subprojectName = subprojectName;
    }

    public GeoData getSubprojectGeoData() {
        return subprojectGeoData;
    }

    public void setSubprojectGeoData(GeoData subprojectGeoData) {
        this.subprojectGeoData = subprojectGeoData;
    }
}
