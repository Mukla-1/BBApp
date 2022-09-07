package de.cau.inf.se.sopro.model;

import de.cau.inf.se.sopro.model.GeoData;

public class SubprojectBaseInfoItem {

	private Long subprojectID;
	private String subprojectName;
	private GeoData[] subprojectGeoData;
	
	public SubprojectBaseInfoItem(Long subprojectID, String subprojectName, GeoData[] subprojectGeoData) {
		this.subprojectID = subprojectID;
		this.subprojectName = subprojectName;
		this.subprojectGeoData = subprojectGeoData;
	}

	public Long getSubprojectID() {
		return subprojectID;
	}

	public void setSubprojectID(Long subprojectID) {
		this.subprojectID = subprojectID;
	}

	public String getSubprojectName() {
		return subprojectName;
	}

	public void setSubprojectName(String subprojectName) {
		this.subprojectName = subprojectName;
	}

	public GeoData[] getSubprojectGeoData() {
		return subprojectGeoData;
	}

	public void setSubprojectGeoData(GeoData[] subprojectGeoData) {
		this.subprojectGeoData = subprojectGeoData;
	}
	
	
}
