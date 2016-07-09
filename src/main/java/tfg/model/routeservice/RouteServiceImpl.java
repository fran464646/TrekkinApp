package tfg.model.routeservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tfg.model.route.Route;
import tfg.model.route.RouteDao;
import tfg.model.util.InstanceNotFoundException;

@Service("routeService")
@Transactional
public class RouteServiceImpl implements RouteService {
	
	
	 @Autowired
	 private RouteDao routeDao;
	

	@Override
	public RouteBlock findRouteByName(String routeName, int startIndex, int count) {
		List<Route> routes = routeDao.findByRouteName(routeName, startIndex, count+1);

		boolean existMoreRoutes = routes.size() == (count + 1);

		/*
		 * Remove the last account from the returned list if there exist more
		 * accounts above the specified range.
		 */
		if (existMoreRoutes) {
			routes.remove(routes.size() - 1);
		}
		
		/* Return AccountBlock. */
		return new RouteBlock(routes, existMoreRoutes);
	}


	@Override
	public Route findByRouteId(long routeId) throws InstanceNotFoundException {
		return routeDao.find(routeId);
	}


	@Override
	public List<Long> getAllRouteIds() {
		return routeDao.findRouteIds();
	}


	@Override
	public List<String> getRouteNames() {
		return routeDao.getRouteNames();
	}

	
}
