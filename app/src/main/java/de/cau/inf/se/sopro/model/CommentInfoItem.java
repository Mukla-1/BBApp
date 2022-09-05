package de.cau.inf.se.sopro.model;

public class CommentInfoItem {
	
	private Long commentID;
	private String commentAuthor;
	private String commentContent;
	private int commentLikes;
	private int commentDislikes;
	private int commentNumberAnswers;
	private int commentVote;
	
	public CommentInfoItem(Long commentID, String commentAuthor, String commentContent, int commentLikes,
			int commentDislikes, int commentNumberAnswers, int commentVote) {
		this.commentID = commentID;
		this.commentAuthor = commentAuthor;
		this.commentContent = commentContent;
		this.commentLikes = commentLikes;
		this.commentDislikes = commentDislikes;
		this.commentNumberAnswers = commentNumberAnswers;
		this.commentVote = commentVote;
	}
	
	public Long getCommentID() {
		return commentID;
	}

	public void setCommentID(Long commentID) {
		this.commentID = commentID;
	}

	public String getCommentAuthor() {
		return commentAuthor;
	}

	public void setCommentAuthor(String commentAuthor) {
		this.commentAuthor = commentAuthor;
	}

	public String getCommentContent() {
		return commentContent;
	}

	public void setCommentContent(String commentContent) {
		this.commentContent = commentContent;
	}

	public int getCommentLikes() {
		return commentLikes;
	}

	public void setCommentLikes(int commentLikes) {
		this.commentLikes = commentLikes;
	}

	public int getCommentDislikes() {
		return commentDislikes;
	}

	public void setCommentDislikes(int commentDislikes) {
		this.commentDislikes = commentDislikes;
	}

	public int getCommentNumberAnswers() {
		return commentNumberAnswers;
	}

	public void setCommentNumberAnswers(int commentNumberAnswers) {
		this.commentNumberAnswers = commentNumberAnswers;
	}

	public int getCommentVote() {
		return commentVote;
	}

	public void setCommentVote(int commentVote) {
		this.commentVote = commentVote;
	}

	
}
