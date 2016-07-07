package tfg.model.alert;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.BatchSize;

@Entity
@BatchSize(size=10)
public class Alert {

	private Long alertId;
	private String alertDescription;
	private Date alertDate;
	private String alertCompleteDescription;
	private Long alertRouteId;
	private Long alertStatId;
	private Long alertUserId;
	private Boolean alertVisited;


	public Alert(){
		
	}
	
	@Column(name = "alertId")
	@SequenceGenerator(
	name = "AlertIdGenerator",
	sequenceName = "AlertSeq")
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "AlertIdGenerator")
	public Long getAlertId() {
		return alertId;
	}
	public void setAlertId(Long alertId) {
		this.alertId = alertId;
	}

	@Column(name = "alertDescription")
	public String getAlertDescription() {
		return alertDescription;
	}

	public void setAlertDescription(String alertDescription) {
		this.alertDescription = alertDescription;
	}

	@Column(name = "alertDate")
	public Date getAlertDate() {
		return alertDate;
	}

	public void setAlertDate(Date alertDate) {
		this.alertDate = alertDate;
	}

	@Column(name = "alertCompleteDescription")
	public String getAlertCompleteDescription() {
		return alertCompleteDescription;
	}

	public void setAlertCompleteDescription(String alertCompleteDescription) {
		this.alertCompleteDescription = alertCompleteDescription;
	}

	@Column(name = "alertRouteId")
	public Long getAlertRouteId() {
		return alertRouteId;
	}

	public void setAlertRouteId(Long alertRouteId) {
		this.alertRouteId = alertRouteId;
	}

	@Column(name = "alertStatId")
	public Long getAlertStatId() {
		return alertStatId;
	}

	public void setAlertStatId(Long alertStatId) {
		this.alertStatId = alertStatId;
	}

	@Column(name = "alertUserId")
	public Long getAlertUserId() {
		return alertUserId;
	}

	public void setAlertUserId(Long alertUserId) {
		this.alertUserId = alertUserId;
	}

	@Column(name = "alertVisited")
	public Boolean getAlertVisited() {
		return alertVisited;
	}

	public void setAlertVisited(Boolean alertVisited) {
		this.alertVisited = alertVisited;
	}
}
