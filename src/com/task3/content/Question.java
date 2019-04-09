package com.task3.content;

import java.util.Date;


public class Question extends Content implements Comparable<Question>{
	private static final long serialVersionUID = 1338186921647326731L;
	private String id = null;
	private String name = null;
	private String email = null;
	private String title = null;
	private String description = null;
	private Date postDate = null;
	private Date modifiedDate = null;
	private Date updateDate = null;
	private Boolean modified = null;
	private String answerId = null;
	public Question(){

	}
	@Override
	public int compareTo(Question question){
		Date thisDate = this.getUpdateDate();
		Date thatDate = question.getUpdateDate();
		int result = thisDate.compareTo(thatDate);
		return -result;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public boolean isModified() {
		return modified;
	}
	public void setModified(boolean modified) {
		this.modified = modified;
	}
	public String getAnswerId() {
		return answerId;
	}
	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
}
