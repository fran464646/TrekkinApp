package tfg.model.util;

import java.util.ArrayList;
import java.util.List;

import tfg.model.alert.Alert;
import tfg.model.tweet.Tweet;

public class AlertBlock {

	private boolean existsMoreAlerts;
	
	private List<Alert> alerts;
	
	public AlertBlock(boolean existsMoreAlerts, List<Alert> alerts) {
		super();
		this.existsMoreAlerts = existsMoreAlerts;
		this.alerts = alerts;
	}
	
	public AlertBlock() {
	}

	public boolean isExistsMoreAlerts() {
		return existsMoreAlerts;
	}

	public void setExistsMoreAlerts(boolean existsMoreAlerts) {
		this.existsMoreAlerts = existsMoreAlerts;
	}

	public List<Alert> getAlerts() {
		return alerts;
	}

	public void setAlerts(List<Alert> alerts) {
		this.alerts = alerts;
	}
	
	
}
