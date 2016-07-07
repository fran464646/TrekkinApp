package tfg.model.statservice;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tfg.model.stat.Stat;
import tfg.model.stat.StatDao;
import tfg.model.util.InstanceNotFoundException;

@Service("statService")
@Transactional
public class StatServiceImpl implements StatService {
	
	@Autowired
	private StatDao statDao;

	@Override
	public List<Stat> findByRoute(Long routeId, int startIndex, int count) {
		return statDao.findByRoute(routeId, startIndex, count);
	}

	@Override
	public List<Stat> findStatByRouteAndDate(Long routeId, int startIndex,
			int count, Date dateStart, Date dateEnd) {
		return statDao.findStatByRouteAndDate(routeId, startIndex, count, dateStart, dateEnd);
	}
	
	@Override
	public Double getTypicalDeviation(List<Long> balances) {
		Double sum=0.0;
		Double aritmeticMedia;
		int i=0;
		for (Long value:balances){
			sum=sum+value;
			i++;
		}
		aritmeticMedia=sum/i;
		sum=0.0;
		for (Long value:balances){
			sum=sum+((value-aritmeticMedia)*(value-aritmeticMedia));
		}
		Double sqrt=sum/(i-1);
		return Math.sqrt(sqrt);
	}

	@Override
	public Double getOldMiddle(List<Long> balances) {
		Double sum=0.0;
		int i=0;
		for (Long value:balances){
			sum=sum+value;
			i++;
		}
		return sum/i;
	}

	@Override
	public void saveStat(Stat stat) {
		statDao.save(stat);
		
	}

	@Override
	public Stat findStat(Long statId) {
		try {
			return statDao.find(statId);
		} catch (InstanceNotFoundException e) {
			return null;
		}
	}

}
