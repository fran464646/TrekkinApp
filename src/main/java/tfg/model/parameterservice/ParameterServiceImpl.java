package tfg.model.parameterservice;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tfg.model.alert.Alert;
import tfg.model.parameter.Parameter;
import tfg.model.parameter.ParameterDao;
import tfg.model.util.AlertBlock;
import tfg.model.util.ParameterBlock;

@Service("parameterService")
@Transactional
public class ParameterServiceImpl implements ParameterService {
	
	@Autowired
	private ParameterDao parameterDao;

	@Override
	public List<Parameter> findParameterByUserId(Long userId) {
		return parameterDao.findParametersByUser(userId);
	}

	@Override
	public void saveParameter(Parameter parameter) {
		parameterDao.save(parameter);
	}

	@Override
	public List<Parameter> findParameterByType(String type, Long routeId) {
		return parameterDao.findParameterByType(type,routeId);
	}

	@Override
	public void deleteParameter(Long userId, String type) {
		parameterDao.deleteParemeter(type, userId);
	}

	@Override
	public ParameterBlock getParametersByUser(Long userId, int startIndex,
			int count) {
		List<Parameter> parameters = parameterDao.getParametersByUser(userId, startIndex, count+1);

		boolean existMoreParameters = parameters.size() == (count + 1);

		if (existMoreParameters) {
			parameters.remove(parameters.size() - 1);
		}
		return new ParameterBlock(existMoreParameters,parameters);
	}

	@Override
	public void deleteParameterById(Long parameterId) {
		parameterDao.deleteParameterById(parameterId);
		
	}

	
}
