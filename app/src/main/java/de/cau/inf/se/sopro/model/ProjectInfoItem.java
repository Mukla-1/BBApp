package de.cau.inf.se.sopro.model;

import de.cau.inf.se.sopro.model.GeoData;

public class ProjectInfoItem {

    private String projectName;
    private String projectPictureURL;
    private String projectDescription;
    private GeoData[] projectGeoData;

    public ProjectInfoItem(String projectName, String projectPictureURL, String projectDescription, GeoData[] projectGeoData) {
        this.projectName = projectName;
        this.projectPictureURL = projectPictureURL;
        this.projectDescription = projectDescription;
        this.projectGeoData = projectGeoData;
    }

    public String getProjectPictureURL() {
        return projectPictureURL;
    }

    public void setProjectPictureURL(String projectPictureURL) {
        this.projectPictureURL = projectPictureURL;
    }

    public GeoData[] getProjectGeoData() {
        return projectGeoData;
    }

    public void setProjectGeoData(GeoData[] projectGeoData) {
        this.projectGeoData = projectGeoData;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() { return projectDescription; }

    public void setProjectDescription(String projectDescription) { this.projectDescription = projectDescription; }
}


