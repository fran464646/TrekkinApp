package tfg.model.util;

import java.util.List;
import java.util.TimerTask;

import tfg.model.alertservice.AlertService;
import tfg.model.routeservice.RouteService;

public class Alerts extends TimerTask {
	
	public Alerts() {
		super();
	}

	private AlertService alertService;

	private Long routeId;
	
	private Long userId;
	
	private RouteService routeService;

	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	public Alerts(Long userId, AlertService alertService, RouteService routeService) {
		super();
		this.userId=userId;
		this.alertService = alertService;
		this.routeService = routeService;
	}

	@Override
	public void run() {
		List<Long> routes = routeService.getAllRouteIds();
		for (Long route:routes){
			alertService.generateAlerts(userId, route);
		}
	}
}
