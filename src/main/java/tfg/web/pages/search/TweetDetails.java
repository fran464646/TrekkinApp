/**
 * 
 */
package tfg.web.pages.search;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.corelib.components.Form;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.corelib.components.EventLink;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Date;

import org.apache.tapestry5.annotations.Component;
import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import tfg.model.tweet.Tweet;
import tfg.model.tweetservice.TweetService;
import tfg.model.util.InstanceNotFoundException;
import tfg.web.services.AuthenticationPolicy;
import tfg.web.services.AuthenticationPolicyType;
import tfg.web.util.UserSession;

@AuthenticationPolicy(AuthenticationPolicyType.ALL_USERS)

public class TweetDetails {
	
	private Long tweetId;
	private Tweet tweet;
	String coordinates="[{lat: 42.341, lng: -3.698},{lat: 21.291, lng: -157.821},{lat: -18.142, lng: 178.431},{lat: -27.467, lng: 153.027}]";
	
	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}


	@Inject
	private ComponentResources _resources;

	@Inject
	private TypeCoercer _coercer;
	
	@Inject
	private Locale locale;
	
	@Inject
	private TweetService tweetService;
	
    @SessionState(create=false)
    private UserSession userSession;
    
    @Inject
    private Request request;
    
    @Inject
	private JavaScriptSupport javaScriptSupport;

	void afterRender() {
		javaScriptSupport.require("bootstrap/tab");
	}

	public Long getTweetId() {
		return tweetId;
	}

	public void setTweetId(Long tweetId) {
		this.tweetId = tweetId;
	}

	public Tweet getTweet() {
		return tweet;
	}

	public void setTweet(Tweet tweet) {
		this.tweet = tweet;
	}

	public Format getNumberFormat() {
		return NumberFormat.getInstance(locale);
	}

	public DateFormat getDateFormat() {
		DateFormat formatter = DateFormat.getDateTimeInstance(
                DateFormat.FULL, 
                DateFormat.SHORT, 
                locale);
		return formatter;
	}
	
	
	void onActivate(Long tweetId) {	
		this.tweetId = tweetId;
		tweet = tweetService.findTweet(tweetId);
	}
	
	
	Long onPassivate() {
		return tweetId;
	}
}