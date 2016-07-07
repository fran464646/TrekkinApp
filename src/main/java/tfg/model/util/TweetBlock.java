package tfg.model.util;

import java.util.ArrayList;
import java.util.List;

import tfg.model.tweet.Tweet;

public class TweetBlock {

	private boolean existsMoreTweets;
	
	private List<Tweet> tweets;
	
	public TweetBlock(boolean existsMoreTweets, List<Tweet> tweets) {
		super();
		this.existsMoreTweets = existsMoreTweets;
		this.tweets = tweets;
	}
	
	public TweetBlock() {
	}

	public boolean isExistsMoreTweets() {
		return existsMoreTweets;
	}

	public void setExistsMoreTweets(boolean existsMoreTweets) {
		this.existsMoreTweets = existsMoreTweets;
	}

	public List<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}
	
	
}
