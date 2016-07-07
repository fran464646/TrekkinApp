package tfg.model.stat;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import tfg.model.util.GenericDaoHibernate;

@Repository("statDao")
public class StatDaoHibernate extends
		GenericDaoHibernate<Stat, Long> implements StatDao {

	@Override
	public List<Stat> findByRoute(Long routeId, int startIndex, int count) {
		Query tmpQuery;
			tmpQuery = getSession().createQuery(
	    			"SELECT s FROM Stat s WHERE s.statRouteId = :routeId ORDER BY s.statDate DESC")
	    			.setParameter("routeId", routeId).setFirstResult(startIndex).setMaxResults(count);

    	return tmpQuery.list();
	}

	@Override
	public List<Stat> findStatByRouteAndDate(Long routeId, int startIndex,
			int count, Date dateStart, Date dateEnd) {
		Query tmpQuery;
		if (dateStart==null && dateEnd==null){
			tmpQuery = getSession().createQuery(
	    			"SELECT s FROM Stat s WHERE s.statRouteId = :routeId ORDER BY s.statDate DESC")
	    			.setParameter("routeId", routeId).setFirstResult(startIndex).setMaxResults(count);
		}else{
			if (dateStart==null){
				tmpQuery = getSession().createQuery(
						"SELECT s FROM Stat s WHERE s.statRouteId = :routeId AND s.statDate<= :dateEnd ORDER BY s.statDate DESC")
		    			.setParameter("routeId", routeId).setParameter("dateEnd", dateEnd).setFirstResult(startIndex).setMaxResults(count);
			}else{
				if (dateEnd==null){
					tmpQuery = getSession().createQuery(
							"SELECT s FROM Stat s WHERE s.statRouteId = :routeId AND s.statDate>= :dateStart ORDER BY s.statDate DESC")
			    			.setParameter("routeId", routeId).setParameter("dateStart", dateStart).setFirstResult(startIndex).setMaxResults(count);
				}else{
					tmpQuery = getSession().createQuery(
			    			"SELECT s FROM Stat s WHERE s.statRouteId = :routeId AND s.statDate>= :dateStart AND s.statDate<= :dateEnd ORDER BY s.statDate DESC")
			    			.setParameter("routeId", routeId).setParameter("dateStart", dateStart).setParameter("dateEnd", dateEnd).setFirstResult(startIndex).setMaxResults(count);
				}
			}
		}
    	return tmpQuery.list();
	}
}