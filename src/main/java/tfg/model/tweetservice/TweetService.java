package tfg.model.tweetservice;

import java.util.Date;
import java.util.List;

import tfg.model.tweet.Tweet;
import tfg.model.util.CoordinateAux;
import tfg.model.util.Result;
import tfg.model.util.SentimentsCount;
import tfg.model.util.TweetBlock;
import de.micromata.opengis.kml.v_2_2_0.Coordinate;

public interface TweetService {
	
	public List<Tweet> findByRoute(Long routeId);
	
	public TweetBlock findByRoute(Long routeId, int startIndex, int count, Date dateStart, Date dateEnd);
	
	public void updateTweet(Tweet tweet);
	
	public List<SentimentsCount> getSentimentCount(Long routeId, Date dateStart, Date dateEnd);
	
	public List<Result> getSentimentsByDate(Long routeId, String sentiment, Date dateStart, Date dateEnd);
	
	public List<Coordinate> getCoordinates(String kmlFile);
	
	public void parseKml(String src, Long routeId); 
	
	public Tweet findTweet(Long tweetId);
	
	public List<Tweet> getTweetsByDateAndSentiment(Long routeId, String sentiment, Date dateStart, Date dateEnd);
	
	public List<Result> getSentimentsByDay(Long routeId, String sentiment, Date dateStart, Date dateEnd);
}
