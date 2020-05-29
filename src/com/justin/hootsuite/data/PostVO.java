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
	
	public PostVO() {
		messageText = "Java test message in PostVO";

	}
	
	
	
	
}
