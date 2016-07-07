package tfg.model.routeservice;

import java.util.List;
import tfg.model.route.Route;

public class RouteBlock {

	    private List<Route> routes;
	    private boolean existMoreRoutes;

	    public RouteBlock(List<Route> routes, boolean existMoreRoutes) {
	        
	        this.routes= routes;
	        this.existMoreRoutes = existMoreRoutes;

	    }
	    
	    public List<Route> getRoutes() {
	        return routes;
	    }
	    
	    public boolean getExistMoreRoutes() {
	        return existMoreRoutes;
	    }
}
