/**
 * 
 */
package tfg.web.pages.search;

import tfg.model.alert.Alert;
import tfg.model.alertservice.AlertService;
import tfg.model.route.Route;
import tfg.model.routeservice.RouteService;
import tfg.model.stat.Stat;
import tfg.model.statservice.StatService;
import tfg.model.tweetservice.TweetService;
import tfg.model.util.InstanceNotFoundException;
import tfg.model.util.Miopia;
import tfg.web.pages.user.Login;
import tfg.web.services.AuthenticationPolicy;
import tfg.web.services.AuthenticationPolicyType;
import tfg.web.util.UserSession;

import java.text.DateFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;

import org.apache.tapestry5.annotations.InjectPage;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.services.Request;

/**
 * @author Urist
 *
 */
@AuthenticationPolicy(AuthenticationPolicyType.ALL_USERS)

public class AlertDetails {
	
	private Long alertId;
	private Alert alert;
	private Stat stat;
	private Route route;
	
	@Inject
	private AlertService alertService;
	
	@Inject
	private StatService statService;
	
	@Inject
	private RouteService routeService;
		
	@Inject
	private Locale locale;
	
    @SessionState(create=false)
    private UserSession userSession;

    @InjectPage
    private Login login;
    
    @Inject
    private Request request;
    
	public void setAlert(Alert alert) {
		this.alert = alert;
	}
	
	public Alert getAlert() {
		return alert;
	}

	public void setAlertId(Long alertId) {
		this.alertId = alertId;
	}
	
	public Long getAlertId() {
		return alertId;
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
	
	
	void onActivate(Long alertId) {	
		this.alertId = alertId;
		alertService.visitAlert(alertId);
		alert = alertService.findAlert(alertId);
		setStat(statService.findStat(alert.getAlertStatId()));
		try {
			setRoute(routeService.findByRouteId(alert.getAlertRouteId()));
		} catch (InstanceNotFoundException e) {
			setRoute(null);
		}
		
		
	}
	
	Long onPassivate() {
		return alertId;
	}

	public Route getRoute() {
		return route;
	}

	public void setRoute(Route route) {
		this.route = route;
	}

	public Stat getStat() {
		return stat;
	}

	public void setStat(Stat stat) {
		this.stat = stat;
	}
}