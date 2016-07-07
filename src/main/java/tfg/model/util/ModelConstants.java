package tfg.model.util;

public class ModelConstants {

	private static final String OAuthConsumerKey = "zp0qeFcpdochTMcVLcSeCArOv";
	private static final String OAuthConsumerSecret = "mXVuLr0OyK3MuZ1soAoIA2woysejnN2lnjGcVXvYYOboelZSkP";
	private static final String OAuthAccessToken  = "4928308731-gOFJfbhZ2Mc40YmN8bbSkM3Q8mo1u5wXB1Zhzvv";
	private static final String OAuthAccessTokenSecret = "Cpvg4lbavHL5PjD9goN7fhkoPobgB11GVwGc55roywlyT";
	private static final int HttpConnectionTimeout = 100000;
	
	public static String getOauthconsumerkey() {
		return OAuthConsumerKey;
	}
	public static String getOauthconsumersecret() {
		return OAuthConsumerSecret;
	}
	public static String getOauthaccesstoken() {
		return OAuthAccessToken;
	}
	public static String getOauthaccesstokensecret() {
		return OAuthAccessTokenSecret;
	}
	public static int getHttpconnectiontimeout() {
		return HttpConnectionTimeout;
	}
	
	
}
