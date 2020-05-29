package com.justin.hootsuite.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/****************************************************************************
 * <b>Title</b>: ArticleVO.java
 * <b>Project</b>: Hootsuite
 * <b>Description: </b> VO for holding information required to create a new post for an article
 * <b>Copyright:</b> Copyright (c) 2020
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author justinjeffrey
 * @version 3.0
 * @since May 28, 2020
 * @updates:
 ****************************************************************************/
public class PostVO {
	
	String messageText;
	Date postDate = new Date(); //We need to add 1 day to this at some point
	String mediaId;
	String mimeType;
	String mediaLocation;
	
	public PostVO() {
		messageText = "Java test message in PostVO";

	}

	/**
	 * @return the messageText
	 */
	public String getMessageText() {
		return messageText;
	}

	/**
	 * @param messageText the messageText to set
	 */
	public void setMessageText(String messageText) {
		this.messageText = messageText;
	}

	/**
	 * @return the postDate
	 */
	public Date getPostDate() {
		return postDate;
	}

	/**
	 * @param postDate the postDate to set
	 */
	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	/**
	 * @return the mediaId
	 */
	public String getMediaId() {
		return mediaId;
	}

	/**
	 * @param mediaId the mediaId to set
	 */
	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	/**
	 * @return the mimeType
	 */
	public String getMimeType() {
		return mimeType;
	}

	/**
	 * @param mimeType the mimeType to set
	 */
	public void setMimeType(String mimeType) {
		this.mimeType = mimeType;
	}

	/**
	 * @return the mediaLocation
	 */
	public String getMediaLocation() {
		return mediaLocation;
	}

	/**
	 * @param mediaLocation the mediaLocation to set
	 */
	public void setMediaLocation(String mediaLocation) {
		this.mediaLocation = mediaLocation;
	}
	
	
	
	
}
