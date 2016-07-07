package tfg.model.alert;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import tfg.model.util.GenericDaoHibernate;
import tfg.model.util.InstanceNotFoundException;

@Repository("alertDao")
public class AlertDaoHibernate extends
		GenericDaoHibernate<Alert, Long> implements AlertDao {


	@Override
	public List<Alert> findAlertByRoute(Long routeId,Long userId, int startIndex, int count) {
		Query tmpQuery;
		tmpQuery = getSession().createQuery(
    			"SELECT a FROM Alert a WHERE a.alertRouteId = :routeId AND a.alertUserId = :userId ORDER BY a.alertDate DESC")
    			.setParameter("routeId", routeId).setParameter("userId", userId).setFirstResult(startIndex).setMaxResults(count);
	List<Alert> alerts= tmpQuery.list();
	return alerts;
	}

	@Override
	public List<Alert> findAlertByRouteAndDate(Long routeId,Long userId, int startIndex,
			int count, Date dateStart, Date dateEnd) {
		Query tmpQuery;
		if (dateStart==null && dateEnd==null){
			tmpQuery = getSession().createQuery(
	    			"SELECT a FROM Alert a WHERE a.alertRouteId = :routeId AND a.alertUserId = :userId ORDER BY a.alertDate DESC")
	    			.setParameter("routeId", routeId).setParameter("userId", userId).setFirstResult(startIndex).setMaxResults(count);
		}else{
			if (dateStart==null){
				tmpQuery = getSession().createQuery(
						"SELECT a FROM Alert a WHERE a.alertRouteId = :routeId AND a.alertDate<= :dateEnd AND a.alertUserId = :userId ORDER BY a.alertDate DESC")
		    			.setParameter("routeId", routeId).setParameter("userId", userId).setParameter("dateEnd", dateEnd).setFirstResult(startIndex).setMaxResults(count);
			}else{
				if (dateEnd==null){
					tmpQuery = getSession().createQuery(
							"SELECT a FROM Alert a WHERE a.alertRouteId = :alertRouteId AND a.alertDate>= :dateStart AND a.alertUserId = :userId ORDER BY a.alertDate DESC")
			    			.setParameter("routeId", routeId).setParameter("userId", userId).setParameter("dateStat", dateEnd).setFirstResult(startIndex).setMaxResults(count);
				}else{
					tmpQuery = getSession().createQuery(
			    			"SELECT a FROM Alert a WHERE a.alertRouteId = :routeId AND a.alertDate>= :dateStart AND a.alertDate<= :dateEnd AND a.alertUserId = :userId ORDER BY a.alertDate DESC")
			    			.setParameter("routeId", routeId).setParameter("userId", userId).setParameter("dateStart", dateStart).setParameter("dateEnd", dateEnd).setFirstResult(startIndex).setMaxResults(count);
				}
			}
		}
		return tmpQuery.list();
	}

	@Override
	public List<Alert> findAlertByDate(Long userId, int startIndex, int count,
			Date dateStart, Date dateEnd) {
		Query tmpQuery;
		if (dateStart==null && dateEnd==null){
			tmpQuery = getSession().createQuery(
	    			"SELECT a FROM Alert a WHERE a.alertUserId = :userId OR a.alertUserId = -1 ORDER BY a.alertDate DESC").setParameter("userId", userId)
	    			.setFirstResult(startIndex).setMaxResults(count);
		}else{
			if (dateStart==null){
				tmpQuery = getSession().createQuery(
						"SELECT a FROM Alert a WHERE a.alertDate<= :dateEnd AND (a.alertUserId = :userId OR a.alertUserId = -1) ORDER BY a.alertDate DESC").setParameter("userId", userId)
		    			.setParameter("dateEnd", dateEnd).setFirstResult(startIndex).setMaxResults(count);
			}else{
				if (dateEnd==null){
					tmpQuery = getSession().createQuery(
							"SELECT a FROM Alert a WHERE a.alertDate>= :dateStart AND (a.alertUserId = :userId OR a.alertUserId = -1) ORDER BY a.alertDate DESC").setParameter("userId", userId)
			    			.setParameter("dateStat", dateEnd).setFirstResult(startIndex).setMaxResults(count);
				}else{
					tmpQuery = getSession().createQuery(
			    			"SELECT a FROM Alert a WHERE a.alertDate>= :dateStart AND a.alertDate<= :dateEnd AND (a.alertUserId = :userId OR a.alertUserId = -1) ORDER BY a.alertDate DESC").setParameter("userId", userId)
			    			.setParameter("dateStart", dateStart).setParameter("dateEnd", dateEnd).setFirstResult(startIndex).setMaxResults(count);
				}
			}
		}
		return tmpQuery.list();
	}

	@Override
	public List<Alert> findAlerts(Long userId, int startIndex,
			int count) {
		Query tmpQuery;
		tmpQuery = getSession().createQuery(
    			"SELECT a FROM Alert a WHERE a.alertUserId = :userId OR a.alertUserId = -1 ORDER BY a.alertDate DESC")
    			.setParameter("userId", userId).setFirstResult(startIndex).setMaxResults(count);

	return tmpQuery.list();
	}

	@Override
	public List<Alert> findSameAlerts(String description, Date fecha) {
		Query tmpQuery;
		tmpQuery = getSession().createQuery(
    			"SELECT a FROM Alert a WHERE a.alertCompleteDescription = :description AND a.alertDate >= :date ORDER BY a.alertDate DESC")
    			.setParameter("description", description).setParameter("date",fecha);

	return tmpQuery.list();
	}

	@Override
	public void deleteAlert(Long alertId) {
		Query tmpQuery;
		tmpQuery = getSession().createQuery(
    			"DELETE FROM Alert WHERE alertId = :alertId")
    			.setParameter("alertId",alertId);
		
		tmpQuery.executeUpdate();
		
	}
}