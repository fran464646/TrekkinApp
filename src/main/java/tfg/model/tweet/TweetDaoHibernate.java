package tfg.model.tweet;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import tfg.model.util.GenericDaoHibernate;
import tfg.model.util.Result;
import tfg.model.util.SentimentsCount;

@Repository("tweetDao")
public class TweetDaoHibernate extends
		GenericDaoHibernate<Tweet, Long> implements TweetDao {
	
	@Override
	public List<Tweet> findByRoute(Long routeId) {

		Query tmpQuery = getSession().createQuery(
    			"SELECT u FROM Tweet u WHERE u.tweetRouteId = :routeId")
    			.setParameter("routeId", routeId);
    	return tmpQuery.list();
	}
	
	@Override
	public boolean containsIllegalCharacters(String displayName)
	{
	    final int nameLength = displayName.length();

	    for (int i = 0; i < nameLength; i++)
	    {
	        final char hs = displayName.charAt(i);

	        if (0xd800 <= hs && hs <= 0xdbff)
	        {
	            final char ls = displayName.charAt(i + 1);
	            final int uc = ((hs - 0xd800) * 0x400) + (ls - 0xdc00) + 0x10000;

	            if (0x1d000 <= uc && uc <= 0x1f77f)
	            {
	                return true;
	            }
	        }
	        else if (Character.isHighSurrogate(hs))
	        {
	            final char ls = displayName.charAt(i + 1);

	            if (ls == 0x20e3)
	            {
	                return true;
	            }
	        }
	        else
	        {
	            // non surrogate
	            if (0x2100 <= hs && hs <= 0x27ff)
	            {
	                return true;
	            }
	            else if (0x2B05 <= hs && hs <= 0x2b07)
	            {
	                return true;
	            }
	            else if (0x2934 <= hs && hs <= 0x2935)
	            {
	                return true;
	            }
	            else if (0x3297 <= hs && hs <= 0x3299)
	            {
	                return true;
	            }
	            else if (hs == 0xa9 || hs == 0xae || hs == 0x303d || hs == 0x3030 || hs == 0x2b55 || hs == 0x2b1c || hs == 0x2b1b || hs == 0x2b50)
	            {
	                return true;
	            }
	        }
	    }

	    return false;
	}

	@Override
	public List<SentimentsCount> getSentimentCount(Long routeId, Date dateStart, Date dateEnd) {
		SentimentsCount sentimentsCount;
		Query tmpQuery;
		if (dateStart==null && dateEnd==null){
			tmpQuery = getSession().createQuery(
    			"SELECT u.tweetSentiment,count(*) FROM Tweet u WHERE u.tweetRouteId = :routeId GROUP BY u.tweetSentiment")
    			.setParameter("routeId", routeId);
		}else{
			if (dateStart==null){
				tmpQuery = getSession().createQuery(
		    			"SELECT u.tweetSentiment,count(*) FROM Tweet u WHERE u.tweetRouteId = :routeId AND u.tweetCreationDate<= :dateEnd GROUP BY u.tweetSentiment")
		    			.setParameter("routeId", routeId).setParameter("dateEnd", dateEnd);
			}else{
				if (dateEnd==null){
					tmpQuery = getSession().createQuery(
			    			"SELECT u.tweetSentiment,count(*) FROM Tweet u WHERE u.tweetRouteId = :routeId AND u.tweetCreationDate>= :dateStart GROUP BY u.tweetSentiment")
			    			.setParameter("routeId", routeId).setParameter("dateStart", dateStart);
				}else{
					tmpQuery = getSession().createQuery(
			    			"SELECT u.tweetSentiment,count(*) FROM Tweet u WHERE u.tweetRouteId = :routeId AND u.tweetCreationDate<= :dateEnd AND u.tweetCreationDate>= :dateStart GROUP BY u.tweetSentiment")
			    			.setParameter("routeId", routeId).setParameter("dateEnd", dateEnd).setParameter("dateStart", dateStart);
				}
			}
		}
		
		List<Object[]> list = tmpQuery.list();
		List<SentimentsCount> finalList = new ArrayList<SentimentsCount>();
		for  (Object[] object:list){
			if (object[0]!=null){
				String sentiment=object[0].toString();
				Long count=Long.decode(object[1].toString());
				sentimentsCount=new SentimentsCount(sentiment,count);
				finalList.add(sentimentsCount);
			}
		}
    	return finalList;		
	}

	@Override
	public List<Result> getSentimentsByDate(Long routeId, String sentiment, Date dateStart, Date dateEnd) {
		Result result;
		Query tmpQuery;
		if (dateStart==null && dateEnd==null){
			tmpQuery = getSession().createQuery(
	    			"SELECT u.tweetCreationDate,count(*) FROM Tweet u WHERE u.tweetRouteId = :routeId AND u.tweetSentiment = :sentiment GROUP BY u.tweetCreationDate")
	    			.setParameter("routeId", routeId).setParameter("sentiment", sentiment);
		}else{
			if (dateStart==null){
				tmpQuery = getSession().createQuery(
		    			"SELECT u.tweetCreationDate,count(*) FROM Tweet u WHERE u.tweetRouteId = :routeId AND u.tweetSentiment = :sentiment AND u.tweetCreationDate<= :dateEnd GROUP BY u.tweetCreationDate")
		    			.setParameter("routeId", routeId).setParameter("sentiment", sentiment).setParameter("dateEnd", dateEnd);
			}else{
				if (dateEnd==null){
					tmpQuery = getSession().createQuery(
			    			"SELECT u.tweetCreationDate,count(*) FROM Tweet u WHERE u.tweetRouteId = :routeId AND u.tweetSentiment = :sentiment AND u.tweetCreationDate>= :dateStart GROUP BY u.tweetCreationDate")
			    			.setParameter("routeId", routeId).setParameter("sentiment", sentiment).setParameter("dateStart", dateStart);
				}else{
					tmpQuery = getSession().createQuery(
			    			"SELECT u.tweetCreationDate,count(*) FROM Tweet u WHERE u.tweetRouteId = :routeId AND u.tweetSentiment = :sentiment AND u.tweetCreationDate>= :dateStart AND u.tweetCreationDate<= :dateEnd GROUP BY u.tweetCreationDate")
			    			.setParameter("routeId", routeId).setParameter("sentiment", sentiment).setParameter("dateEnd", dateEnd).setParameter("dateStart", dateStart);
				}
			}
		}
		
		List<Object[]> list = tmpQuery.list();
		List<Result> finalList = new ArrayList<Result>();
		for  (Object[] object:list){
			String creationDate=object[0].toString();
			Long count=Long.decode(object[1].toString());
			result=new Result(count,creationDate);
			finalList.add(result);
		}
    	return finalList;		
	}
	
	@Override
	public List<Result> getSentimentsByDay(Long routeId, String sentiment, Date dateStart, Date dateEnd) {
		Result result;
		Query tmpQuery;
		if (dateStart==null && dateEnd==null){
			tmpQuery = getSession().createQuery(
	    			"SELECT u.tweetCreationDate,count(*) FROM Tweet u WHERE u.tweetRouteId = :routeId AND u.tweetSentiment = :sentiment GROUP BY YEAR(u.tweetCreationDate), MONTH(u.tweetCreationDate), DAY(u.tweetCreationDate)")
	    			.setParameter("routeId", routeId).setParameter("sentiment", sentiment);
		}else{
			if (dateStart==null){
				tmpQuery = getSession().createQuery(
		    			"SELECT u.tweetCreationDate,count(*) FROM Tweet u WHERE u.tweetRouteId = :routeId AND u.tweetSentiment = :sentiment AND u.tweetCreationDate<= :dateEnd GROUP BY YEAR(u.tweetCreationDate), MONTH(u.tweetCreationDate), DAY(u.tweetCreationDate)")
		    			.setParameter("routeId", routeId).setParameter("sentiment", sentiment).setParameter("dateEnd", dateEnd);
			}else{
				if (dateEnd==null){
					tmpQuery = getSession().createQuery(
			    			"SELECT u.tweetCreationDate,count(*) FROM Tweet u WHERE u.tweetRouteId = :routeId AND u.tweetSentiment = :sentiment AND u.tweetCreationDate>= :dateStart GROUP BY YEAR(u.tweetCreationDate), MONTH(u.tweetCreationDate), DAY(u.tweetCreationDate)")
			    			.setParameter("routeId", routeId).setParameter("sentiment", sentiment).setParameter("dateStart", dateStart);
				}else{
					tmpQuery = getSession().createQuery(
			    			"SELECT u.tweetCreationDate,count(*) FROM Tweet u WHERE u.tweetRouteId = :routeId AND u.tweetSentiment = :sentiment AND u.tweetCreationDate>= :dateStart AND u.tweetCreationDate<= :dateEnd GROUP BY YEAR(u.tweetCreationDate), MONTH(u.tweetCreationDate), DAY(u.tweetCreationDate)")
			    			.setParameter("routeId", routeId).setParameter("sentiment", sentiment).setParameter("dateEnd", dateEnd).setParameter("dateStart", dateStart);
				}
			}
		}
		
		List<Object[]> list = tmpQuery.list();
		List<Result> finalList = new ArrayList<Result>();
		for  (Object[] object:list){
			String creationDate=object[0].toString();
			Long count=Long.decode(object[1].toString());
			result=new Result(count,creationDate);
			finalList.add(result);
		}
    	return finalList;		
	}
	
	@Override
	public List<Tweet> getTweetsByDateAndSentiment(Long routeId, String sentiment, Date dateStart, Date dateEnd) {
		Query tmpQuery;
		if (dateStart==null && dateEnd==null){
			tmpQuery = getSession().createQuery(
	    			"SELECT u FROM Tweet u WHERE u.tweetRouteId = :routeId AND u.tweetSentiment = :sentiment")
	    			.setParameter("routeId", routeId).setParameter("sentiment", sentiment);
		}else{
			if (dateStart==null){
				tmpQuery = getSession().createQuery(
		    			"SELECT u FROM Tweet u WHERE u.tweetRouteId = :routeId AND u.tweetSentiment = :sentiment AND u.tweetCreationDate<= :dateEnd GROUP BY")
		    			.setParameter("routeId", routeId).setParameter("sentiment", sentiment).setParameter("dateEnd", dateEnd);
			}else{
				if (dateEnd==null){
					tmpQuery = getSession().createQuery(
			    			"SELECT u FROM Tweet u WHERE u.tweetRouteId = :routeId AND u.tweetSentiment = :sentiment AND u.tweetCreationDate>= :dateStart GROUP BY")
			    			.setParameter("routeId", routeId).setParameter("sentiment", sentiment).setParameter("dateStart", dateStart);
				}else{
					tmpQuery = getSession().createQuery(
			    			"SELECT u FROM Tweet u WHERE u.tweetRouteId = :routeId AND u.tweetSentiment = :sentiment AND u.tweetCreationDate>= :dateStart AND u.tweetCreationDate<= :dateEnd").
			    			setParameter("routeId", routeId).setParameter("sentiment", sentiment).setParameter("dateEnd", dateEnd).setParameter("dateStart", dateStart);
				}
			}
		}
		
		return tmpQuery.list();	
	}

	@Override
	public List<Tweet> findByRoute(Long routeId, int startIndex, int count, Date dateStart, Date dateEnd) {
		Query tmpQuery;
		if (dateStart==null && dateEnd==null){
			tmpQuery = getSession().createQuery(
	    			"SELECT u FROM Tweet u WHERE u.tweetRouteId = :routeId ORDER BY u.tweetCreationDate DESC")
	    			.setParameter("routeId", routeId).setFirstResult(startIndex).setMaxResults(count);
		}else{
			if (dateStart==null){
				tmpQuery = getSession().createQuery(
		    			"SELECT u FROM Tweet u WHERE u.tweetRouteId = :routeId AND u.tweetCreationDate<= :dateEnd ORDER BY u.tweetCreationDate DESC")
		    			.setParameter("routeId", routeId).setParameter("dateEnd", dateEnd).setFirstResult(startIndex).setMaxResults(count);
			}else{
				if (dateEnd==null){
					tmpQuery = getSession().createQuery(
			    			"SELECT u FROM Tweet u WHERE u.tweetRouteId = :routeId AND u.tweetCreationDate>= :dateStart ORDER BY u.tweetCreationDate DESC")
			    			.setParameter("routeId", routeId).setParameter("dateStart", dateStart).setFirstResult(startIndex).setMaxResults(count);
				}else{
					tmpQuery = getSession().createQuery(
			    			"SELECT u FROM Tweet u WHERE u.tweetRouteId = :routeId AND u.tweetCreationDate>= :dateStart AND u.tweetCreationDate<= :dateEnd ORDER BY u.tweetCreationDate DESC")
			    			.setParameter("routeId", routeId).setParameter("dateStart", dateStart).setParameter("dateEnd", dateEnd).setFirstResult(startIndex).setMaxResults(count);
				}
			}
		}
    	return tmpQuery.list();
	}
}