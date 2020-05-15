package com.justin.hootsuite.testRequests;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.justin.hootsuite.messageVOs.TokenRefreshVO;
import com.justin.hootsuite.messageVOs.TwitterMessageVO;
import com.justin.hootsuite.responseVOs.TokenResponseVO;
import com.siliconmtn.io.http.SMTHttpConnectionManager;

/****************************************************************************
 * <b>Title</b>: HootsuiteTestRequests.java <b>Project</b>: Hootsuite
 * <b>Description: </b> CHANGE ME!! <b>Copyright:</b> Copyright (c) 2020
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author justinjeffrey
 * @version 3.0
 * @since May 11, 2020
 * @updates:
 ****************************************************************************/
public class HootsuiteTestRequests {

	static Logger log = Logger.getLogger(Process.class.getName());
	private String token;
	private String refresh_token;
	private Date tokenExperationDate;

	public static void main(String[] args) throws IOException {
		HootsuiteTestRequests hr = new HootsuiteTestRequests();
		hr.refreshToken();
	}

	private void refreshToken() throws IOException {

		Gson gson = new Gson();
		Map<String, Object> parameters = new HashMap<>();

		SMTHttpConnectionManager cm = new SMTHttpConnectionManager();

		addRefreshTokenHeaders(cm);

		addRefreshTokenParameters(parameters);

		ByteBuffer in = ByteBuffer
				.wrap(cm.retrieveDataViaPost("https://platform.hootsuite.com/oauth2/token", parameters));

		TokenResponseVO response = gson.fromJson(StandardCharsets.UTF_8.decode(in).toString(), TokenResponseVO.class);

		checkRefreshTokenResponse(response);

	}

	/**
	 * Checks if the API response is successful and either logs an error or updates
	 * token values.
	 * 
	 * @param response
	 */
	private void checkRefreshTokenResponse(TokenResponseVO response) {
		// TODO Auto-generated method stub
		if (response.getAccess_token() != null) {
			token = response.getAccess_token();
			refresh_token = response.getRefresh_token();
			Date now = new Date();
			tokenExperationDate = new Date(now.getTime() + (response.getExpires_in() * 1000));
		} else {
			// Log out the error response info
			log.info(response.getError());
			log.info(response.getError_description());
			log.info(response.getError_hint());
			log.info(response.getStatus_code());
		}
	}

	/**
	 * Adds required parameters for the Hootsuite refresh end point
	 * 
	 * @param parameters
	 */
	private void addRefreshTokenParameters(Map<String, Object> parameters) {
		parameters.put("grant_type", "refresh_token");
		parameters.put("refresh_token", refresh_token);
	}

	/**
	 * Adds required headers for the Hootsuite Token refresh end point.
	 * 
	 * @param cm
	 */
	private void addRefreshTokenHeaders(SMTHttpConnectionManager cm) {
		cm.addRequestHeader("Accept", "application/json;charset=utf-8");
		cm.addRequestHeader("Content-Type", "application/x-www-form-urlencoded");
		cm.addRequestHeader("Authorization",
				"Basic YTYwZDA0MzItMzk5OS00YThkLTkxNDAtZjdhNDNmMzNjZjlmOlVac25hcW5mZVo5bA==");
		cm.addRequestHeader("Accept-Encoding", "gzip, deflate, br");
	}

	/**
	 * Return an array of social profiles connected to the hootsuite account
	 * 
	 * @throws IOException
	 */
	public void getSocialProfiles() throws IOException {

		checkToken();

		String str = "";

		SMTHttpConnectionManager cm = new SMTHttpConnectionManager();
		cm.addRequestHeader("Content-Length", "635");
		cm.addRequestHeader("Content-Type", "application/json");
		cm.addRequestHeader("Date", "Mon, 11 May 2020 21:52:48 GMT");
		cm.addRequestHeader("X-Request-Id", "6134fe4a-ba05-459e-b50b-5f545dc59be2");
		cm.addRequestHeader("Authorization", "Bearer " + token);

		try (BufferedReader in = new BufferedReader(new InputStreamReader(
				cm.getConnectionStream("https://platform.hootsuite.com/v1/socialProfiles", null)))) {

			ByteArrayOutputStream s = new ByteArrayOutputStream();
			int i = 0;
			while ((i = in.read()) > -1) {
				s.write(i);
			}
			str = s.toString("UTF-8");
			log.info(str);
		}
	}

	/**
	 * Checks to see if the token is expired and refreshes the token if it is.
	 * 
	 * @throws IOException
	 */
	private void checkToken() throws IOException {
		Date now = new Date();
		if (now.compareTo(tokenExperationDate) > 0) {
			refreshToken();
		}
	}

	/**
	 * postMessage will post a message to the hootsuite api.
	 * 
	 * @throws IOException
	 */
	public void postMessage() throws IOException {

		checkToken();

		Gson gson = new Gson();

		TwitterMessageVO tm = new TwitterMessageVO();

		tm.setDate("2020-6-9T16:15:00Z");
		List<String> socialList = new ArrayList<>();
		socialList.add("131070214");
		tm.setSocialProfiles(socialList);
		tm.setText("Demo post");// This needs to be restricted to a length of 280 characters
		List<String> mediaList = new ArrayList<>();

		byte[] document = gson.toJson(tm).getBytes();
		String newStr = new String(document);
		log.info(newStr);
		String str = "";

		SMTHttpConnectionManager cm = new SMTHttpConnectionManager();
		cm.addRequestHeader("Authorization", "Bearer " + token);
		cm.addRequestHeader("Content-Length", "635");
		cm.addRequestHeader("Content-Type", "application/json");
		cm.addRequestHeader("Date", "Mon, 11 May 2020 21:52:48 GMT");
		cm.addRequestHeader("X-Request-Id", "6134fe4a-ba05-459e-b50b-5f545dc59be2");

		ByteBuffer in = ByteBuffer
				.wrap(cm.postDocument("https://platform.hootsuite.com/v1/messages", document, "application/json"));
		log.info(StandardCharsets.UTF_8.decode(in).toString());
		ByteArrayOutputStream s = new ByteArrayOutputStream();
	}

	/**
	 * getMediaUploadLink will request a link to the Hootsuite AWS file server that
	 * can be used in conjunction with upload image to create a media link for new
	 * message uploads
	 * 
	 * @throws IOException
	 */
	public void getMediaUploadLink() throws IOException {

		checkToken();

	}

	/**
	 * uploadImage will upload a image to the hootsuite AWS file server. this upload
	 * returns a link that can be used when posting message to attach an image to
	 * that message.
	 * 
	 * @throws IOException
	 */
	public void uploadImage() throws IOException {

		checkToken();

	}

	/**
	 * approveMessage set the status of a hootsuite message to approved. --This is
	 * required for messages scheduled through the hootsuite API--
	 * 
	 * @throws IOException
	 */
	public void approveMessage() throws IOException {

		checkToken();

	}
}
