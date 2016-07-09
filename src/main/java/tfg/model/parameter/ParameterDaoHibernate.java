package tfg.model.parameter;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.springframework.stereotype.Repository;

import tfg.model.util.GenericDaoHibernate;
import tfg.model.util.InstanceNotFoundException;

@Repository("parameterDao")
public class ParameterDaoHibernate extends
		GenericDaoHibernate<Parameter, Long> implements ParameterDao {

	@Override
	public List<Parameter> findParametersByUser(Long userId) {
		Query tmpQuery = getSession().createQuery(
    			"SELECT p FROM Parameter p WHERE p.parameterUserId= :userId")
    			.setParameter("userId", userId);
    	return tmpQuery.list();
	}

	@Override
	public List<Parameter> findParameterByType(String type, Long routeId) {
		Query tmpQuery = getSession().createQuery(
    			"SELECT p FROM Parameter p WHERE p.parameterKey= :type AND p.parameterRouteId= :routeId")
    			.setParameter("type", type).setParameter("routeId", routeId);
    	return tmpQuery.list();
	}

	@Override
	public void deleteParemeter(String type, Long userId) {
		Query tmpQuery = getSession().createQuery(
    			"DELETE FROM Parameter p WHERE p.parameterKey= :type AND p.parameterUserId= :userId")
    			.setParameter("type", type).setParameter("userId", userId);
    	tmpQuery.executeUpdate();
		
	}

	@Override
	public List<Parameter> getParametersByUser(Long userId, int startIndex, int count) {
		Query tmpQuery = getSession().createQuery(
    			"SELECT p FROM Parameter p WHERE p.parameterUserId= :userId")
    			.setParameter("userId", userId).setMaxResults(count).setFirstResult(startIndex);
    	return tmpQuery.list();
	}

	@Override
	public void deleteParameterById(Long parameterId) {
		Query tmpQuery = getSession().createQuery(
    			"DELETE FROM Parameter p WHERE p.parameterId= :parameterId").setParameter("parameterId", parameterId);
    	tmpQuery.executeUpdate();
	}		

}