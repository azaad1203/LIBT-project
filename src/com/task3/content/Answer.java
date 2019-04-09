package com.task3.content;

import java.util.Date;

public class Answer extends Content{
	private static final long serialVersionUID = 6526328224435798429L;
	private String id;
	private String description;
	private Date postDate;
	private Date modifiedDate = null;
	private Boolean modified;
	public Answer(){

	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getPostDate() {
		return postDate;
	}
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean isModified() {
		return modified;
	}
	public void setModified(Boolean modified) {
		this.modified = modified;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}
