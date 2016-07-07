package tfg.web.pages.search;


import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.annotations.SessionState;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.json.JSONObject;
import org.apache.tapestry5.services.PersistentLocale;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;

import tfg.model.parameter.Parameter;
import tfg.model.parameterservice.ParameterService;
import tfg.model.routeservice.RouteBlock;
import tfg.model.routeservice.RouteService;
import tfg.model.tweetservice.TweetService;
import tfg.model.util.CoordinateAux;
import tfg.model.util.ParameterBlock;
import tfg.web.pages.Index;
import tfg.web.services.AuthenticationPolicy;
import tfg.web.services.AuthenticationPolicyType;
import tfg.web.util.SupportedLanguages;
import tfg.web.util.UserSession;

@AuthenticationPolicy(AuthenticationPolicyType.ALL_USERS)

public class ConfigureAlerts{
	
	@Inject
	private TweetService tweetService;
	
	@Inject
	private RouteService routeService;
	
	@Inject
	private ParameterService parameterService;
	
	@SessionState(create=false)
    private UserSession userSession;
	
	private Long positiveOpinions;
	
	private Long positiveBalance;
	
	private Long negativeBalance;
	
	private Boolean existsPositiveOpinions=false;
	
	private Boolean existsNegativeOpinions=false;
	
	private Boolean existsPositiveBalance=false;
	
	private Boolean existsNegativeBalance=false;
	
	private String routeNames="";
	
	private Parameter parameter;
	
	private ParameterBlock parameterBlock;
	
	private int startIndex;
	
	private int PARAMETERS_PER_PAGE=5;
	
	@Property
	private Boolean checkBoxNewNegative;
	
	List<String> routes;
	
	private Long negativeOpinions;

    @Inject
    private JavaScriptSupport javaScriptSupport;

    @Property
    private String route;

    @Inject
    private Locale locale;

    @Inject
    private PersistentLocale persistentLocale;

    void onPrepareForRender() {
    }

	void afterRender() {
        javaScriptSupport.require("bootstrap/tab");
    }
	
	public Object[] getPreviousLinkContext() {
		
		if (startIndex-PARAMETERS_PER_PAGE >= 0) {
			return new Object[] {startIndex-PARAMETERS_PER_PAGE};
		} else {
			return null;
		}
		
	}
	
	public Object[] getNextLinkContext() {
		
		if (parameterBlock.isExistsMoreParameters()) {
			return new Object[] {startIndex+PARAMETERS_PER_PAGE};
		} else {
			return null;
		}
		
	}
	
	public void onDelete(Long parameterId){
		parameterService.deleteParameterById(parameterId);
	}
	
	void onActivate(int startIndex) {
		this.startIndex=startIndex;
		this.routeNames="";
    	this.routes=routeService.getRouteNames();
		String result="";
		for (String route:routes){
			result=result.concat(route+",");
		}
		result=result.substring(0,result.length()-1);
		this.routeNames=result;
		this.parameterBlock=parameterService.getParametersByUser(userSession.getUserProfileId(), startIndex, PARAMETERS_PER_PAGE);
    }
	
	void onSuccess(){
		boolean insert=true;
		parameterBlock=parameterService.getParametersByUser(userSession.getUserProfileId(), startIndex, PARAMETERS_PER_PAGE);
		routes=routeService.getRouteNames();
		RouteBlock routeBlock=routeService.findRouteByName(route, 0, 1);
		if (positiveOpinions!=null){
			
			List<Parameter> parameters=parameterService.findParameterByType("positiveOpinions",routeBlock.getRoutes().get(0).getRouteId());
			for (Parameter parameter:parameters){
				if (parameter.getParameterRouteId()==routeBlock.getRoutes().get(0).getRouteId()){
					insert=false;
				}
			}
			if (insert){
				Parameter parameter=new Parameter();
				parameter.setParameterKey("positiveOpinions");
				parameter.setParameterDescription("Límite de opiniones positivas");
				parameter.setParameterRouteName(routeBlock.getRoutes().get(0).getRouteName());
				parameter.setParameterValue(positiveOpinions);
				parameter.setParameterUserId(userSession.getUserProfileId());
				parameter.setParameterRouteId(routeBlock.getRoutes().get(0).getRouteId());
				parameterService.saveParameter(parameter);
			}else{
				Parameter parameter=parameters.get(0);
				parameter.setParameterValue(positiveOpinions);
				parameter.setParameterRouteId(routeBlock.getRoutes().get(0).getRouteId());
				parameterService.saveParameter(parameter);
			}
		}else{
			if (existsPositiveOpinions){
				parameterService.deleteParameter(userSession.getUserProfileId(), "positiveOpinions");
			}
		}
		if (negativeOpinions!=null){
			
			List<Parameter> parameters=parameterService.findParameterByType("negativeOpinions",routeBlock.getRoutes().get(0).getRouteId());
			insert=true;
			for (Parameter parameter:parameters){
				if (parameter.getParameterRouteId()==routeBlock.getRoutes().get(0).getRouteId()){
					insert=false;
				}
			}
			if (insert){
				Parameter parameter=new Parameter();
				parameter.setParameterKey("negativeOpinions");
				parameter.setParameterDescription("Límite de opiniones negativas");
				parameter.setParameterRouteName(routeBlock.getRoutes().get(0).getRouteName());
				parameter.setParameterValue(negativeOpinions);
				parameter.setParameterUserId(userSession.getUserProfileId());
				parameter.setParameterRouteId(routeBlock.getRoutes().get(0).getRouteId());
				parameterService.saveParameter(parameter);
			}else{
				Parameter parameter=parameters.get(0);
				parameter.setParameterValue(negativeOpinions);
				parameter.setParameterRouteId(routeBlock.getRoutes().get(0).getRouteId());
				parameterService.saveParameter(parameter);
			}
		}else{
			if (existsNegativeOpinions){
				parameterService.deleteParameter(userSession.getUserProfileId(), "negativeOpinions");
			}
		}
		if (positiveBalance!=null){
			
			List<Parameter> parameters=parameterService.findParameterByType("positiveBalance",routeBlock.getRoutes().get(0).getRouteId());
			insert=true;
			for (Parameter parameter:parameters){
				if (parameter.getParameterRouteId()==routeBlock.getRoutes().get(0).getRouteId()){
					insert=false;
				}
			}
			if (insert){
				Parameter parameter=new Parameter();
				parameter.setParameterKey("positiveBalance");
				parameter.setParameterDescription("Límite positivo en el balance de opiniones");
				parameter.setParameterRouteName(routeBlock.getRoutes().get(0).getRouteName());
				parameter.setParameterValue(positiveBalance);
				parameter.setParameterUserId(userSession.getUserProfileId());
				parameter.setParameterRouteId(routeBlock.getRoutes().get(0).getRouteId());
				parameterService.saveParameter(parameter);
			}else{
				Parameter parameter=parameters.get(0);
				parameter.setParameterValue(positiveBalance);
				parameter.setParameterRouteId(routeBlock.getRoutes().get(0).getRouteId());
				parameterService.saveParameter(parameter);
			}
		}else{
			if (existsPositiveBalance){
				parameterService.deleteParameter(userSession.getUserProfileId(), "positiveBalance");
			}
		}
		if (negativeBalance!=null){
			
			List<Parameter> parameters=parameterService.findParameterByType("negativeBalance",routeBlock.getRoutes().get(0).getRouteId());
			insert=true;
			for (Parameter parameter:parameters){
				if (parameter.getParameterRouteId()==routeBlock.getRoutes().get(0).getRouteId()){
					insert=false;
				}
			}
			if (insert){
				Parameter parameter=new Parameter();
				parameter.setParameterKey("negativeBalance");
				parameter.setParameterDescription("Límite negativo en el balance de opiniones");
				parameter.setParameterRouteName(routeBlock.getRoutes().get(0).getRouteName());
				parameter.setParameterValue(negativeBalance);
				parameter.setParameterUserId(userSession.getUserProfileId());
				parameter.setParameterRouteId(routeBlock.getRoutes().get(0).getRouteId());
				parameterService.saveParameter(parameter);
			}else{
				Parameter parameter=parameters.get(0);
				parameter.setParameterValue(negativeBalance);
				parameter.setParameterRouteId(routeBlock.getRoutes().get(0).getRouteId());
				parameterService.saveParameter(parameter);
			}
		}else{
			if (existsNegativeBalance){
				parameterService.deleteParameter(userSession.getUserProfileId(), "negativeBalance");
			}
		}
	}
	
	public Long getPositiveOpinions() {
		return positiveOpinions;
	}

	public void setPositiveOpinions(Long positiveOpinions) {
		this.positiveOpinions = positiveOpinions;
	}

	public Long getNegativeOpinions() {
		return negativeOpinions;
	}

	public void setNegativeOpinions(Long negativeOpinions) {
		this.negativeOpinions = negativeOpinions;
	}

	public Long getPositiveBalance() {
		return positiveBalance;
	}

	public void setPositiveBalance(Long positiveBalance) {
		this.positiveBalance = positiveBalance;
	}

	public Long getNegativeBalance() {
		return negativeBalance;
	}

	public void setNegativeBalance(Long negativeBalance) {
		this.negativeBalance = negativeBalance;
	}

	public Boolean getExistsPositiveOpinions() {
		return existsPositiveOpinions;
	}

	public void setExistsPositiveOpinions(Boolean existsPositiveOpinions) {
		this.existsPositiveOpinions = existsPositiveOpinions;
	}

	public Boolean getExistsNegativeOpinions() {
		return existsNegativeOpinions;
	}

	public void setExistsNegativeOpinions(Boolean existsNegativeOpinions) {
		this.existsNegativeOpinions = existsNegativeOpinions;
	}

	public Boolean getExistsPositiveBalance() {
		return existsPositiveBalance;
	}

	public void setExistsPositiveBalance(Boolean existsPositiveBalance) {
		this.existsPositiveBalance = existsPositiveBalance;
	}

	public Boolean getExistsNegativeBalance() {
		return existsNegativeBalance;
	}

	public void setExistsNegativeBalance(Boolean existsNegativeBalance) {
		this.existsNegativeBalance = existsNegativeBalance;
	}

	public String getRouteNames() {
		this.routes=routeService.getRouteNames();
		String result="";
		for (String route:routes){
			result=result.concat(route+",");
		}
		result=result.substring(0,result.length()-1);
		this.routeNames=result;
		return result;
	}

	public void setRouteNames(String routeNames) {
		this.routeNames = routeNames;
	}

	public List<Parameter> getParameters() {
		this.parameterBlock=parameterService.getParametersByUser(userSession.getUserProfileId(), startIndex, PARAMETERS_PER_PAGE);
		return this.parameterBlock.getParameters();
	}

	public Parameter getParameter() {
		return parameter;
	}

	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}
}
