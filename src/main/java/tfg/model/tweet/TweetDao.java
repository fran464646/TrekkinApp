package tfg.model.tweet;

import java.util.Date;
import java.util.List;

import tfg.model.util.GenericDao;
import tfg.model.util.Result;
import tfg.model.util.SentimentsCount;

public interface TweetDao extends GenericDao<Tweet, Long>{
	
	public List<Tweet> findByRoute(Long routeId);
	
	public List<Tweet> findByRoute(Long routeId,int startIndex,int count,Date dateStart,Date dateEnd);
	
	public boolean containsIllegalCharacters(String displayName);
	
	public List<SentimentsCount> getSentimentCount(Long routeId, Date dateStart, Date dateEnd);
	
	public List<Result> getSentimentsByDate(Long routeId, String sentiment, Date dateStart, Date dateEnd);
	
	public List<Result> getSentimentsByDay(Long routeId, String sentiment, Date dateStart, Date dateEnd);
	
	public List<Tweet> getTweetsByDateAndSentiment(Long routeId, String sentiment, Date dateStart, Date dateEnd);
	
}
