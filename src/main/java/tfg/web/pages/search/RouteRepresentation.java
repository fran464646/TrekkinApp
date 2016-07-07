/**
 * 
 */
package tfg.web.pages.search;

import tfg.model.route.Route;
import tfg.model.routeservice.RouteService;
import tfg.model.tweetservice.TweetService;
import tfg.web.pages.user.Login;
import tfg.web.services.AuthenticationPolicy;
import tfg.web.services.AuthenticationPolicyType;
import tfg.web.util.UserSession;

import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;

/**
 * @author Urist
 *
 */
@AuthenticationPolicy(AuthenticationPolicyType.ALL_USERS)

public class RouteRepresentation {
	
	private Long routeId;
	private Route route;
	
	@Inject
	private RouteService routeService;
	
	@Inject
	private TweetService tweetService;
		
	@Inject
	private Locale locale;
	
    @SessionState(create=false)
    private UserSession userSession;

    @InjectPage
    private Login login;
    
    @Inject
    private Request request;

    
	public void setRoute(Route route) {
		this.route = route;
	}
	
	public Route getRoute() {
		return route;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}
	
	public Long getRouteId() {
		return routeId;
	}
	
	void onActivate() {	
	}
	
	void onPassivate() {
	}
	
	void onSuccess() {

    }
}