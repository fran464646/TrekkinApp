package tfg.model.parameterservice;

import java.util.List;

import tfg.model.parameter.Parameter;
import tfg.model.util.ParameterBlock;

public interface ParameterService {

	public List<Parameter> findParameterByUserId(Long userId);
	
	public List<Parameter> findParameterByType(String type, Long routeId);
	
	public void saveParameter(Parameter parameter);
	
	public void deleteParameter(Long userId,String type);
	
	public void deleteParameterById(Long parameterId);
	
	public ParameterBlock getParametersByUser(Long userId, int startIndex, int count);
}
