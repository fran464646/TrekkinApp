package tfg.model.statservice;

import java.util.Date;
import java.util.List;

import tfg.model.stat.Stat;

public interface StatService {

	public List<Stat> findByRoute(Long routeId,int startIndex, int count);
	
	public List<Stat> findStatByRouteAndDate(Long routeId,int startIndex,int count,Date dateStart,Date dateEnd);
	
	public Double getTypicalDeviation(List<Long> balances);
	
	public Double getOldMiddle(List<Long> balances);
	
	public Stat findStat(Long statId);
	
	public void saveStat(Stat stat);
}
