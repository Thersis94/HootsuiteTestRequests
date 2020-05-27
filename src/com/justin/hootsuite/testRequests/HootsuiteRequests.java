package com.justin.hootsuite.testRequests;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.justin.hootsuite.messageVOs.ApproveMessageVO;
import com.justin.hootsuite.messageVOs.MediaLinkRequestVO;
import com.justin.hootsuite.messageVOs.ScheduleMessageVO;
import com.justin.hootsuite.responseVOs.MediaLinkResponseVO;
import com.justin.hootsuite.responseVOs.MediaUploadStatusResponseVO;
import com.justin.hootsuite.responseVOs.SchedulePostResponseVO;
import com.justin.hootsuite.responseVOs.TokenResponseVO;
import com.justin.hootsuite.socialMediaProfileVOs.SocialMediaProfilesVO;
import com.siliconmtn.io.http.SMTHttpConnectionManager;
import com.siliconmtn.io.http.SMTHttpConnectionManager.HttpConnectionType;

/****************************************************************************
 * <b>Title</b>: HootsuiteTestRequests.java <b>Project</b>: Hootsuite
 * <b>Description: </b> Class for developing Hootsuite test requests
 * <b>Copyright:</b> Copyright (c) 2020 <b>Company:</b> Silicon Mountain
 * Technologies
 * 
 * @author justinjeffrey
 * @version 3.0
 * @since May 11, 2020
 * @updates:
 ****************************************************************************/
public class HootsuiteRequests {

	static Logger log = Logger.getLogger(Process.class.getName());
	private String token = "vIiu6n28yrIP_UzJg70zl40_ZdeliJYpkIKniZmNtEk.jmFvIgoUOaVqR7F6k9rfnWmDnUQTTJsHhpZK4TIurGg";
	private String refresh_token = "zBFB_hovBaUIK-vq1J9BnQolL-W-4QcNtgzmh-3-fGI.5Ha1ccFQ62P22ugAJSDW7Onzs2jEr_wHk7pxrCptdu0";
	private Date tokenExperationDate = new Date();

	public static void main(String[] args) throws IOException, InterruptedException {
		HootsuiteRequests hr = new HootsuiteRequests();

		HashMap<String, String> socialProfilesMap = hr.getSocialProfiles();

		String messageText = "Java Test message.";

		ArrayList<String> socialProfiles = new ArrayList<>();

		socialProfiles.add(socialProfilesMap.get("TWITTER"));

		String postDate = "2020-6-27T20:00:00Z";

		hr.postMessageWithMedia(hr, messageText, socialProfiles, postDate, "/home/justinjeffrey/Downloads/demoImg.jpeg",
				"image/jpeg");

	}

	/**
	 * Post a message using the hootsuite api.
	 * 
	 * @param hr             an instance of HootsuiteRequests.
	 * @param messageText    a String with the text you would like to appear in the
	 *                       message. (Carriage returns can be used to format the
	 *                       message.)
	 * @param socialProfiles a ArrayList of socialProfile ids.
	 * @param postDate       ISO 8601 formatted String containing the date to post
	 *                       the message. ("YYYY-M-DTHH:MM:SSZ")
	 *                       https://en.wikipedia.org/wiki/ISO_8601
	 */
	public void postMessage(HootsuiteRequests hr, String messageText, ArrayList<String> socialProfiles,
			String postDate) {
		String postId = "";
		String mediaId = "";
		
		try {
			postId = hr.schedulePost(socialProfiles, postDate, messageText, mediaId);

//			hr.approveMessage(postId); No longer sure these are needed
		} catch (Exception e) {
			log.info(e);
		}
	}

	/**
	 * Post a message with media using the hootsuite api.
	 * 
	 * @param hr             an instance of HootsuiteRequests.
	 * @param messageText    a String with the text you would like to appear in the
	 *                       message. (Carriage returns can be used to format the
	 *                       message.)
	 * @param socialProfiles a ArrayList of socialProfile ids.
	 * @param postDate       ISO 8601 formatted String containing the date to post
	 *                       the message. ("YYYY-M-DTHH:MM:SSZ")
	 *                       https://en.wikipedia.org/wiki/ISO_8601
	 * @param mediaLocation  String formatted path to the media that is being
	 *                       uploaded
	 * @param mimeType       String formatted mimeType. ("image/jpeg")
	 * @param sizeBytes      media byte size.
	 */
	public void postMessageWithMedia(HootsuiteRequests hr, String messageText, ArrayList<String> socialProfiles,
			String postDate, String mediaLocation, String mimeType) {
		String postId = "";
		String mediaId = "";
		
		try {
			mediaId = hr.uploadHootsuiteMedia(mimeType, mediaLocation);
			postId = hr.schedulePost(socialProfiles, postDate, messageText, mediaId);
//			hr.approveMessage(postId); No longer sure these are needed
		} catch (Exception e) {
			log.info(e);
		}
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

		SocialMediaProfilesVO response = gson.fromJson(StandardCharsets.UTF_8.decode(in).toString(),
				SocialMediaProfilesVO.class);
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
	 * Schedules a social media post using the hootsuite api
	 * 
	 * @param socialIdList      List of social media ids
	 * @param scheduledSendTime ISO 8601 formatted String containing the date to
	 *                          post the message. ("YYYY-M-DTHH:MM:SSZ")
	 *                          https://en.wikipedia.org/wiki/ISO_8601
	 * @param messageText       a String with the text you would like to appear in
	 *                          the message. (Carriage returns can be used to format
	 *                          the message.)
	 * @param mediaId           the id returned when uploading media using the
	 *                          hootsuite api
	 * @return The id of the scheduled pot
	 * @throws IOException
	 */
	private String schedulePost(ArrayList<String> socialIdList, String scheduledSendTime, String messageText,
			String mediaId) throws IOException {

//		checkToken();

		Map<String, String> mediaIds = new HashMap<>();

		mediaIds.put("id", mediaId);

		List<Map<String, String>> mediaList = new ArrayList<>();
		mediaList.add(mediaIds);

		Gson gson = new Gson();

		ScheduleMessageVO message = new ScheduleMessageVO();

		setMessageContent(message, scheduledSendTime, socialIdList, messageText, mediaList);

		byte[] document = gson.toJson(message).getBytes();

		SMTHttpConnectionManager cm = new SMTHttpConnectionManager();
		cm.addRequestHeader("Authorization", "Bearer " + token);

		ByteBuffer in = ByteBuffer.wrap(cm.sendBinaryData("https://platform.hootsuite.com/v1/messages", document,
				"application/json", HttpConnectionType.POST));

		SchedulePostResponseVO response = gson.fromJson(StandardCharsets.UTF_8.decode(in).toString(),
				SchedulePostResponseVO.class);

		return response.getId();
	}

	/**
	 * Sets the parameters to the values of the ScheduleMessageVO
	 * 
	 * @param message
	 * @param scheduledSendTime
	 * @param socialIdList
	 * @param messageText
	 * @param mediaList
	 */
	private void setMessageContent(ScheduleMessageVO message, String scheduledSendTime, ArrayList<String> socialIdList,
			String messageText, List<Map<String, String>> mediaList) {
		message.setDate(scheduledSendTime);
		message.setSocialProfiles(socialIdList);
		message.setText(messageText);// This needs to be restricted to a length of 280 characters for twitter
		message.setMedia(mediaList);
	}

	/**
	 * getMediaUploadLink will request a link to the Hootsuite AWS file server that
	 * can be used in conjunction with upload image to create a media link for new
	 * message uploads
	 * 
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private String uploadHootsuiteMedia(String mimeType, String path) throws IOException, InterruptedException {

		String mediaId = "";

//		checkToken();

		Gson gson = new Gson();

		SMTHttpConnectionManager cm = new SMTHttpConnectionManager();
		cm.addRequestHeader("Authorization", "Bearer " + token);

		MediaLinkRequestVO mlr = new MediaLinkRequestVO();
		mlr.setMimeType(mimeType);

		byte[] document = gson.toJson(mlr).getBytes();

		ByteBuffer in = ByteBuffer.wrap(cm.sendBinaryData("https://platform.hootsuite.com/v1/media", document,
				"application/json", HttpConnectionType.POST));

		MediaLinkResponseVO response = gson.fromJson(StandardCharsets.UTF_8.decode(in).toString(),
				MediaLinkResponseVO.class);

		if (response.successfulRequest()) {
			uploadMediaToAWS(response, mlr, path);
			mediaId = response.getId();
		} else {
			log.info("checkMediaLinkResponse returned false");
			log.info(response.getError() + " : " + response.getError_description());
		}

		waitForSuccessfulUpload(response);

		return mediaId;
	}

	/**
	 * Loops the retrieveMediaUploadStatus until the media has been successfully
	 * uploaded to the AWS server
	 * 
	 * @param response the response from hootsuite media link request
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private void waitForSuccessfulUpload(MediaLinkResponseVO response) throws IOException, InterruptedException {

		// Find a better way to do this!
		int timeOut = 0;
		while (!retrieveMediaUploadStatus(response.getId())) {
			timeOut++;
			if (timeOut > 10) {
				log.info("Upload Failed");
				break;
			}
			Thread.sleep(1000);
			log.info("Waiting for upload to complete.");
		}

		if (retrieveMediaUploadStatus(response.getId())) {
			log.info("Media successfully uploaded.");
		} else {
			log.info("Media still uploading.");
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
	private void uploadMediaToAWS(MediaLinkResponseVO response, MediaLinkRequestVO mlr, String path)
			throws IOException {

		String errorMessage = "";

		SMTHttpConnectionManager cm = new SMTHttpConnectionManager();

		// Using a file path for now. Later this will need to be changed to whatever
		// format Webcrescendo uses
		byte[] bytesArr = Files.readAllBytes(Paths.get(path));

		// Build the put request using the response url value
		ByteBuffer in = ByteBuffer
				.wrap(cm.sendBinaryData(response.getUploadUrl(), bytesArr, mlr.getMimeType(), HttpConnectionType.PUT));

		errorMessage = StandardCharsets.UTF_8.decode(in).toString();

		if (errorMessage.length() > 0) {
			log.info("uploadMedia response code: " + cm.getResponseCode());
			log.info("uploadMedia error message: " + errorMessage);
		}
	}

	/**
	 * Checks the upload status of a media file to the Hootsuite/Amazon AWS server
	 * 
	 * @return the boolean status of the upload
	 * @throws IOException
	 */
	private boolean retrieveMediaUploadStatus(String mediaId) throws IOException {

//		checkToken();

		Gson gson = new Gson();

		SMTHttpConnectionManager cm = new SMTHttpConnectionManager();
		cm.addRequestHeader("Authorization", "Bearer " + token);

		Map<String, Object> parameters = new HashMap<>();

		HttpConnectionType get = HttpConnectionType.GET;

		ByteBuffer in = ByteBuffer
				.wrap(cm.getRequestData("https://platform.hootsuite.com/v1/media/" + mediaId, parameters, get));

		MediaUploadStatusResponseVO response = gson.fromJson(StandardCharsets.UTF_8.decode(in).toString(),
				MediaUploadStatusResponseVO.class);

		if (response.getState() != null && response.getState().equalsIgnoreCase("READY"))
			return true;
		else
			return false;
	}

	/**
	 * approveMessage set the status of a hootsuite message to approved. --This is
	 * required for messages scheduled through the hootsuite API--
	 * 
	 * @throws IOException
	 */
	private void approveMessage(String messageId) throws IOException {

//		checkToken();

		Gson gson = new Gson();

		SMTHttpConnectionManager cm = new SMTHttpConnectionManager();
		cm.addRequestHeader("Authorization", "Bearer " + token);

		ApproveMessageVO message = new ApproveMessageVO();
		message.setSequenceNumber(11);

		byte[] document = gson.toJson(message).getBytes();

		HttpConnectionType post = HttpConnectionType.POST;

		ByteBuffer in = ByteBuffer
				.wrap(cm.sendBinaryData("https://platform.hootsuite.com/v1/messages/" + messageId + "/approve",
						document, "application/json", post));

		log.info("approveMessage response." + StandardCharsets.UTF_8.decode(in).toString()); // Logger to check
																								// response. Remove in
																								// production.

//		ApproveMessageResponseVO response = gson.fromJson(StandardCharsets.UTF_8.decode(in).toString(), ApproveMessageResponseVO.class);

	}
}