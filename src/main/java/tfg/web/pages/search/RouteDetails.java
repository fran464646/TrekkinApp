/**
 * 
 */
package tfg.web.pages.search;

import tfg.model.route.Route;
import tfg.model.routeservice.RouteService;
import tfg.model.tweetservice.TweetService;
import tfg.model.util.CoordinateAux;
import tfg.model.util.InstanceNotFoundException;
import tfg.model.util.Miopia;
import tfg.web.pages.user.Login;
import tfg.web.services.AuthenticationPolicy;
import tfg.web.services.AuthenticationPolicyType;
import tfg.web.util.UserSession;

import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.Request;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;

/**
 * @author Urist
 *
 */
@AuthenticationPolicy(AuthenticationPolicyType.ALL_USERS)

public class RouteDetails {
	
	private Long routeId;
	private Route route;
	String coordinates="";
	Coordinate coordinateAux;
	
	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	@Inject
	private RouteService routeService;
	
	@Inject
	private TweetService tweetService;
		
	@Inject
	private Locale locale;
	
    @SessionState(create=false)
    private UserSession userSession;
    
    @Inject
	private ComponentResources _resources;

	@Inject
	private TypeCoercer _coercer;

    @InjectPage
    private Login login;
    
    @InjectPage
    private SearchedTweets searchedTweets;
    
    @Inject
    private Request request;
    
    private Thread myThread;
    
    @Inject
	private JavaScriptSupport javaScriptSupport;

	void afterRender() {
		javaScriptSupport.require("bootstrap/tab");
	}

    
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
	
	
	void onActivate(Long routeId) {	
		this.routeId = routeId;
		try {
			route = routeService.findByRouteId(routeId);
			List <Coordinate> coordinatesAux=tweetService.getCoordinates(System.getProperty("user.dir")
					+ "/src/kml/" + route.getKmlFile());
					coordinates=coordinates.concat("[");
					coordinateAux=coordinatesAux.get(0);
					for (Coordinate coordinate:coordinatesAux){
						coordinates=coordinates.concat("{lat: "+coordinate.getLatitude()+" , lng: "+coordinate.getLongitude()+"},");
					}
					coordinates=coordinates.substring(0,coordinates.length()-1);
					coordinates=coordinates.concat("]");
					System.out.println(coordinates);
		} catch (InstanceNotFoundException e) {
		}
	}
	
	public Coordinate getCoordinateAux() {
		return coordinateAux;
	}

	public void setCoordinateAux(Coordinate coordinateAux) {
		this.coordinateAux = coordinateAux;
	}

	Long onPassivate() {
		return routeId;
	}
	
	Object onSuccess() {
		this.searchedTweets.setRouteId(routeId);
		return this.searchedTweets;
    }
}