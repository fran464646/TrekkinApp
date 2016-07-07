package tfg.model.stat;

import java.util.Date;
import java.util.List;

import tfg.model.util.GenericDao;

public interface StatDao extends GenericDao<Stat, Long>{
	
	public List<Stat> findByRoute(Long routeId,int startIndex, int count);
	
	public List<Stat> findStatByRouteAndDate(Long routeId,int startIndex,int count,Date dateStart,Date dateEnd);

}
