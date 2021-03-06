package tfg.test.tweetservice;

import static org.junit.Assert.*;
import static tfg.test.util.GlobalNames.SPRING_CONFIG_TEST_FILE;
import static tfg.model.util.GlobalNames.SPRING_CONFIG_FILE;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;
import tfg.model.route.Route;
import tfg.model.route.RouteDao;
import tfg.model.tweet.Tweet;
import tfg.model.tweet.TweetDao;
import tfg.model.tweetservice.TweetService;
import tfg.model.util.Result;
import tfg.model.util.SentimentsCount;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { SPRING_CONFIG_FILE, SPRING_CONFIG_TEST_FILE })
@Transactional
public class TweetServiceTest {

	 @Autowired
	 private RouteDao routeDao;
	 
	 @Autowired
	 private TweetDao tweetDao;
	 
	 @Autowired
	 private TweetService tweetService;
	
	@Test
	public void findTweetTest() {
		Date date=new Date();
		Date dateStart;
		Date dateEnd;
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.HOUR_OF_DAY, -5);
	    dateStart=cal.getTime();
	    cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.HOUR_OF_DAY, 5);
	    dateEnd=cal.getTime();
		Route route=new Route();
		route.setKilometers(1000l);
		route.setKmlFile("kmlfile");
		route.setNegativeSlope(100l);
		route.setNumberOfHostels(30l);
		route.setPositiveSlope(50l);
		route.setRouteEnd("Camariñas");
		route.setRouteName("Rutita");
		route.setRouteStart("Vimianzo");
		routeDao.save(route);
		Tweet tweet=new Tweet();
		tweet.setRetweetedId(1l);
		tweet.setTweetCreationDate(date);
		tweet.setTweetFavourites(1);
		tweet.setTweetIsFavourite(false);
		tweet.setTweetIsRetweeted(false);
		tweet.setTweetLatitude(1.23);
		tweet.setTweetLongitude(2.05);
		tweet.setTweetRetweets(2);
		tweet.setTweetRouteId(route.getRouteId());
		tweet.setTweetSentiment("P");
		tweet.setTweetText("Mola mazo");
		tweet.setTweetId(1l);
		tweet.setTweetUserId(1l);
		tweetDao.save(tweet);
		Tweet tweetAux=tweetService.findTweet(tweet.getTweetId());
		assertEquals(tweet,tweetAux);
	}
	
	@Test
	public void findByRouteIdTest() {
		Date date=new Date();
		Date dateStart;
		Date dateEnd;
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DAY_OF_MONTH, -1);
	    dateStart=cal.getTime();
	    cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DAY_OF_MONTH, 1);
	    dateEnd=cal.getTime();
		Route route=new Route();
		route.setKilometers(1000l);
		route.setKmlFile("kmlfile");
		route.setNegativeSlope(100l);
		route.setNumberOfHostels(30l);
		route.setPositiveSlope(50l);
		route.setRouteEnd("Camariñas");
		route.setRouteName("Rutita");
		route.setRouteStart("Vimianzo");
		routeDao.save(route);
		Tweet tweet=new Tweet();
		tweet.setRetweetedId(1l);
		tweet.setTweetCreationDate(date);
		tweet.setTweetFavourites(1);
		tweet.setTweetIsFavourite(false);
		tweet.setTweetIsRetweeted(false);
		tweet.setTweetLatitude(1.23);
		tweet.setTweetLongitude(2.05);
		tweet.setTweetRetweets(2);
		tweet.setTweetRouteId(route.getRouteId());
		tweet.setTweetSentiment("P");
		tweet.setTweetText("Mola mazo");
		tweet.setTweetId(1l);
		tweet.setTweetUserId(1l);
		tweetDao.save(tweet);
		List<Tweet> tweets=tweetService.findByRoute(route.getRouteId());
		assertEquals(tweets.size(),1);
		assertEquals(tweets.get(0),tweet);
		tweets=tweetService.findByRoute(route.getRouteId(),0,1,dateStart,dateEnd).getTweets();
		assertEquals(tweets.size(),1);
		assertEquals(tweets.get(0),tweet);
		Tweet tweet1=new Tweet();
		tweet1.setRetweetedId(1l);
		tweet1.setTweetCreationDate(date);
		tweet1.setTweetFavourites(1);
		tweet1.setTweetIsFavourite(false);
		tweet1.setTweetIsRetweeted(false);
		tweet1.setTweetLatitude(1.23);
		tweet1.setTweetLongitude(2.05);
		tweet1.setTweetRetweets(2);
		tweet1.setTweetRouteId(route.getRouteId());
		tweet1.setTweetSentiment("P");
		tweet1.setTweetText("Mola pilon");
		tweet1.setTweetId(2l);
		tweet1.setTweetUserId(1l);
		tweetDao.save(tweet1);
		tweets=tweetService.findByRoute(route.getRouteId());
		assertEquals(tweets.size(),2);
		assertEquals(tweets.get(0),tweet);
		assertEquals(tweets.get(1),tweet1);
		tweets=tweetService.findByRoute(route.getRouteId(),0,1,dateStart,dateEnd).getTweets();
		assertEquals(tweets.size(),1);
		assertEquals(tweets.get(0),tweet);
		tweets=tweetService.findByRoute(route.getRouteId(),0,2,dateStart,dateEnd).getTweets();
		assertEquals(tweets.size(),2);
		assertEquals(tweets.get(0),tweet);
		assertEquals(tweets.get(1),tweet1);
	}
	
	@Test
	public void updateTweetTest() {
		Route route=new Route();
		route.setKilometers(1000l);
		route.setKmlFile("kmlfile");
		route.setNegativeSlope(100l);
		route.setNumberOfHostels(30l);
		route.setPositiveSlope(50l);
		route.setRouteEnd("Camariñas");
		route.setRouteName("Rutita");
		route.setRouteStart("Vimianzo");
		routeDao.save(route);
		Tweet tweet=new Tweet();
		tweet.setRetweetedId(1l);
		tweet.setTweetCreationDate(new Date());
		tweet.setTweetFavourites(1);
		tweet.setTweetIsFavourite(false);
		tweet.setTweetIsRetweeted(false);
		tweet.setTweetLatitude(1.23);
		tweet.setTweetLongitude(2.05);
		tweet.setTweetRetweets(2);
		tweet.setTweetRouteId(route.getRouteId());
		tweet.setTweetSentiment("P");
		tweet.setTweetText("Mola mazo");
		tweet.setTweetId(1l);
		tweet.setTweetUserId(1l);
		tweetDao.save(tweet);
		List<Tweet> tweets=tweetService.findByRoute(route.getRouteId());
		assertEquals(tweets.size(),1);
		assertEquals(tweets.get(0),tweet);
		tweet.setTweetSentiment("N");
		tweetDao.save(tweet);
		tweets=tweetService.findByRoute(route.getRouteId());
		assertEquals(tweets.size(),1);
		assertEquals(tweets.get(0).getTweetSentiment(),tweet.getTweetSentiment());
	}
	
	@Test
	public void getSentimentsByDateTest() {
		Date date=new Date();
		Date dateStart;
		Date dateEnd;
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DAY_OF_MONTH, -1);
	    dateStart=cal.getTime();
	    cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DAY_OF_MONTH, 1);
	    dateEnd=cal.getTime();
		Route route=new Route();
		route.setKilometers(1000l);
		route.setKmlFile("kmlfile");
		route.setNegativeSlope(100l);
		route.setNumberOfHostels(30l);
		route.setPositiveSlope(50l);
		route.setRouteEnd("Camariñas");
		route.setRouteName("Rutita");
		route.setRouteStart("Vimianzo");
		routeDao.save(route);
		Tweet tweet=new Tweet();
		tweet.setRetweetedId(1l);
		tweet.setTweetCreationDate(date);
		tweet.setTweetFavourites(1);
		tweet.setTweetIsFavourite(false);
		tweet.setTweetIsRetweeted(false);
		tweet.setTweetLatitude(1.23);
		tweet.setTweetLongitude(2.05);
		tweet.setTweetRetweets(2);
		tweet.setTweetRouteId(route.getRouteId());
		tweet.setTweetSentiment("P");
		tweet.setTweetText("Mola mazo");
		tweet.setTweetId(1l);
		tweet.setTweetUserId(1l);
		tweetDao.save(tweet);
		List<Result> sentiments=tweetService.getSentimentsByDate(route.getRouteId(), "P",dateStart,dateEnd);
		assertEquals(sentiments.size(),1);
		assertTrue(Long.compare(sentiments.get(0).getCount(),1l)==0);
		Tweet tweet1=new Tweet();
		tweet1.setRetweetedId(1l);
		tweet1.setTweetCreationDate(date);
		tweet1.setTweetFavourites(1);
		tweet1.setTweetIsFavourite(false);
		tweet1.setTweetIsRetweeted(false);
		tweet1.setTweetLatitude(1.23);
		tweet1.setTweetLongitude(2.05);
		tweet1.setTweetRetweets(2);
		tweet1.setTweetRouteId(route.getRouteId());
		tweet1.setTweetSentiment("P");
		tweet1.setTweetText("Mola pilon");
		tweet1.setTweetId(2l);
		tweet1.setTweetUserId(1l);
		tweetDao.save(tweet1);
		sentiments=tweetService.getSentimentsByDate(route.getRouteId(), "P",dateStart,dateEnd);
		assertEquals(sentiments.size(),1);
		assertTrue(Long.compare(sentiments.get(0).getCount(),2l)==0);
	}
	
	@Test
	public void getSentimentCountTest() {
		Date date=new Date();
		Date dateStart;
		Date dateEnd;
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DAY_OF_MONTH, -1);
	    dateStart=cal.getTime();
	    cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DAY_OF_MONTH, 1);
	    dateEnd=cal.getTime();
		Route route=new Route();
		route.setKilometers(1000l);
		route.setKmlFile("kmlfile");
		route.setNegativeSlope(100l);
		route.setNumberOfHostels(30l);
		route.setPositiveSlope(50l);
		route.setRouteEnd("Camariñas");
		route.setRouteName("Rutita");
		route.setRouteStart("Vimianzo");
		routeDao.save(route);
		Tweet tweet=new Tweet();
		tweet.setRetweetedId(1l);
		tweet.setTweetCreationDate(date);
		tweet.setTweetFavourites(1);
		tweet.setTweetIsFavourite(false);
		tweet.setTweetIsRetweeted(false);
		tweet.setTweetLatitude(1.23);
		tweet.setTweetLongitude(2.05);
		tweet.setTweetRetweets(2);
		tweet.setTweetRouteId(route.getRouteId());
		tweet.setTweetSentiment("P");
		tweet.setTweetText("Mola mazo");
		tweet.setTweetId(1l);
		tweet.setTweetUserId(1l);
		tweetDao.save(tweet);
		List<SentimentsCount> sentiments=tweetService.getSentimentCount(route.getRouteId(), dateStart, dateEnd);
		assertEquals(sentiments.size(),1);
		assertTrue(Long.compare(sentiments.get(0).getCountSentiment(),1l)==0);
		Tweet tweet1=new Tweet();
		tweet1.setRetweetedId(1l);
		tweet1.setTweetCreationDate(date);
		tweet1.setTweetFavourites(1);
		tweet1.setTweetIsFavourite(false);
		tweet1.setTweetIsRetweeted(false);
		tweet1.setTweetLatitude(1.23);
		tweet1.setTweetLongitude(2.05);
		tweet1.setTweetRetweets(2);
		tweet1.setTweetRouteId(route.getRouteId());
		tweet1.setTweetSentiment("P");
		tweet1.setTweetText("Mola pilon");
		tweet1.setTweetId(2l);
		tweet1.setTweetUserId(1l);
		tweetDao.save(tweet1);
		sentiments=tweetService.getSentimentCount(route.getRouteId(), dateStart, dateEnd);
		assertEquals(sentiments.size(),1);
		assertTrue(Long.compare(sentiments.get(0).getCountSentiment(),2l)==0);
		Tweet tweet2=new Tweet();
		tweet2.setRetweetedId(1l);
		tweet2.setTweetCreationDate(date);
		tweet2.setTweetFavourites(1);
		tweet2.setTweetIsFavourite(false);
		tweet2.setTweetIsRetweeted(false);
		tweet2.setTweetLatitude(1.23);
		tweet2.setTweetLongitude(2.05);
		tweet2.setTweetRetweets(2);
		tweet2.setTweetRouteId(route.getRouteId());
		tweet2.setTweetSentiment("N");
		tweet2.setTweetText("Malo");
		tweet2.setTweetId(3l);
		tweet2.setTweetUserId(1l);
		tweetDao.save(tweet2);
		sentiments=tweetService.getSentimentCount(route.getRouteId(), dateStart, dateEnd);
		assertEquals(sentiments.size(),2);
		assertTrue(Long.compare(sentiments.get(0).getCountSentiment(),1l)==0);
		assertTrue(Long.compare(sentiments.get(1).getCountSentiment(),2l)==0);
		Tweet tweet3=new Tweet();
		tweet3.setRetweetedId(1l);
		tweet3.setTweetCreationDate(date);
		tweet3.setTweetFavourites(1);
		tweet3.setTweetIsFavourite(false);
		tweet3.setTweetIsRetweeted(false);
		tweet3.setTweetLatitude(1.23);
		tweet3.setTweetLongitude(2.05);
		tweet3.setTweetRetweets(2);
		tweet3.setTweetRouteId(route.getRouteId());
		tweet3.setTweetSentiment("N");
		tweet3.setTweetText("Muy malo");
		tweet3.setTweetId(4l);
		tweet3.setTweetUserId(1l);
		tweetDao.save(tweet3);
		sentiments=tweetService.getSentimentCount(route.getRouteId(), dateStart, dateEnd);
		assertEquals(sentiments.size(),2);
		assertTrue(Long.compare(sentiments.get(0).getCountSentiment(),2l)==0);
		assertTrue(Long.compare(sentiments.get(1).getCountSentiment(),2l)==0);
	}
	
	@Test
	public void getCoordinatesTest() {
		List<Coordinate> coordinates=tweetService.getCoordinates(System.getProperty("user.dir")
				+ "/src/kml/Tests.kml");
		assertTrue((coordinates.get(0).getLatitude() - Double.parseDouble("2"))==0);
		assertTrue((coordinates.get(0).getLongitude() - Double.parseDouble("1"))==0);
		assertTrue((coordinates.get(1).getLatitude() - Double.parseDouble("4"))==0);
		assertTrue((coordinates.get(1).getLongitude() - Double.parseDouble("3"))==0);
	}
	
	@Test
	public void getTweetsByDateAndSentiment() {
		Date date=new Date();
		Date dateStart;
		Date dateEnd;
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DAY_OF_MONTH, -1);
	    dateStart=cal.getTime();
	    cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DAY_OF_MONTH, 1);
	    dateEnd=cal.getTime();
		Route route=new Route();
		route.setKilometers(1000l);
		route.setKmlFile("kmlfile");
		route.setNegativeSlope(100l);
		route.setNumberOfHostels(30l);
		route.setPositiveSlope(50l);
		route.setRouteEnd("Camariñas");
		route.setRouteName("Rutita");
		route.setRouteStart("Vimianzo");
		routeDao.save(route);
		Tweet tweet=new Tweet();
		tweet.setRetweetedId(1l);
		tweet.setTweetCreationDate(date);
		tweet.setTweetFavourites(1);
		tweet.setTweetIsFavourite(false);
		tweet.setTweetIsRetweeted(false);
		tweet.setTweetLatitude(1.23);
		tweet.setTweetLongitude(2.05);
		tweet.setTweetRetweets(2);
		tweet.setTweetRouteId(route.getRouteId());
		tweet.setTweetSentiment("P");
		tweet.setTweetText("Mola mazo");
		tweet.setTweetId(1l);
		tweet.setTweetUserId(1l);
		tweetDao.save(tweet);
		Tweet tweet1=new Tweet();
		tweet1.setRetweetedId(1l);
		tweet1.setTweetCreationDate(date);
		tweet1.setTweetFavourites(1);
		tweet1.setTweetIsFavourite(false);
		tweet1.setTweetIsRetweeted(false);
		tweet1.setTweetLatitude(1.23);
		tweet1.setTweetLongitude(2.05);
		tweet1.setTweetRetweets(2);
		tweet1.setTweetRouteId(route.getRouteId());
		tweet1.setTweetSentiment("P");
		tweet1.setTweetText("Mola pilon");
		tweet1.setTweetId(2l);
		tweet1.setTweetUserId(1l);
		tweetDao.save(tweet1);
		Tweet tweet2=new Tweet();
		tweet2.setRetweetedId(1l);
		tweet2.setTweetCreationDate(date);
		tweet2.setTweetFavourites(1);
		tweet2.setTweetIsFavourite(false);
		tweet2.setTweetIsRetweeted(false);
		tweet2.setTweetLatitude(1.23);
		tweet2.setTweetLongitude(2.05);
		tweet2.setTweetRetweets(2);
		tweet2.setTweetRouteId(route.getRouteId());
		tweet2.setTweetSentiment("N");
		tweet2.setTweetText("Malo");
		tweet2.setTweetId(3l);
		tweet2.setTweetUserId(1l);
		tweetDao.save(tweet2);
		Tweet tweet3=new Tweet();
		tweet3.setRetweetedId(1l);
		tweet3.setTweetCreationDate(date);
		tweet3.setTweetFavourites(1);
		tweet3.setTweetIsFavourite(false);
		tweet3.setTweetIsRetweeted(false);
		tweet3.setTweetLatitude(1.23);
		tweet3.setTweetLongitude(2.05);
		tweet3.setTweetRetweets(2);
		tweet3.setTweetRouteId(route.getRouteId());
		tweet3.setTweetSentiment("N");
		tweet3.setTweetText("Muy malo");
		tweet3.setTweetId(4l);
		tweet3.setTweetUserId(1l);
		tweetDao.save(tweet3);
		List<Tweet> tweets = tweetService.getTweetsByDateAndSentiment(route.getRouteId(), "P", dateStart, dateEnd);
		assertEquals(tweets.get(0),tweet);
		assertEquals(tweets.get(1),tweet1);
		tweets = tweetService.getTweetsByDateAndSentiment(route.getRouteId(), "N", dateStart, dateEnd);
		assertEquals(tweets.get(0),tweet2);
		assertEquals(tweets.get(1),tweet3);
	}
	
	@Test
	public void getSentimentByDayTest() {
		Date date=new Date();
		Date dateStart;
		Date dateEnd;
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DAY_OF_MONTH, -1);
	    dateStart=cal.getTime();
	    cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DAY_OF_MONTH, 1);
	    dateEnd=cal.getTime();
		Route route=new Route();
		route.setKilometers(1000l);
		route.setKmlFile("kmlfile");
		route.setNegativeSlope(100l);
		route.setNumberOfHostels(30l);
		route.setPositiveSlope(50l);
		route.setRouteEnd("Camariñas");
		route.setRouteName("Rutita");
		route.setRouteStart("Vimianzo");
		routeDao.save(route);
		Tweet tweet=new Tweet();
		tweet.setRetweetedId(1l);
		tweet.setTweetCreationDate(date);
		tweet.setTweetFavourites(1);
		tweet.setTweetIsFavourite(false);
		tweet.setTweetIsRetweeted(false);
		tweet.setTweetLatitude(1.23);
		tweet.setTweetLongitude(2.05);
		tweet.setTweetRetweets(2);
		tweet.setTweetRouteId(route.getRouteId());
		tweet.setTweetSentiment("P");
		tweet.setTweetText("Mola mazo");
		tweet.setTweetId(1l);
		tweet.setTweetUserId(1l);
		tweetDao.save(tweet);
		Tweet tweet1=new Tweet();
		tweet1.setRetweetedId(1l);
		tweet1.setTweetCreationDate(date);
		tweet1.setTweetFavourites(1);
		tweet1.setTweetIsFavourite(false);
		tweet1.setTweetIsRetweeted(false);
		tweet1.setTweetLatitude(1.23);
		tweet1.setTweetLongitude(2.05);
		tweet1.setTweetRetweets(2);
		tweet1.setTweetRouteId(route.getRouteId());
		tweet1.setTweetSentiment("P");
		tweet1.setTweetText("Mola pilon");
		tweet1.setTweetId(2l);
		tweet1.setTweetUserId(1l);
		tweetDao.save(tweet1);
		Tweet tweet2=new Tweet();
		tweet2.setRetweetedId(1l);
		tweet2.setTweetCreationDate(date);
		tweet2.setTweetFavourites(1);
		tweet2.setTweetIsFavourite(false);
		tweet2.setTweetIsRetweeted(false);
		tweet2.setTweetLatitude(1.23);
		tweet2.setTweetLongitude(2.05);
		tweet2.setTweetRetweets(2);
		tweet2.setTweetRouteId(route.getRouteId());
		tweet2.setTweetSentiment("N");
		tweet2.setTweetText("Malo");
		tweet2.setTweetId(3l);
		tweet2.setTweetUserId(1l);
		tweetDao.save(tweet2);
		Tweet tweet3=new Tweet();
		tweet3.setRetweetedId(1l);
		tweet3.setTweetCreationDate(date);
		tweet3.setTweetFavourites(1);
		tweet3.setTweetIsFavourite(false);
		tweet3.setTweetIsRetweeted(false);
		tweet3.setTweetLatitude(1.23);
		tweet3.setTweetLongitude(2.05);
		tweet3.setTweetRetweets(2);
		tweet3.setTweetRouteId(route.getRouteId());
		tweet3.setTweetSentiment("N");
		tweet3.setTweetText("Muy malo");
		tweet3.setTweetId(4l);
		tweet3.setTweetUserId(1l);
		tweetDao.save(tweet3);
		List<Result> sentiments = tweetService.getSentimentsByDay(route.getRouteId(), "P", dateStart, dateEnd);
		assertTrue(sentiments.size()==1);
		assertTrue(sentiments.get(0).getCount()==2);
		sentiments = tweetService.getSentimentsByDay(route.getRouteId(), "N", dateStart, dateEnd);
		assertTrue(sentiments.size()==1);
		assertTrue(sentiments.get(0).getCount()==2);
	}

}
