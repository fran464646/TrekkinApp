package tfg.model.parameter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.BatchSize;

@Entity
@BatchSize(size=10)
public class Parameter {
	
	private Long parameterId;
	private String parameterKey;
	private Long parameterValue;
	private Long parameterUserId;
	private Long parameterRouteId;
	private String parameterRouteName;
	private String parameterDescription;
	
	public Parameter() {
		super();
	}
	
	public Parameter(Long parameterId, String parameterKey,
			Long parameterValue, Long parameterUserId, Long parameterRouteId, String parameterRouteName, String parameterDescription) {
		super();
		this.parameterId = parameterId;
		this.parameterKey = parameterKey;
		this.parameterValue = parameterValue;
		this.parameterUserId = parameterUserId;
		this.parameterRouteId = parameterRouteId;
		this.parameterRouteName = parameterRouteName;
		this.parameterDescription = parameterDescription;
	}
	
	@Column(name = "parameterId")
	@SequenceGenerator(
	name = "ParameterIdGenerator",
	sequenceName = "ParameterSeq")
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "ParameterIdGenerator")
	public Long getParameterId() {
		return parameterId;
	}
	public void setParameterId(Long parameterId) {
		this.parameterId = parameterId;
	}
	
	@Column(name = "parameterKey")
	public String getParameterKey() {
		return parameterKey;
	}
	public void setParameterKey(String parameterKey) {
		this.parameterKey = parameterKey;
	}
	@Column(name = "parameterValue")
	public Long getParameterValue() {
		return parameterValue;
	}
	public void setParameterValue(Long parameterValue) {
		this.parameterValue = parameterValue;
	}
	
	@Column(name = "parameterUserId")
	public Long getParameterUserId() {
		return parameterUserId;
	}
	public void setParameterUserId(Long parameterUserId) {
		this.parameterUserId = parameterUserId;
	}

	@Column(name = "parameterRouteId")
	public Long getParameterRouteId() {
		return parameterRouteId;
	}

	public void setParameterRouteId(Long parameterRouteId) {
		this.parameterRouteId = parameterRouteId;
	}

	@Column(name = "parameterRouteName")
	public String getParameterRouteName() {
		return parameterRouteName;
	}

	public void setParameterRouteName(String parameterRouteName) {
		this.parameterRouteName = parameterRouteName;
	}

	@Column(name = "parameterDescription")
	public String getParameterDescription() {
		return parameterDescription;
	}

	public void setParameterDescription(String parameterDescription) {
		this.parameterDescription = parameterDescription;
	}
	
	
}
