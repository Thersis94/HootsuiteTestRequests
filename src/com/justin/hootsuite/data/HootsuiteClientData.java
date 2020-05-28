package com.justin.hootsuite.data;

import java.util.HashMap;
import java.util.Map;

/****************************************************************************
 * <b>Title</b>: HootsuiteClientData.java
 * <b>Project</b>: Hootsuite
 * <b>Description: </b> CHANGE ME!!
 * <b>Copyright:</b> Copyright (c) 2020
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author justinjeffrey
 * @version 3.0
 * @since May 28, 2020
 * @updates:
 ****************************************************************************/
public class HootsuiteClientData {
	

	private Map<String, String> socialProfiles = new HashMap<>();
	
	
	
	HootsuiteClientData() {
		socialProfiles.put("TWITTER", "131070214");
		socialProfiles.put("FACEBOOK", "130968924");
		
		
	}
	
}
