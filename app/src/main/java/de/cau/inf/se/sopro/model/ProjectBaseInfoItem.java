package de.cau.inf.se.sopro.model;

public class ProjectBaseInfoItem {

	private Long projectID;
	private String projectName;
	
	public ProjectBaseInfoItem(Long projectID, String projectName) {
		this.projectID = projectID;
		this.projectName = projectName;
	}

	public Long getProjectID() {
		return projectID;
	}

	public void setProjectID(Long projectID) {
		this.projectID = projectID;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
}
