package tfg.model.route;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.BatchSize;

@Entity
@BatchSize(size=10)
public class Route {

	private Long routeId;
	private String routeName;
	private String routeStart;
	private String routeEnd;
	private Long kilometers;
	private Long positiveSlope;
	private Long negativeSlope;
	private Long numberOfHostels;
	private String kmlFile;
	
	public Route(){
		
	}
	
	public Route(Long routeId, String routeName, String routeStart, String routeEnd, Long kilometers, Long positiveSlope,
			Long negativeSlope, Long numberOfHostels, String kmlFile) {
		super();
		this.routeId = routeId;
		this.routeName = routeName;
		this.routeStart = routeStart;
		this.routeEnd = routeEnd;
		this.kilometers = kilometers;
		this.positiveSlope = positiveSlope;
		this.negativeSlope = negativeSlope;
		this.numberOfHostels = numberOfHostels;
		this.kmlFile = kmlFile;
	}
	
	
	@Column(name = "kmlFile")
	public String getKmlFile() {
		return kmlFile;
	}



	public void setKmlFile(String kmlFile) {
		this.kmlFile = kmlFile;
	}



	@Column(name = "kilometers")
	public Long getKilometers() {
		return kilometers;
	}



	public void setKilometers(Long kilometers) {
		this.kilometers = kilometers;
	}


	@Column(name = "positiveSlope")
	public Long getPositiveSlope() {
		return positiveSlope;
	}



	public void setPositiveSlope(Long positiveSlope) {
		this.positiveSlope = positiveSlope;
	}


	@Column(name = "negativeSlope")
	public Long getNegativeSlope() {
		return negativeSlope;
	}



	public void setNegativeSlope(Long negativeSlope) {
		this.negativeSlope = negativeSlope;
	}


	@Column(name = "numberOfHostels")
	public Long getNumberOfHostels() {
		return numberOfHostels;
	}



	public void setNumberOfHostels(Long numberOfHostels) {
		this.numberOfHostels = numberOfHostels;
	}



	@Column(name = "routeId")
	@SequenceGenerator( // It only takes effect for
	name = "RouteIdGenerator", // databases providing identifier
	sequenceName = "RouteSeq")
	// generators.
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "RouteIdGenerator")
	public Long getRouteId() {
		return routeId;
	}
	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}
	
	@Column(name = "routeName")
	public String getRouteName() {
		return routeName;
	}
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	
	@Column(name = "routeStart")
	public String getRouteStart() {
		return routeStart;
	}
	public void setRouteStart(String routeStart) {
		this.routeStart = routeStart;
	}
	
	@Column(name = "routeEnd")
	public String getRouteEnd() {
		return routeEnd;
	}
	public void setRouteEnd(String routeEnd) {
		this.routeEnd = routeEnd;
	}
	
	

}
