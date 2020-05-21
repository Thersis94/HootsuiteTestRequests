package com.justin.hootsuite.socialMediaProfileVOs;

import java.util.ArrayList;
import java.util.HashMap;

/****************************************************************************
 * <b>Title</b>: SocialMediaProfiles.java
 * <b>Project</b>: Hootsuite
 * <b>Description: </b> CHANGE ME!!
 * <b>Copyright:</b> Copyright (c) 2020
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author justinjeffrey
 * @version 3.0
 * @since May 19, 2020
 * @updates:
 ****************************************************************************/
public class SocialMediaProfilesVO {
	
	ArrayList<SocialMediaProfileVO> data = new ArrayList<>();
	
	/**
	 * Returns the social profile id of the profile name passed into it.
	 * @param name of the social profile you would like the id for
	 * @return the id of the social profile you requested
	 */
	public String getIdByName(String name) {
		String id = "";
		for (SocialMediaProfileVO profile: data) {
			if(profile.getType().equalsIgnoreCase(name)) {
				id = profile.getId();
			}
		}
		return id;
	}
	
	public HashMap<String, String> getAllSocialIds() {
		HashMap<String, String> ids = new HashMap<>();
		for (SocialMediaProfileVO profile: data) {
			ids.put(profile.getType(), profile.getId());
		}
		return ids;
	}
}