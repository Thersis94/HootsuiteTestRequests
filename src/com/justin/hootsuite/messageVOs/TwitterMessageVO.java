package com.justin.hootsuite.messageVOs;

import java.util.ArrayList;
import java.util.List;

/****************************************************************************
 * <b>Title</b>: TwitterMessageVO.java
 * <b>Project</b>: Hootsuite
 * <b>Description: </b> CHANGE ME!!
 * <b>Copyright:</b> Copyright (c) 2020
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author justinjeffrey
 * @version 3.0
 * @since May 11, 2020
 * @updates:
 ****************************************************************************/
public class TwitterMessageVO {

	private String text;
	private List<String> socialProfileIds = new ArrayList<>();
	private String scheduledSendTime;
	private List<String> media = new ArrayList<>();
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
	 * @return the socialProfiles
	 */
	public List<String> getSocialProfiles() {
		return socialProfileIds;
	}
	/**
	 * @param socialProfiles the socialProfiles to set
	 */
	public void setSocialProfiles(List<String> socialProfiles) {
		this.socialProfileIds = socialProfiles;
	}
	/**
	 * @return the date
	 */
	public String getDate() {
		return scheduledSendTime;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.scheduledSendTime = date;
	}
	/**
	 * @return the media
	 */
	public List<String> getMedia() {
		return media;
	}
	/**
	 * @param media the media to set
	 */
	public void setMedia(List<String> media) {
		this.media = media;
	}
	
	
	
	
}
