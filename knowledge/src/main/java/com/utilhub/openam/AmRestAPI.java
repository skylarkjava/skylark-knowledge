package com.utilhub.openam;

import java.io.*;
import java.net.*;
import java.nio.charset.Charset;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmRestAPI {
    private static Logger LOG = LoggerFactory.getLogger(AmRestAPI.class);

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
		String cookieName = doInvoke( baseURL + "/identity/getCookieNameForToken" );
		if (cookieName.startsWith(stringEquals)) {
			cookieName = cookieName.substring(stringEquals.length());
			cookieName = cookieName.trim();
		}
		else cookieName = "";
		return cookieName;
	}

	/**
	 * Validate a token.
	 * @param baseURL the base url of the OpenAM server, including the protocol, host, and port
	 * @param token the value of the token cookie to validate
	 * @return true if the token is valid; false otherwise.
	 */
    public static boolean isTokenValid(String baseURL, String token) {
		String result = doInvoke( baseURL + "/identity/isTokenValid", "tokenid="+token );
		if (result.startsWith(booleanEquals)) {
			result = result.substring(booleanEquals.length()).trim();
		}
		return result.toLowerCase().equals("true");
	}

	/**
	 * Get the attributes.associated with a token
	 * @param baseURL the base url of the OpenAM server, including the protocol, host, and port
	 * @param token the value of the token cookie
	 * @return the attributes.
	 */
    public static String getAttributes(String baseURL, String token) {
		String result = doInvoke( baseURL + "/identity/attributes", "subjectid="+token );
		return result;
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
		String result = doInvoke( baseURL + "/identity/authorize", postBody );
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
    public static String logout(String baseURL) {
		String result = doInvoke( baseURL + "/openam/UI/Logout" );
		return result;
	}

    protected static String read(InputStream is) throws IOException {
        byte b[] = new byte[is.available()];
        is.read(b);
        return new String(b);
    }
    protected static String doInvoke(String url) {
    	return doInvoke(url,null);
    }
    protected static String doInvoke(String url,String params) {
        try {
            LOG.debug("Sending : {}", url);
            HttpURLConnection conn = (HttpURLConnection)new URL(url).openConnection();
            conn.setRequestMethod("POST");
            if (params != null) {
    			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
    			conn.setDoOutput(true);
    			conn.connect();
    			BufferedWriter  writer = new BufferedWriter( new OutputStreamWriter( conn.getOutputStream(), Charset.forName("UTF-8") ) );
    			writer.write(params);
    			writer.flush();
            }
            conn.connect();
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
