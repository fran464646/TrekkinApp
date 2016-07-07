package tfg.web.pages.user;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.hibernate.Query;

import tfg.model.alertservice.AlertService;
import tfg.model.route.Route;
import tfg.model.routeservice.RouteService;
import tfg.model.statservice.StatService;
import tfg.model.tweetservice.TweetService;
import tfg.model.util.Alerts;
import tfg.model.util.InstanceNotFoundException;
import tfg.model.util.Miopia;
import tfg.web.services.AuthenticationPolicy;
import tfg.web.services.AuthenticationPolicyType;
import tfg.web.util.UserSession;

@AuthenticationPolicy(AuthenticationPolicyType.ALL_USERS)

/**
 * @author fran
 *
 */
public class Profile {
	
	@Inject
	RouteService routeService;
	
	@Inject
	TweetService tweetService;
	
	@Inject
	AlertService alertService;
	
	@Inject
	StatService statService;
	
	@SessionState(create=true)
    private UserSession userSession;
	

	void onActivate(){
		Date horaDespertar = new Date(System.currentTimeMillis());
		
		Calendar c = Calendar.getInstance();
		c.setTime(horaDespertar);
		
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		
		horaDespertar = c.getTime();
		int tiempoRepeticion = 300000;
		Timer temporizador = new Timer();
		temporizador.schedule(new Alerts(userSession.getUserProfileId(), alertService, routeService), horaDespertar, tiempoRepeticion);
	}
}