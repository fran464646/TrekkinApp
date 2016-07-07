package tfg.model.util;

import java.util.ArrayList;
import java.util.List;

import tfg.model.alert.Alert;
import tfg.model.parameter.Parameter;
import tfg.model.tweet.Tweet;

public class ParameterBlock {

	private boolean existsMoreParameters;
	
	private List<Parameter> parameters;
	
	public ParameterBlock(boolean existsMoreParameters, List<Parameter> parameters) {
		super();
		this.existsMoreParameters = existsMoreParameters;
		this.parameters = parameters;
	}
	
	public ParameterBlock() {
	}

	public boolean isExistsMoreParameters() {
		return existsMoreParameters;
	}

	public void setExistsMoreParameters(boolean existsMoreParameters) {
		this.existsMoreParameters = existsMoreParameters;
	}

	public List<Parameter> getParameters() {
		return parameters;
	}

	public void setParameters(List<Parameter> parameters) {
		this.parameters = parameters;
	}
	
	
}
