package tfg.model.parameter;

import java.util.List;

import tfg.model.util.GenericDao;

public interface ParameterDao extends GenericDao<Parameter, Long>{

    public List<Parameter> findParametersByUser(Long userId);
    
    public List<Parameter> findParameterByType(String type, Long routeId);
    
    public void deleteParemeter(String type, Long userId);
    
    public void deleteParameterById(Long parameterId);
    
    public List<Parameter> getParametersByUser(Long userId, int startIndex, int count);
}
