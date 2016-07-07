package tfg.model.alertservice;

import java.util.Date;
import java.util.List;

import tfg.model.alert.Alert;
import tfg.model.util.AlertBlock;

public interface AlertService {

	public List<Alert> findAlertByRoute(Long routeId, Long userId, int startIndex, int count);
	
	public List<Alert> findAlertByRouteAndDate(Long routeId, Long userId, int startIndex,int count,Date dateStart,Date dateEnd);
	
	public AlertBlock findAlertByDate(Long userId, int startIndex,int count,Date dateStart,Date dateEnd);
	
	public AlertBlock findAlerts(Long userId, int startIndex, int count);
	
	public Alert findAlert(Long alertId);
	
	public void generateAlerts(Long userId, Long routeId);
	
	public void deleteAlert(Long alertId);
	
	public void visitAlert(Long alertId);
}
