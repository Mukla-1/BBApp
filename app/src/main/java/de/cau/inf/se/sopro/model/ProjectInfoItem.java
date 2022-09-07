package de.cau.inf.se.sopro.model;

import de.cau.inf.se.sopro.model.GeoData;

public class ProjectInfoItem {

	private String projectDescription;
	private String projectPictureURL;
	private GeoData[] projectGeoData;
	
	public ProjectInfoItem(String projectDescription, String projectPictureURL, GeoData[] projectGeoData) {
		this.projectDescription = projectDescription;
		this.projectPictureURL = projectPictureURL;
		this.projectGeoData = projectGeoData;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
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

	
}
