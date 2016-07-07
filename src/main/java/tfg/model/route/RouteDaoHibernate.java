package tfg.model.route;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import tfg.model.util.GenericDaoHibernate;
import tfg.model.util.InstanceNotFoundException;

@Repository("routeDao")
public class RouteDaoHibernate extends
		GenericDaoHibernate<Route, Long> implements RouteDao {

	public List<Route> findByRouteName(String routeName,int startIndex,int count) {
		Query tmpQuery; 
		if (routeName!=null)
			tmpQuery = getSession().createQuery(
    			"SELECT u FROM Route u WHERE u.routeName LIKE '%' || :routeName || '%' ")
    			.setParameter("routeName", routeName);
		else
			tmpQuery = getSession().createQuery(
	    			"SELECT u FROM Route u");
		return tmpQuery.setFirstResult(startIndex).setMaxResults(count).list();
	}

	@Override
	public List<Long> findRouteIds() {
		Query tmpQuery = getSession().createQuery(
    			"SELECT u.routeId FROM Route u");
    	return tmpQuery.list();
	}

	@Override
	public List<String> getRouteNames() {
		Query tmpQuery = getSession().createQuery(
    			"SELECT u.routeName FROM Route u");
    	return tmpQuery.list();
	}		

}