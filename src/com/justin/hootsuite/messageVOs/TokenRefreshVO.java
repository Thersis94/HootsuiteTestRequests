package com.justin.hootsuite.messageVOs;
/****************************************************************************
 * <b>Title</b>: TokenRefreshVO.java
 * <b>Project</b>: Hootsuite
 * <b>Description: </b> CHANGE ME!!
 * <b>Copyright:</b> Copyright (c) 2020
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author justinjeffrey
 * @version 3.0
 * @since May 12, 2020
 * @updates:
 ****************************************************************************/
public class TokenRefreshVO {

	private String grant_type;
	private String refresh_token;
	/**
	 * @return the grant_type
	 */
	public String getGrant_type() {
		return grant_type;
	}
	/**
	 * @param grant_type the grant_type to set
	 */
	public void setGrant_type(String grant_type) {
		this.grant_type = grant_type;
	}
	/**
	 * @return the refresh_token
	 */
	public String getRefresh_token() {
		return refresh_token;
	}
	/**
	 * @param refresh_token the refresh_token to set
	 */
	public void setRefresh_token(String refresh_token) {
		this.refresh_token = refresh_token;
	}
}
