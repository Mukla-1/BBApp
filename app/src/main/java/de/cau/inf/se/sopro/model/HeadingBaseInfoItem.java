package de.cau.inf.se.sopro.model;

public class HeadingBaseInfoItem {

	private String headingName;
	private Long headingID;
	
	public HeadingBaseInfoItem(String headingName, Long headingID) {
		this.headingName = headingName;
		this.headingID = headingID;
	}

	public String getHeadingName() {
		return headingName;
	}

	public void setHeadingName(String headingName) {
		this.headingName = headingName;
	}

	public Long getHeadingID() {
		return headingID;
	}

	public void setHeadingID(Long headingID) {
		this.headingID = headingID;
	}

}
