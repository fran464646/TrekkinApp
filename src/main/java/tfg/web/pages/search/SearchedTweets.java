/**
 * 
 */
package tfg.web.pages.search;

import java.text.Format;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.annotations.InjectComponent;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.corelib.components.Zone;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import tfg.model.tweet.Tweet;
import tfg.model.tweetservice.TweetService;
import tfg.model.userprofile.UserProfile;
import tfg.model.userprofileservice.UserProfileService;
import tfg.model.util.TweetBlock;
import tfg.web.services.AuthenticationPolicy;
import tfg.web.services.AuthenticationPolicyType;
import tfg.web.util.EvenOdd;
import tfg.web.util.UserSession;


@AuthenticationPolicy(AuthenticationPolicyType.ALL_USERS)
public class SearchedTweets {
	
	private final static int TWEETS_PER_PAGE = 10;
	private int startIndex = 0;
	private TweetBlock tweetBlock;
	private UserProfile userProfile;
	private Tweet tweet;
	private List<Tweet> tweets;
	
	@InjectPage
	private RouteStats routeStats;
	
	@Persist
	@Property
	private Date dateStart;
	
	@Persist
	@Property
	private Date dateEnd;
	
	@InjectComponent
	private Zone myZone;
	
	@Property
    private boolean started;
	
	@Inject
	private UserProfileService userProfileService;
	
	@Inject
    private AjaxResponseRenderer ajaxResponseRenderer;
	
	@Property
    private EvenOdd evenOdd;
	
	@Inject
	private TweetService tweetService;
	
	private Long routeId;
	
	@Inject
	private Locale locale;

	void setupRender() {
        started = false;
        evenOdd = new EvenOdd();
    }

    @SessionState(create=false)
    private UserSession userSession;

   
	public List<Tweet> getTweets() {
		return tweets;
	}
	
	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}

	public UserProfile getUserProfile() {
		return userProfile;
	}

	public void setUserProfile(UserProfile userProfile) {
		this.userProfile = userProfile;
	}
	
	public Format getFormat() {
		return NumberFormat.getInstance(locale);
	}
	
	public Tweet getTweet(){
		return this.tweet;
	}
	
	public void setTweet(Tweet tweet){
		this.tweet = tweet;
	}
	
	public Long getRouteId(){
		return this.routeId;
	}
	
	public void setRouteId(Long routeId){
		this.routeId = routeId;
	}
	
	public Object[] getPreviousLinkContext() {
		
		if (startIndex-TWEETS_PER_PAGE >= 0) {
			return new Object[] {startIndex-TWEETS_PER_PAGE, routeId};
		} else {
			return null;
		}
		
	}
	
	public Object[] getNextLinkContext() {
		
		if (tweetBlock.isExistsMoreTweets()) {
			return new Object[] {startIndex+TWEETS_PER_PAGE, routeId};
		} else {
			return null;
		}
		
	}
	
	Object[] onPassivate() {
		return new Object[] {startIndex, routeId};
	}
	
	void onSuccess()
    {
		if (dateEnd!=null){
			Calendar cal = Calendar.getInstance();
	    	cal.setTime(dateEnd);
	    	cal.add(Calendar.HOUR_OF_DAY, 24);
	    	dateEnd=cal.getTime();
		}
		tweetBlock = tweetService.findByRoute(routeId, startIndex, TWEETS_PER_PAGE,dateStart,dateEnd);
		if (dateEnd!=null){
			Calendar cal = Calendar.getInstance();
	    	cal.setTime(dateEnd);
	    	cal.add(Calendar.HOUR_OF_DAY,-24);
	    	dateEnd=cal.getTime();
		}
		setTweets(tweetBlock.getTweets());
		TweetBlock aux=tweetBlock;
		ajaxResponseRenderer.addRender(myZone);
    }
	
	void onActivate(int startIndex,Long routeId) {
		this.startIndex = startIndex;
		this.routeId = routeId;
		if (dateEnd!=null){
			Calendar cal = Calendar.getInstance();
	    	cal.setTime(dateEnd);
	    	cal.add(Calendar.HOUR_OF_DAY, 24);
	    	dateEnd=cal.getTime();
		}
		tweetBlock = tweetService.findByRoute(routeId, startIndex, TWEETS_PER_PAGE,dateStart,dateEnd);
		if (dateEnd!=null){
			Calendar cal = Calendar.getInstance();
	    	cal.setTime(dateEnd);
	    	cal.add(Calendar.HOUR_OF_DAY,-24);
	    	dateEnd=cal.getTime();
		}
		setTweets(tweetBlock.getTweets());
		ajaxResponseRenderer.addRender(myZone);
	}
}