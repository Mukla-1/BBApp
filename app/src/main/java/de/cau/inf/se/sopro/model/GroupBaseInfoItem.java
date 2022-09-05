package de.cau.inf.se.sopro.model;

public class GroupBaseInfoItem {

	private String groupName;
	private Long groupID;
	
	public GroupBaseInfoItem(String groupName, Long groupID) {
		this.groupName = groupName;
		this.groupID = groupID;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Long getGroupID() {
		return groupID;
	}

	public void setGroupID(Long groupID) {
		this.groupID = groupID;
	}
	
	
}
