package com.justin.hootsuite.responseVOs;

import java.util.ArrayList;

import com.justin.hootsuite.socialMediaProfileVOs.SocialMediaProfileVO;

/****************************************************************************
 * <b>Title</b>: SchedulePostResponseVO.java
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
public class SchedulePostResponseVO {

	ArrayList<SocialMediaProfileVO> data = new ArrayList<>();

	/**
	 * @return the data
	 */
	public ArrayList<SocialMediaProfileVO> getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public void setData(ArrayList<SocialMediaProfileVO> data) {
		this.data = data;
	}
	
	public String getId() {
		return data.get(0).getId();
	}
	
}
