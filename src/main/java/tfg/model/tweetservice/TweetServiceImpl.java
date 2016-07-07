package tfg.model.tweetservice;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.tools.ant.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.github.sommeri.less4j.core.ast.Nth.Form;

import de.micromata.opengis.kml.v_2_2_0.Boundary;
import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Feature;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Geometry;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.LinearRing;
import de.micromata.opengis.kml.v_2_2_0.MultiGeometry;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Point;
import de.micromata.opengis.kml.v_2_2_0.Polygon;
import tfg.model.route.Route;
import tfg.model.route.RouteDao;
import tfg.model.routeservice.RouteBlock;
import tfg.model.tweet.Tweet;
import tfg.model.tweet.TweetDao;
import tfg.model.util.CoordinateAux;
import tfg.model.util.InstanceNotFoundException;
import tfg.model.util.ModelConstants;
import tfg.model.util.Result;
import tfg.model.util.SentimentsCount;
import tfg.model.util.TweetBlock;
import twitter4j.GeoLocation;
import twitter4j.GeoQuery;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

@Service("tweetService")
@Transactional
public class TweetServiceImpl implements TweetService {
	
	@Autowired
	private TweetDao tweetDao;
	
	int i=0;
	
	public List<Coordinate> getCoordinates(String kmlFile){
		InputStream is;
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
		try {
			is = new FileInputStream(kmlFile);
			Kml kml = Kml.unmarshal(is);
		    Feature feature = kml.getFeature();
		    if(feature != null) {
		        if(feature instanceof Document) {
		            Document document = (Document) feature;
		            List<Feature> featureList = document.getFeature();
		            for(Feature documentFeature : featureList) {
		                if(documentFeature instanceof Folder) {
		                	Folder folder = (Folder) documentFeature;
		                	List<Feature> features = folder.getFeature();
		    	            for(Feature featur : features) {
		    	            	if (featur instanceof Placemark){
				                    Placemark placemark = (Placemark) featur;
				                    Geometry geometry = placemark.getGeometry();
				                    if(geometry != null) {
				            	        if(geometry instanceof Point) {
				            	            Point point = (Point) geometry;
				            	            List<Coordinate> coordinatesAux= point.getCoordinates();
				            	            coordinates.addAll(coordinatesAux);
				            	        }
				                    }
		    	            	}
		    	            }
		                }
		            }
		        }
		    }
		    return coordinates;
		} catch (FileNotFoundException e) {
			return null;
		}
	}
	
	public void parseKml(String src, Long routeId) {
	    InputStream is;
		try {
			is = new FileInputStream(src);
			 Kml kml = Kml.unmarshal(is);
			    Feature feature = kml.getFeature();
			    parseFeature(feature,routeId);
		} catch (FileNotFoundException e) {
		}
	   
	}

	private void parseFeature(Feature feature,Long routeId) {
	    if(feature != null) {
	        if(feature instanceof Document) {
	            Document document = (Document) feature;
	            List<Feature> featureList = document.getFeature();
	            for(Feature documentFeature : featureList) {
	                if(documentFeature instanceof Folder) {
	                	Folder folder = (Folder) documentFeature;
	                	List<Feature> features = folder.getFeature();
	    	            for(Feature featur : features) {
	    	            	if (featur instanceof Placemark){
			                    Placemark placemark = (Placemark) featur;
			                    Geometry geometry = placemark.getGeometry();
			                    parseGeometry(geometry,routeId);
	    	            	}
	    	            }
	                }
	            }
	        }
	    }
	}

	private void parseGeometry(Geometry geometry, Long routeId) {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey(ModelConstants.getOauthconsumerkey())
				.setOAuthConsumerSecret(ModelConstants.getOauthconsumersecret())
				.setOAuthAccessToken(ModelConstants.getOauthaccesstoken())

				.setOAuthAccessTokenSecret(ModelConstants.getOauthaccesstokensecret())
				.setHttpConnectionTimeout(ModelConstants.getHttpconnectiontimeout());
		TwitterFactory tf = new TwitterFactory(cb.build());
		Twitter twitter = tf.getInstance();
		String resUnit = "mi";
		int res=10;
		Tweet tweetBD;
	    if(geometry != null) {
	        if(geometry instanceof Point) {
	            Point point = (Point) geometry;
	            List<Coordinate> coordinates= point.getCoordinates();
	            for(Coordinate coordinate : coordinates) {
	            	if (i%5==0){
	    				Query query=new Query().geoCode(new GeoLocation(coordinate.getLatitude(), coordinate.getLongitude()), res, resUnit);
	    				query.setCount(5000);
	    				
	    				QueryResult result;
						try {
							result = twitter.search(query);
							List<Status> qrTweets = result.getTweets();

		    				for (Status tweet : qrTweets) {  
		    					if (!tweetDao.containsIllegalCharacters(tweet.getText()) && tweet.getGeoLocation()!=null && tweet.getText()!="" && (tweet.getText().toLowerCase().contains("camino")||tweet.getText().toLowerCase().contains("santiago"))){
		    							tweetBD=new Tweet(tweet.getId(),tweet.getText().replaceAll("\n", " "),tweet.getCreatedAt(),tweet.getCurrentUserRetweetId(),tweet.getFavoriteCount(),tweet.getGeoLocation().getLatitude(),tweet.getGeoLocation().getLongitude(),tweet.getRetweetCount(),tweet.getUser().getId(),tweet.isFavorited(),tweet.isRetweeted(),routeId,null);
		    						try {
		    							tweetDao.find(tweetBD.getTweetId());
		    						} catch (InstanceNotFoundException e) {
		    							tweetDao.save(tweetBD);
		    						}
		    					}
		    			}
						} catch (TwitterException e1) {
						}
						
	            	}
	            	i++;	
                }
	        }
	    }
	}
	
	@Override
	public List<Tweet> findByRoute(Long routeId) {
		return tweetDao.findByRoute(routeId);
	}

	@Override
	public void updateTweet(Tweet tweet) {
		tweetDao.save(tweet);
		
	}

	@Override
	public List<SentimentsCount> getSentimentCount(Long routeId, Date dateStart, Date dateEnd) {
		return tweetDao.getSentimentCount(routeId, dateStart, dateEnd);
	}

	@Override
	public List<Result> getSentimentsByDate(Long routeId, String sentiment, Date dateStart, Date dateEnd) {
		return tweetDao.getSentimentsByDate(routeId, sentiment, dateStart, dateEnd);
	}

	@Override
	public TweetBlock findByRoute(Long routeId, int startIndex, int count, Date dateStart, Date dateEnd) {
		List<Tweet> tweets = tweetDao.findByRoute(routeId, startIndex, count+1, dateStart, dateEnd);

		boolean existMoreTweets = tweets.size() == (count + 1);

		if (existMoreTweets) {
			tweets.remove(tweets.size() - 1);
		}
		return new TweetBlock(existMoreTweets,tweets);
	}

	@Override
	public Tweet findTweet(Long tweetId) {
		try {
			return tweetDao.find(tweetId);
		} catch (InstanceNotFoundException e) {
			return null;
		}
	}

	@Override
	public List<Tweet> getTweetsByDateAndSentiment(Long routeId,
			String sentiment, Date dateStart, Date dateEnd) {
		return tweetDao.getTweetsByDateAndSentiment(routeId, sentiment, dateStart, dateEnd);
	}

	@Override
	public List<Result> getSentimentsByDay(Long routeId, String sentiment,
			Date dateStart, Date dateEnd) {
		return tweetDao.getSentimentsByDay(routeId, sentiment, dateStart, dateEnd);
	}
}