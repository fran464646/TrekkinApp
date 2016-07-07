/**
 * 
 */
package tfg.web.pages.search;

import java.text.Format;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;

import tfg.model.route.Route;
import tfg.model.routeservice.RouteBlock;
import tfg.model.routeservice.RouteService;
import tfg.model.userprofile.UserProfile;
import tfg.model.userprofileservice.UserProfileService;
import tfg.web.services.AuthenticationPolicy;
import tfg.web.services.AuthenticationPolicyType;
import tfg.web.util.UserSession;


@AuthenticationPolicy(AuthenticationPolicyType.ALL_USERS)
public class SearchedRoutes {
	private final static int ROUTES_PER_PAGE = 10;
	private int startIndex = 0;
	private RouteBlock routeBlock;
	private UserProfile userProfile;
	private Route route;
	
	@Inject
	private UserProfileService userProfileService;
	
	@Inject
	private RouteService routeService;
	
	private String keywords;
	
	@Inject
	private Locale locale;


    @SessionState(create=false)
    private UserSession userSession;

   
	public List<Route> getRoutes() {
		return routeBlock.getRoutes();
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
	
	public Route getRoute(){
		return this.route;
	}
	
	public void setRoute(Route route){
		this.route = route;
	}
	
	public String getKeywords(){
		return this.keywords;
	}
	
	public void setKeywords(String keywords){
		this.keywords = keywords;
	}
	
	public Object[] getPreviousLinkContext() {
		
		if (startIndex-ROUTES_PER_PAGE >= 0) {
			return new Object[] {startIndex-ROUTES_PER_PAGE, keywords};
		} else {
			return null;
		}
		
	}
	
	public Object[] getNextLinkContext() {
		
		if (routeBlock.getExistMoreRoutes()) {
			return new Object[] {startIndex+ROUTES_PER_PAGE, keywords};
		} else {
			return null;
		}
		
	}
	
	Object[] onPassivate() {
		return new Object[] {startIndex, keywords};
	}
	
	void onActivate(int startIndex,String keywords) {
		this.startIndex = startIndex;
		this.keywords = keywords;
		routeBlock = routeService.findRouteByName(keywords, startIndex, ROUTES_PER_PAGE);
	}
}