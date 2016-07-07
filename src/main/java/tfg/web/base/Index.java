package tfg.web.base;

import java.applet.Applet;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;

import tfg.model.tweetservice.TweetService;
import tfg.web.services.AuthenticationPolicy;
import tfg.web.services.AuthenticationPolicyType;

@AuthenticationPolicy(AuthenticationPolicyType.ALL_USERS)

public class Index extends Applet {
	
	@Inject
	private TweetService tweetService;
	
	private double left=51.508742;
	private double right=-0.120850;
	
	void onActivate() {
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
