package com.utilhub.openam;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.arnx.jsonic.JSON;


public class Am13RestAPI {
    private static Logger LOG = LoggerFactory.getLogger(Am13RestAPI.class);

    static final String stringEquals = "string=";
	static final String booleanEquals = "boolean=";
	static final String nameEquals = "userdetails.attribute.name=";
	static final String valueEquals = "userdetails.attribute.value=";

	/**
	 * Get the OpenAM token cookie name.
	 * @param baseURL the base url of the OpenAM server, including the protocol, host, and port
	 * @return the name of the session cookie, or the empty string if the name cannot be obtained.
	 */
    public static String getCookieNameForToken(String baseURL) {
    	return "iPlanetDirectoryPro";
    	/*
		String cookieName = doInvoke(null,"GET", baseURL + "/identity/getCookieNameForToken" );
		if (cookieName.startsWith(stringEquals)) {
			cookieName = cookieName.substring(stringEquals.length());
			cookieName = cookieName.trim();
		}
		else cookieName = "";
		return cookieName;
		*/
	}

	/**
	 * Validate a token .
	 * @param baseURL the base url of the OpenAM server, including the protocol, host, and port
	 * @param token the value of the token cookie to validate
	 * @return true if the token is valid; false otherwise.
	 */
    public static boolean isTokenValid(String baseURL, String token) {
        /*
		String result = doInvoke( baseURL + "/identity/isTokenValid", "tokenid="+token );
		if (result.startsWith(booleanEquals)) {
			result = result.substring(booleanEquals.length()).trim();
		}
		return result.toLowerCase().equals("true");
         */
		String result = doInvoke(token,"POST", baseURL + "/json/sessions/"+token+"?_action=validate");
		Map tokenInfo = (Map)JSON.decode(result);
		return ((Boolean)tokenInfo.get("valid"));
	}

    /*
     * Get userid and realm from token.
	 * @param baseURL the base url of the OpenAM server, including the protocol, host, and port
	 * @param token the value of the token cookie to validate
     */
    public static AmUser validate(String baseURL, String token) {
		String result = doInvoke(token,"POST", baseURL + "/json/sessions/"+token+"?_action=validate");
		Map<String,Object> tokenInfo = (Map<String,Object>)JSON.decode(result);
		if ((Boolean)tokenInfo.get("valid")){
			AmUser user = new AmUser();
			user.setUid((String)tokenInfo.get("uid"));
			user.setRealm((String)tokenInfo.get("realm"));
			return user;
		} else {
			return null;
		}
	}

	/**
	 * Get the attributes.associated with a user
	 * @param baseURL the base url of the OpenAM server, including the protocol, host, and port
	 * @param token the value of the token cookie
	 * @return the attributes.
	 */
    public static void detail(String baseURL, String token,AmUser user) {
    	/*
    	String result = doInvoke( baseURL + "/identity/attributes", "subjectid="+token );
    	*/
    	String uid = user.getUid();
		String result = doInvoke( token,"GET",baseURL + "/json/users/"+uid);
		Map<String,Object> userInfo = (Map<String,Object>)JSON.decode(result);
		ArrayList<String> mails = (ArrayList<String>)userInfo.get("mail");
		ArrayList<String> telephoneNumbers = (ArrayList<String>)userInfo.get("telephoneNumber");
		ArrayList<String> givenNames = (ArrayList<String>)userInfo.get("givenName");
		ArrayList<String> sns = (ArrayList<String>)userInfo.get("sn");

		if (mails !=null  && mails.size()>0) {
			user.setMail(mails.get(0));
		}
		if (telephoneNumbers !=null  && telephoneNumbers.size()>0) {
			user.setTelephoneNumber(telephoneNumbers.get(0));
		}
		if (givenNames !=null  && givenNames.size()>0) {
			user.setGivenName(givenNames.get(0));
		}
		if (sns !=null  && sns.size()>0) {
			user.setFamilyName(sns.get(0));
		}
	}

	/**
	 * Convert the attributes string to a hashtable.
	 * @return the attributes in a hashtable
	 */
    public static Hashtable<String,LinkedList<String>> parseAttributes(String attributes) {
		String[] lines = attributes.split("\n");
		String name = null;
		LinkedList<String> values = null;
		Hashtable<String,LinkedList<String>> attrs = new Hashtable<String,LinkedList<String>>();
		for (String line : lines) {
			line = line.trim();
			if (line.startsWith(nameEquals)) {
				name = line.substring(nameEquals.length());
				values = new LinkedList<String>();
				attrs.put(name, values);
			}
			else if (line.startsWith(valueEquals) && (values != null)) {
				values.add(line.substring(valueEquals.length()));
			}
		}
		return attrs;
	}

	/**
	 * Check whether a token has a specific role
	 * @param baseURL the base url of the OpenAM server, including the protocol, host, and port
	 * @param role the role
	 * @param token the value of the token cookie
	 * @return true if the token has the role; false otherwise.
	 */
    public static boolean authorize(String baseURL, String role, String token) {
		String postBody = "uri="+role+"&action=GET&subjectid="+token;
		String result = doInvoke(token, baseURL + "/identity/authorize", postBody );
		if (result.startsWith(booleanEquals)) {
			result = result.substring(booleanEquals.length());
		}
		return result.toLowerCase().equals("true");
	}

	/**
	 * Launch the browser and go to the login page
	 * @param baseURL the base url of the OpenAM server, including the protocol, host, and port
	 * @return the result.
	 */
    public static String login(String baseURL, String redirectURL) {
		return (baseURL + "/UI/Login?goto="+redirectURL);
	}

	/**
	 * Log out - this probably doesn't work
	 * @param baseURL the base url of the OpenAM server, including the protocol, host, and port
	 * @return the result.
	 */
    public static String logout(String baseURL,String token) {
		String result = doInvoke(token,"POST",baseURL + "/openam/UI/Logout" );
		return result;
	}

    protected static String read(InputStream is) throws IOException {
        byte b[] = new byte[is.available()];
        is.read(b);
        return new String(b);
    }
    protected static String doInvoke(String token,String method,String url) {
    	return doInvoke(token,method,url,null);
    }
    protected static String doInvoke(String token,String method,String url,String params) {
        try {
            LOG.debug("Sending : {}", url);
            HttpURLConnection conn = (HttpURLConnection)new URL(url).openConnection();
            conn.setRequestMethod(method);
            //if (params != null) {
    			conn.setRequestProperty("Content-Type", "application/json");
    			conn.setRequestProperty("iplanetDirectoryPro",token);
    			conn.setDoOutput(true);
    			conn.connect();
    			//BufferedWriter  writer = new BufferedWriter( new OutputStreamWriter( conn.getOutputStream(), Charset.forName("UTF-8") ) );
    			//writer.write(params);1	q
    			//writer.flush();
            //}
            //conn.connect();
            String rc = "";
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                rc = read(conn.getInputStream());
            } else {
                rc = read(conn.getErrorStream());
            }
            conn.disconnect();
            LOG.debug("Got : {}", rc);
            return rc;
        } catch (Exception e) {
            LOG.warn("Exception invoking on REST API", e);
            throw new RuntimeException("Exception invoking on REST API : " + e.getMessage());
        }
    }


}
