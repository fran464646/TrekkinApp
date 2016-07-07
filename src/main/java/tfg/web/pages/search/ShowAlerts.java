package tfg.web.pages.search;


import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
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
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.ajax.AjaxResponseRenderer;

import tfg.model.alert.Alert;
import tfg.model.alertservice.AlertService;
import tfg.model.routeservice.RouteService;
import tfg.model.tweet.Tweet;
import tfg.model.tweetservice.TweetService;
import tfg.model.userprofile.UserProfile;
import tfg.model.userprofileservice.UserProfileService;
import tfg.model.util.AlertBlock;
import tfg.model.util.CoordinateAux;
import tfg.model.util.InstanceNotFoundException;
import tfg.model.util.TweetBlock;
import tfg.web.services.AuthenticationPolicy;
import tfg.web.services.AuthenticationPolicyType;
import tfg.web.util.EvenOdd;
import tfg.web.util.UserSession;

@AuthenticationPolicy(AuthenticationPolicyType.ALL_USERS)
public class ShowAlerts {
	
	private final static int ALERTS_PER_PAGE = 10;
	private int startIndex = 0;
	private AlertBlock alertBlock;
	private UserProfile userProfile;
	private Alert alert;
	String routeName;
	
	public String getRouteName() {
		try {
			return routeService.findByRouteId(alert.getAlertRouteId()).getRouteName();
		} catch (InstanceNotFoundException e) {
			return null;
		}
	}

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
	private RouteService routeService;
	
	@Inject
    private AjaxResponseRenderer ajaxResponseRenderer;
	
	@Property
    private EvenOdd evenOdd;
	
	@Inject
	private AlertService alertService;
	
	private Long routeId;
	
	@Inject
	private Locale locale;

	void setupRender() {
        started = false;
        evenOdd = new EvenOdd();
    }

    @SessionState(create=false)
    private UserSession userSession;

   
	public List<Alert> getAlerts() {
		return alertBlock.getAlerts();
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
	
	public Alert getAlert(){
		return this.alert;
	}
	
	public void setAlert(Alert alert){
		this.alert = alert;
	}
	
	public Long getRouteId(){
		return this.routeId;
	}
	
	public void setRouteId(Long routeId){
		this.routeId = routeId;
	}
	
	public void onDelete(Long alertId){
		alertService.deleteAlert(alertId);
	}
	
	public Object[] getPreviousLinkContext() {
		
		if (startIndex-ALERTS_PER_PAGE >= 0) {
			return new Object[] {startIndex-ALERTS_PER_PAGE, routeId};
		} else {
			return null;
		}
		
	}
	
	public Object[] getNextLinkContext() {
		
		if (alertBlock.isExistsMoreAlerts()) {
			return new Object[] {startIndex+ALERTS_PER_PAGE, routeId};
		} else {
			return null;
		}
		
	}
	
	Object[] onPassivate() {
		return new Object[] {startIndex, routeId};
	}
	
	void onSuccess()
    {
		alertBlock = alertService.findAlertByDate(userSession.getUserProfileId(), startIndex, ALERTS_PER_PAGE, dateStart, dateEnd);
		ajaxResponseRenderer.addRender(myZone);
    }
	
	void onActivate(int startIndex) {
		this.startIndex = startIndex;
		alertBlock = alertService.findAlertByDate(userSession.getUserProfileId(), startIndex, ALERTS_PER_PAGE,dateStart,dateEnd);
		ajaxResponseRenderer.addRender(myZone);
	}
}
