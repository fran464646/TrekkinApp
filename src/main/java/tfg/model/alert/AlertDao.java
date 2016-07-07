package tfg.model.alert;

import java.util.Date;
import java.util.List;

import tfg.model.util.GenericDao;

public interface AlertDao extends GenericDao<Alert, Long>{
	
	public List<Alert> findAlertByRoute(Long routeId,Long userId,int startIndex, int count);
	
	public List<Alert> findAlerts(Long userId,int startIndex, int count);
	
	public List<Alert> findSameAlerts(String description, Date fecha);
	
	public List<Alert> findAlertByRouteAndDate(Long routeId,Long userId,int startIndex,int count,Date dateStart,Date dateEnd);
	
	public List<Alert> findAlertByDate(Long userId,int startIndex,int count,Date dateStart,Date dateEnd);
	
	public void deleteAlert(Long alertId);
	
}
