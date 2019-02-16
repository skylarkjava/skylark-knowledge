package com.utilhub.openam;


public class OpenAMClientTest {

	public static void main(String[] args) {
		AmRestClient client = new AmRestClient();
		String baseUrl = "http://passport.utilhub.dt.hudaokeji.com/openam";
		String cookieName = AmRestAPI.getCookieNameForToken(baseUrl);
		System.out.println(cookieName);
		String token = client.authenticate("hudao", "hudao2013");
		String atts = client.getAttributes(token);
		//AmUser u = AmUser.parse(atts);
		System.out.println(atts);
	}

}
