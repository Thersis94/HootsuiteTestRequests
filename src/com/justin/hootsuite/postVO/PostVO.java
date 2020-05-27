package com.justin.hootsuite.postVO;

import java.util.ArrayList;
import java.util.HashMap;

/****************************************************************************
 * <b>Title</b>: PostVO.java
 * <b>Project</b>: Hootsuite
 * <b>Description: </b> CHANGE ME!!
 * <b>Copyright:</b> Copyright (c) 2020
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author justinjeffrey
 * @version 3.0
 * @since May 22, 2020
 * @updates:
 ****************************************************************************/
public class PostVO {
	
	private String id;
	private String state;
	private String text;
	private String scheduledSendTime;
	private HashMap<String, String> socialProfile;
	private ArrayList<HashMap<String, String>> mediaUrls;
	private String thumbnailUrl;
	private ArrayList<HashMap<String, String>> media;
	private String thumbnailId;
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}
	/**
	 * @param state the state to set
	 */
	public void setState(String state) {
		this.state = state;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return the scheduledSendTime
	 */
	public String getScheduledSendTime() {
		return scheduledSendTime;
	}
	/**
	 * @param scheduledSendTime the scheduledSendTime to set
	 */
	public void setScheduledSendTime(String scheduledSendTime) {
		this.scheduledSendTime = scheduledSendTime;
	}
	/**
	 * @return the socialProfile
	 */
	public HashMap<String, String> getSocialProfile() {
		return socialProfile;
	}
	/**
	 * @param socialProfile the socialProfile to set
	 */
	public void setSocialProfile(HashMap<String, String> socialProfile) {
		this.socialProfile = socialProfile;
	}
	/**
	 * @return the mediaUrls
	 */
	public ArrayList<HashMap<String, String>> getMediaUrls() {
		return mediaUrls;
	}
	/**
	 * @param mediaUrls the mediaUrls to set
	 */
	public void setMediaUrls(ArrayList<HashMap<String, String>> mediaUrls) {
		this.mediaUrls = mediaUrls;
	}
	/**
	 * @return the thumbnailUrl
	 */
	public String getThumbnailUrl() {
		return thumbnailUrl;
	}
	/**
	 * @param thumbnailUrl the thumbnailUrl to set
	 */
	public void setThumbnailUrl(String thumbnailUrl) {
		this.thumbnailUrl = thumbnailUrl;
	}
	/**
	 * @return the media
	 */
	public ArrayList<HashMap<String, String>> getMedia() {
		return media;
	}
	/**
	 * @param media the media to set
	 */
	public void setMedia(ArrayList<HashMap<String, String>> media) {
		this.media = media;
	}
	/**
	 * @return the thumbnailId
	 */
	public String getThumbnailId() {
		return thumbnailId;
	}
	/**
	 * @param thumbnailId the thumbnailId to set
	 */
	public void setThumbnailId(String thumbnailId) {
		this.thumbnailId = thumbnailId;
	}
}
