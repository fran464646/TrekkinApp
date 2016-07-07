package tfg.model.routeservice;

import java.util.List;

import tfg.model.route.Route;
import tfg.model.util.InstanceNotFoundException;

public interface RouteService {

	public RouteBlock findRouteByName(String routeName, int startIndex, int count);
	
	public Route findByRouteId(long routeId) throws InstanceNotFoundException;
	
	public List<Long> getAllRouteIds();
	
	public List<String> getRouteNames();
}
