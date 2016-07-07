package tfg.web.pages;


import java.util.ArrayList;
import java.util.List;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;

import tfg.model.tweetservice.TweetService;
import tfg.model.util.CoordinateAux;
import tfg.web.services.AuthenticationPolicy;
import tfg.web.services.AuthenticationPolicyType;

@AuthenticationPolicy(AuthenticationPolicyType.ALL_USERS)

public class Index{
	
	@Inject
	private TweetService tweetService;
	
	
	
	Double[][] coordinates;
	
	private double left=43.345164;
	private double right=-8.427373;
	
	ArrayList<Double> coordinatesLatitude= new ArrayList<Double>();
	ArrayList<Double> coordinatesLongitude= new ArrayList<Double>();
	
	void onActivate() {
		coordinatesLatitude.add(left);
		coordinatesLatitude.add(43.386058);
		coordinatesLongitude.add(right);
		coordinatesLongitude.add(-8.406463);
    }

	public ArrayList<Double> getCoordinatesLatitude() {
		return coordinatesLatitude;
	}

	public void setCoordinatesLatitude(ArrayList<Double> coordinatesLatitude) {
		this.coordinatesLatitude = coordinatesLatitude;
	}

	public ArrayList<Double> getCoordinatesLongitude() {
		return coordinatesLongitude;
	}

	public void setCoordinatesLongitude(ArrayList<Double> coordinatesLongitude) {
		this.coordinatesLongitude = coordinatesLongitude;
	}

	public JSONObject getOptions(){
        JSONObject json = new JSONObject();
        return json;
    }

	public double getLeft() {
		return left;
	}

	public void setLeft(double left) {
		this.left = left;
	}

	public double getRight() {
		return right;
	}

	public void setRight(double right) {
		this.right = right;
	}
}
