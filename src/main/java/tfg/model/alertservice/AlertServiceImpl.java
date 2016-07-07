package tfg.model.alertservice;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tfg.model.alert.Alert;
import tfg.model.alert.AlertDao;
import tfg.model.parameter.Parameter;
import tfg.model.parameter.ParameterDao;
import tfg.model.stat.Stat;
import tfg.model.stat.StatDao;
import tfg.model.tweet.Tweet;
import tfg.model.util.AlertBlock;
import tfg.model.util.InstanceNotFoundException;
import tfg.model.util.TweetBlock;

@Service("alertService")
@Transactional
public class AlertServiceImpl implements AlertService {
	
	@Autowired
	private AlertDao alertDao;
	
	@Autowired
	private ParameterDao parameterDao;
	
	@Autowired
	private StatDao statDao;

	@Override
	public List<Alert> findAlertByRoute(Long routeId, Long userId, int startIndex, int count) {
		return alertDao.findAlertByRoute(routeId, userId, startIndex, count);
	}

	@Override
	public List<Alert> findAlertByRouteAndDate(Long routeId, Long userId, int startIndex,
			int count, Date dateStart, Date dateEnd) {
		return alertDao.findAlertByRouteAndDate(routeId, userId, startIndex, count, dateStart, dateEnd);
	}

	@Override
	public AlertBlock findAlertByDate(Long userId, int startIndex, int count,
			Date dateStart, Date dateEnd) {
		List<Alert> alerts = alertDao.findAlertByDate(userId, startIndex, count+1, dateStart, dateEnd);
		boolean existMoreAlerts = alerts.size() == (count + 1);

		if (existMoreAlerts) {
			alerts.remove(alerts.size() - 1);
		}
		return new AlertBlock(existMoreAlerts,alerts);
	}

	@Override
	public void generateAlerts(Long userId, Long routeId) {
		Date date=new Date();
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		List<Stat> stats=statDao.findStatByRouteAndDate(routeId, 0, 1, date, null);
		if (stats.size()!=0){
			Stat stat=stats.get(0);
			List<Parameter> parameters = parameterDao.findParametersByUser(userId);
			if (stat.getStatOpinionBalance()>(stat.getStatOldMiddle()+stat.getStatTipicalDeviation())||stat.getStatOpinionBalance()<(stat.getStatOldMiddle()-stat.getStatTipicalDeviation())){
				Alert alert=new Alert();
				alert.setAlertDate(new Date());
				alert.setAlertRouteId(routeId);
				alert.setAlertStatId(stat.getStatId());
				alert.setAlertUserId(-1l);
				alert.setAlertDescription("La valoración ha variado respecto a los últimos días");
				alert.setAlertCompleteDescription("La valoración ha variado en una cantidad mayor a la desviación típica obtenida a partir de los balances de opniones de los últimos 7 días.");
				alert.setAlertVisited(false);
				List<Alert> alerts = alertDao.findSameAlerts(alert.getAlertCompleteDescription(), date);
				if (alerts.isEmpty())
					alertDao.save(alert);
			}
			if (stat.getStatNewNegative()){
				Alert alert=new Alert();
				alert.setAlertDate(new Date());
				alert.setAlertRouteId(routeId);
				alert.setAlertStatId(stat.getStatId());
				alert.setAlertUserId(-1l);
				alert.setAlertDescription("La valoración ha pasado a ser negativa");
				alert.setAlertCompleteDescription("El balance de opiniones a pasado de ser un balance positivo a ser un balance negativo.");
				alert.setAlertVisited(false);
				List<Alert> alerts = alertDao.findSameAlerts(alert.getAlertCompleteDescription(), date);
				if (alerts.isEmpty())
					alertDao.save(alert);
			}
			for (Parameter parameter: parameters){
				if (parameter.getParameterKey().equals("positiveOpinions")){
					if (stat.getStatPNumber()>parameter.getParameterValue()){
						Alert alert=new Alert();
						alert.setAlertDate(new Date());
						alert.setAlertRouteId(routeId);
						alert.setAlertStatId(stat.getStatId());
						alert.setAlertUserId(userId);
						alert.setAlertDescription("Se ha supera el número de votos positivos establecido");
						alert.setAlertCompleteDescription("El número de votos positivos ha superado el límite establecido para los mismos. El límite establecido era de "+parameter.getParameterValue()+" votos positivos.");
						alert.setAlertVisited(false);
						List<Alert> alerts = alertDao.findSameAlerts(alert.getAlertCompleteDescription(), date);
						if (alerts.isEmpty() && alert.getAlertRouteId()==parameter.getParameterRouteId())
							alertDao.save(alert);
					}
				}
				if (parameter.getParameterKey().equals("negativeOpinions")){
					if (stat.getStatNNumber()>parameter.getParameterValue()){
						Alert alert=new Alert();
						alert.setAlertDate(new Date());
						alert.setAlertRouteId(routeId);
						alert.setAlertStatId(stat.getStatId());
						alert.setAlertUserId(userId);
						alert.setAlertDescription("Se ha supera el número de votos negativos establecido");
						alert.setAlertCompleteDescription("El número de votos negativos ha superado el límite establecido para los mismos. El límite establecido era de "+parameter.getParameterValue()+"votos negativos.");
						alert.setAlertVisited(false);
						List<Alert> alerts = alertDao.findSameAlerts(alert.getAlertCompleteDescription(), date);
						if (alerts.isEmpty() && alert.getAlertRouteId()==parameter.getParameterRouteId())
							alertDao.save(alert);
					}
				}
				if (parameter.getParameterKey().equals("positiveBalance")){
					if (stat.getStatOpinionBalance()>parameter.getParameterValue()){
						Alert alert=new Alert();
						alert.setAlertDate(new Date());
						alert.setAlertRouteId(routeId);
						alert.setAlertStatId(stat.getStatId());
						alert.setAlertUserId(userId);
						alert.setAlertDescription("Se ha superado el valor del balance establecido");
						alert.setAlertCompleteDescription("El balance ha superado el límite establecido para el mismo. El límite establecido era de "+parameter.getParameterValue()+".");
						alert.setAlertVisited(false);
						List<Alert> alerts = alertDao.findSameAlerts(alert.getAlertCompleteDescription(), date);
						if (alerts.isEmpty() && alert.getAlertRouteId()==parameter.getParameterRouteId())
							alertDao.save(alert);
					}
				}
				if (parameter.getParameterKey().equals("negativeBalance")){
					if (stat.getStatOpinionBalance()<parameter.getParameterValue()){
						Alert alert=new Alert();
						alert.setAlertDate(new Date());
						alert.setAlertRouteId(routeId);
						alert.setAlertStatId(stat.getStatId());
						alert.setAlertUserId(userId);
						alert.setAlertDescription("El balance es inferior al límite establecido para el mismo");
						alert.setAlertCompleteDescription("El balance ha sido inferior al límite establecido para el mismo. El límite establecido era de "+parameter.getParameterValue()+".");
						alert.setAlertVisited(false);
						List<Alert> alerts = alertDao.findSameAlerts(alert.getAlertCompleteDescription(), date);
						if (alerts.isEmpty() && alert.getAlertRouteId()==parameter.getParameterRouteId())
							alertDao.save(alert);
					}
				}
			}
		}
	}

	@Override
	public AlertBlock findAlerts(Long userId, int startIndex, int count) {
		List<Alert> alerts = alertDao.findAlerts(userId, startIndex, count+1);

		boolean existMoreAlerts = alerts.size() == (count + 1);

		if (existMoreAlerts) {
			alerts.remove(alerts.size() - 1);
		}
		return new AlertBlock(existMoreAlerts,alerts);
	}

	@Override
	public Alert findAlert(Long alertId) {
		try {
			return alertDao.find(alertId);
		} catch (InstanceNotFoundException e) {
			return null;
		}
	}

	@Override
	public void visitAlert(Long alertId) {
		Alert alert;
		try {
			alert = alertDao.find(alertId);
			alert.setAlertVisited(true);
			alertDao.save(alert);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	@Override
	public void deleteAlert(Long alertId) {
		alertDao.deleteAlert(alertId);	
	}

}
