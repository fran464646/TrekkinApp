package tfg.model.stat;

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
public class Stat {

	private Long statId;
	private Date statDate;
	private Long statPNumber;
	private Long statNNumber;
	private Long statOpinionBalance;
	private Double statTipicalDeviation;
	private Boolean statNewNegative;
	private Double statOldMiddle;
	private Long statRouteId;
	
	public Stat(){
		
	}
	
	@Column(name = "statId")
	@SequenceGenerator(
	name = "StatIdGenerator",
	sequenceName = "StatSeq")
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "StatIdGenerator")
	public Long getStatId() {
		return statId;
	}
	public void setStatId(Long statId) {
		this.statId = statId;
	}
	
	@Column(name = "statDate")
	public Date getStatDate() {
		return statDate;
	}

	public void setStatDate(Date statDate) {
		this.statDate = statDate;
	}

	@Column(name = "statPNumber")
	public Long getStatPNumber() {
		return statPNumber;
	}

	public void setStatPNumber(Long statPNumber) {
		this.statPNumber = statPNumber;
	}

	@Column(name = "statNNumber")
	public Long getStatNNumber() {
		return statNNumber;
	}

	public void setStatNNumber(Long statNNumber) {
		this.statNNumber = statNNumber;
	}

	@Column(name = "statOpinionBalance")
	public Long getStatOpinionBalance() {
		return statOpinionBalance;
	}

	public void setStatOpinionBalance(Long statOpinionBalance) {
		this.statOpinionBalance = statOpinionBalance;
	}

	@Column(name = "statTipicalDeviation")
	public Double getStatTipicalDeviation() {
		return statTipicalDeviation;
	}

	public void setStatTipicalDeviation(Double statTipicalDeviation) {
		this.statTipicalDeviation = statTipicalDeviation;
	}

	@Column(name = "statNewNegative")
	public Boolean getStatNewNegative() {
		return statNewNegative;
	}

	public void setStatNewNegative(Boolean statNewNegative) {
		this.statNewNegative = statNewNegative;
	}

	@Column(name = "statOldMiddle")
	public Double getStatOldMiddle() {
		return statOldMiddle;
	}

	public void setStatOldMiddle(Double statOldMiddle) {
		this.statOldMiddle = statOldMiddle;
	}

	@Column(name = "statRouteId")
	public Long getStatRouteId() {
		return statRouteId;
	}

	public void setStatRouteId(Long statRouteId) {
		this.statRouteId = statRouteId;
	}

}
