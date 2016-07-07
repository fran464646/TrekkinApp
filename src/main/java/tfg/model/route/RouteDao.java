package tfg.model.route;

import java.util.List;

import tfg.model.util.GenericDao;

public interface RouteDao extends GenericDao<Route, Long>{

    /**
     * Returns an UserProfile by login name (not user identifier)
     *
     * @param routeName the route name
     * @return the Route
     */
    public List<Route> findByRouteName(String routeName,int startIndex,int count);
    
    public List<Long> findRouteIds();
    
    public List<String> getRouteNames();
}
