package com.justin.hootsuite.testRequests;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.justin.hootsuite.messageVOs.MediaLinkRequestVO;
import com.justin.hootsuite.messageVOs.TokenRefreshVO;
import com.justin.hootsuite.messageVOs.ScheduleMessageVO;
import com.justin.hootsuite.responseVOs.MediaLinkResponseVO;
import com.justin.hootsuite.responseVOs.MediaUploadStatusResponseVO;
import com.justin.hootsuite.responseVOs.TokenResponseVO;
import com.justin.hootsuite.socialMediaProfileVOs.SocialMediaProfilesVO;
import com.siliconmtn.io.http.SMTHttpConnectionManager;
import com.siliconmtn.io.http.SMTHttpConnectionManager.HttpConnectionType;

/****************************************************************************
 * <b>Title</b>: HootsuiteTestRequests.java <b>Project</b>: Hootsuite
 * <b>Description: </b> Class for developing Hootsuite test requests <b>Copyright:</b> Copyright (c) 2020
 * <b>Company:</b> Silicon Mountain Technologies
 * 
 * @author justinjeffrey
 * @version 3.0
 * @since May 11, 2020
 * @updates:
 ****************************************************************************/
public class HootsuiteTestRequests {

	static Logger log = Logger.getLogger(Process.class.getName());
	private String token = "q34VM9PMvM8kGwFCDdBZ737wOgkLRWv5_vWxFgudbN0.H3AsfMDdqS-xwRqpr6QoPvS1sZaSWWNio4YTf1Er9Ws"; // Stored in a db?
	private String refresh_token = "-9kNN7dzTg3ttdJYgKZO7iXdyyrf7ykXZ9yHV-4-liI.dkOPbYLOemH1yZWPWs2K5Bvvb03UEWT61RoImHHlIWc"; // Stored in a db?
	private Date tokenExperationDate = new Date(); // Stored in a db?
	

	public static void main(String[] args) throws IOException {
		HootsuiteTestRequests hr = new HootsuiteTestRequests();
		
//		log.info(hr.getSocialProfiles());
		
//		hr.refreshToken();
		
//		hr.getMediaUploadLink("image/jpeg", 11501);
		
//		log.info(hr.retrieveMediaUploadStatus("aHR0cHM6Ly9ob290c3VpdGUtdmlkZW8uczMuYW1hem9uYXdzLmNvbS9wcm9kdWN0aW9uLzIxODQ1ODMxX2UyNGM3MDNiLTAxYzMtNDRkZi1hY2M0LTBiYTM2NDRlNzc0NC5qcGc="));
		
		
		
//		HashMap<String, String> socialProfilesMap = hr.getSocialProfiles();
//		String messageText = "Java Test message.";
//		ArrayList<String> socialProfiles = new ArrayList<>();
//		socialProfiles.add(socialProfilesMap.get("TWITTER"));
//		String postDate = "2020-6-9T16:15:00Z";
//		hr.postManager(hr, messageText, socialProfiles, postDate);

	}

	private void postManager(HootsuiteTestRequests hr, String messageText, ArrayList<String> socialProfiles, String postDate) {
		
		try {
			hr.schedulePost(socialProfiles, postDate, messageText);
		} catch (Exception e) {
			log.info(e);
		}
	}
	
	private void postManager(HootsuiteTestRequests hr, String message, ArrayList<String> socialProfiles, String postDate, String mediaLocation) {
		
		
		
	}

	/**
	 * Requests a new set of tokens from the Hootsuite Api refresh token endpoint
	 * 
	 * @throws IOException
	 */
	private void refreshToken() throws IOException {

		Gson gson = new Gson();
		Map<String, Object> parameters = new HashMap<>();

		SMTHttpConnectionManager cm = new SMTHttpConnectionManager();

		addRefreshTokenHeaders(cm);

		addRefreshTokenParameters(parameters);
		
		HttpConnectionType post = HttpConnectionType.POST;

		// Send post request
		ByteBuffer in = ByteBuffer
				.wrap(cm.getRequestData("https://platform.hootsuite.com/oauth2/token", parameters, post));

		// Capture the response
		TokenResponseVO response = gson.fromJson(StandardCharsets.UTF_8.decode(in).toString(), TokenResponseVO.class);
		
		log.info(gson.toJson(response).toString()); // Remove when the tokens are stored to a database.
		
		checkRefreshTokenResponse(response);

	}

	/**
	 * Checks if the API response is successful and either logs an error or updates
	 * token values.
	 * 
	 * @param response
	 */
	private void checkRefreshTokenResponse(TokenResponseVO response) {
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
	public HashMap<String, String> getSocialProfiles() throws IOException {

//		checkToken();
		
		Gson gson = new Gson();
		
		Map<String, Object> parameters = new HashMap<>();

		SMTHttpConnectionManager cm = new SMTHttpConnectionManager();

		addGetSocialProfilesHeaders(cm);
		
		HttpConnectionType get = HttpConnectionType.GET;

		// Send post request
				ByteBuffer in = ByteBuffer
						.wrap(cm.getRequestData("https://platform.hootsuite.com/v1/socialProfiles", parameters, get));

				SocialMediaProfilesVO response = gson.fromJson(StandardCharsets.UTF_8.decode(in).toString(), SocialMediaProfilesVO.class);
				HashMap<String, String> socialProfiles = response.getAllSocialIds();
				
				return socialProfiles;
	}

	/**
	 * Adds required headers for the Hootsuite get social profiles end point.
	 * 
	 * @param cm
	 */
	private void addGetSocialProfilesHeaders(SMTHttpConnectionManager cm) {
		cm.addRequestHeader("Content-Type", "type:application/json;charset=utf-8");
		cm.addRequestHeader("Authorization", "Bearer " + token);
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
	private void schedulePost(ArrayList<String> socialIdList, String scheduledSendTime, String messageText) throws IOException {

//		checkToken();
		Map<String, String> mediaIds = new HashMap<>();
		
		// Change this to the media id that is going to be passed to this method
		mediaIds.put("id", "aHR0cHM6Ly9ob290c3VpdGUtdmlkZW8uczMuYW1hem9uYXdzLmNvbS9wcm9kdWN0aW9uLzIxODQ1ODMxXzE4ZmJhYjIyLTQ5ZjgtNGY2My1iMDExLTUzNjFmNjc1ZTNkMC5qcGc=");
		
		List<Map<String, String>> mediaList = new ArrayList<>();
		mediaList.add(mediaIds);

		Gson gson = new Gson();

		ScheduleMessageVO message = new ScheduleMessageVO();
		message.setDate(scheduledSendTime);
		message.setSocialProfiles(socialIdList);
		message.setText(messageText);// This needs to be restricted to a length of 280 characters for twitter
		message.setMedia(mediaList);

		byte[] document = gson.toJson(message).getBytes();
		
		// Loggers to test document format. Delete me
//		String newStr = new String(document);
//		log.info(newStr);

		SMTHttpConnectionManager cm = new SMTHttpConnectionManager();
		
		addSchedulePostHeaders(cm);

		ByteBuffer in = ByteBuffer
				.wrap(cm.sendBinaryData("https://platform.hootsuite.com/v1/messages", document, "application/json", HttpConnectionType.POST));
		log.info(StandardCharsets.UTF_8.decode(in).toString());
		
		// Build the response bean
		
	}

	/**
	 * Adds headers for the schedule post request
	 * @param cm receives connection manager
	 */
	private void addSchedulePostHeaders(SMTHttpConnectionManager cm) {
		cm.addRequestHeader("Authorization", "Bearer " + token);
	}

	/**
	 * getMediaUploadLink will request a link to the Hootsuite AWS file server that
	 * can be used in conjunction with upload image to create a media link for new
	 * message uploads
	 * 
	 * @throws IOException
	 */
	public void getMediaUploadLink(String mimeType, int sizeBytes) throws IOException {

//		checkToken();

		
		Gson gson = new Gson();

		SMTHttpConnectionManager cm = new SMTHttpConnectionManager();
		cm.addRequestHeader("Authorization", "Bearer " + token);
		cm.addRequestHeader("accept-encoding", "gzip, deflate, br");
		
		MediaLinkRequestVO mlr = new MediaLinkRequestVO();
		mlr.setMimeType(mimeType);
		mlr.setSizeBytes(sizeBytes);

		byte[] document = gson.toJson(mlr).getBytes();
		
		ByteBuffer in = ByteBuffer
				.wrap(cm.sendBinaryData("https://platform.hootsuite.com/v1/media", document, "application/json", HttpConnectionType.POST));

		MediaLinkResponseVO response = gson.fromJson(StandardCharsets.UTF_8.decode(in).toString(),
				MediaLinkResponseVO.class);
 
		
		if (response.successfulRequest()) {
			uploadMedia(response, mlr);
		} else {
			log.info("checkMediaLinkResponse returned false");
			log.info(response.getError() + " : " + response.getError_description());
		}
		
		if(retrieveMediaUploadStatus(response.getId())) {
			log.info("Media successfully uploaded.");
		}
	}

	/**
	 * uploadImage will upload a image to the hootsuite AWS file server. this upload
	 * returns a link that can be used when posting message to attach an image to
	 * that message.
	 * 
	 * @param response
	 * @param mlr
	 * 
	 * @throws IOException
	 */
	public void uploadMedia(MediaLinkResponseVO response, MediaLinkRequestVO mlr) throws IOException {
		
		String errorMessage = "";

		SMTHttpConnectionManager cm = new SMTHttpConnectionManager();
		
		// Using a file path for now. Later this will need to be changed to whatever
		// format Webcrescendo uses
		byte[] bytesArr = Files.readAllBytes(Paths.get("/home/justinjeffrey/Downloads/demoImg.jpeg"));
		
		// Build the put request using the response url value
		ByteBuffer in = ByteBuffer
				.wrap(cm.sendBinaryData(response.getUploadUrl(), bytesArr, mlr.getMimeType(), HttpConnectionType.PUT));
		
		errorMessage = StandardCharsets.UTF_8.decode(in).toString();
		
		if(errorMessage.length() > 0) {
			log.info("uploadMedia response code: " + cm.getResponseCode());
			log.info("uploadMedia error message: " + errorMessage);
		}
	}
	
	/**
	 * Checks the upload status of a media file to the Hootsuite/Amazon AWS server
	 * @return the boolean status of the upload
	 * @throws IOException 
	 */
	public boolean retrieveMediaUploadStatus(String mediaId) throws IOException {
		
//		checkToken();
		
		Gson gson = new Gson();
		
		SMTHttpConnectionManager cm = new SMTHttpConnectionManager();
		cm.addRequestHeader("Authorization", "Bearer " + token);
		
		Map<String, Object> parameters = new HashMap<>();
		
		HttpConnectionType get = HttpConnectionType.GET;
		
		ByteBuffer in = ByteBuffer
				.wrap(cm.getRequestData("https://platform.hootsuite.com/v1/media/" + mediaId, parameters, get));

//		log.info(StandardCharsets.UTF_8.decode(in).toString()); // Logger to check response. Remove in production.
		
		MediaUploadStatusResponseVO response = gson.fromJson(StandardCharsets.UTF_8.decode(in).toString(), MediaUploadStatusResponseVO.class);
		
		if(response.getState() != null && response.getState().equalsIgnoreCase("READY")) {
			return true;
		} else {
			return false;
		}
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