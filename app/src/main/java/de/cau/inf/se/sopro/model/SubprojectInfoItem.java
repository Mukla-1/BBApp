package de.cau.inf.se.sopro.model;

public class SubprojectInfoItem {

	private String subprojectDescription;
	private String subprojectPictureURL;
	
	public SubprojectInfoItem(String subprojectDescription, String subprojectPictureURL) {
		this.subprojectDescription = subprojectDescription;
		this.subprojectPictureURL = subprojectPictureURL;
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
}
