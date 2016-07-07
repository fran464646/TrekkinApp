package tfg.model.tweet;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.BatchSize;

@Entity
@BatchSize(size=10)
public class Tweet {

	private Long tweetId;
	private String tweetText;
	private Date tweetCreationDate;
	private Long retweetedId;
	private int tweetFavourites;
	private Double tweetLatitude;
	private Double tweetLongitude;
	private int tweetRetweets;
	private Long tweetUserId;
	private Boolean tweetIsFavourite;
	private Boolean tweetIsRetweeted;
	private Long tweetRouteId;
	private String tweetSentiment;
	
	public Tweet(){
		
	}

	public Tweet(Long tweetId, String tweetText, Date tweetCreationDate,
			Long retweetedId, int tweetFavourites, Double tweetLatitude,
			Double tweetLongitude, int tweetRetweets, Long tweetUserId,
			Boolean tweetIsFavourite, Boolean tweetIsRetweeted,
			Long tweetRouteId, String tweetSentiment) {
		super();
		this.tweetId = tweetId;
		this.tweetText = tweetText;
		this.tweetCreationDate = tweetCreationDate;
		this.retweetedId = retweetedId;
		this.tweetFavourites = tweetFavourites;
		this.tweetLatitude = tweetLatitude;
		this.tweetLongitude = tweetLongitude;
		this.tweetRetweets = tweetRetweets;
		this.tweetUserId = tweetUserId;
		this.tweetIsFavourite = tweetIsFavourite;
		this.tweetIsRetweeted = tweetIsRetweeted;
		this.tweetRouteId = tweetRouteId;
		this.tweetSentiment = tweetSentiment;
	}

	@Column(name = "tweetSentiment")
	public String getTweetSentiment() {
		return tweetSentiment;
	}

	public void setTweetSentiment(String tweetSentiment) {
		this.tweetSentiment = tweetSentiment;
	}
	
	
	@Column(name = "tweetId")
	@Id
	public Long getTweetId() {
		return tweetId;
	}
	

	public void setTweetId(Long tweetId) {
		this.tweetId = tweetId;
	}
	
	@Column(name = "tweetText")
	public String getTweetText() {
		return tweetText;
	}
	public void setTweetText(String tweetText) {
		this.tweetText = tweetText;
	}
	
	@Column(name = "tweetCreationDate")
	public Date getTweetCreationDate() {
		return tweetCreationDate;
	}
	public void setTweetCreationDate(Date tweetCreationDate) {
		this.tweetCreationDate = tweetCreationDate;
	}
	
	@Column(name = "retweetedId")
	public Long getRetweetedId() {
		return retweetedId;
	}
	public void setRetweetedId(Long retweetedId) {
		this.retweetedId = retweetedId;
	}
	
	@Column(name = "tweetFavourites")
	public int getTweetFavourites() {
		return tweetFavourites;
	}
	public void setTweetFavourites(int tweetFavourites) {
		this.tweetFavourites = tweetFavourites;
	}
	
	@Column(name = "tweetLatitude")
	public Double getTweetLatitude() {
		return tweetLatitude;
	}
	public void setTweetLatitude(Double tweetLatitude) {
		this.tweetLatitude = tweetLatitude;
	}
	
	@Column(name = "tweetLongitude")
	public Double getTweetLongitude() {
		return tweetLongitude;
	}
	public void setTweetLongitude(Double tweetLongitude) {
		this.tweetLongitude = tweetLongitude;
	}
	
	@Column(name = "tweetRetweets")
	public int getTweetRetweets() {
		return tweetRetweets;
	}
	public void setTweetRetweets(int tweetRetweets) {
		this.tweetRetweets = tweetRetweets;
	}
	
	@Column(name = "tweetUserId")
	public Long getTweetUserId() {
		return tweetUserId;
	}
	public void setTweetUserId(Long tweetUserId) {
		this.tweetUserId = tweetUserId;
	}
	
	@Column(name = "tweetIsFavourite")
	public Boolean getTweetIsFavourite() {
		return tweetIsFavourite;
	}
	public void setTweetIsFavourite(Boolean tweetIsFavourite) {
		this.tweetIsFavourite = tweetIsFavourite;
	}
	
	@Column(name = "tweetIsRetweeted")
	public Boolean getTweetIsRetweeted() {
		return tweetIsRetweeted;
	}
	public void setTweetIsRetweeted(Boolean tweetIsRetweeted) {
		this.tweetIsRetweeted = tweetIsRetweeted;
	}
	
	@Column(name = "tweetRouteId")
	public Long getTweetRouteId() {
		return tweetRouteId;
	}
	public void setTweetRouteId(Long tweetRouteId) {
		this.tweetRouteId = tweetRouteId;
	}
	
	
	
}
