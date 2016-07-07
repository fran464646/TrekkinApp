package tfg.model.util;

public class SentimentsCount {
	
	private String sentiment;
	private Long countSentiment;
	
	public SentimentsCount(String sentiment, Long countSentiment) {
		super();
		this.sentiment = sentiment;
		this.countSentiment = countSentiment;
	}
	public String getSentiment() {
		return sentiment;
	}
	public void setSentiment(String sentiment) {
		this.sentiment = sentiment;
	}
	public Long getCountSentiment() {
		return countSentiment;
	}
	public void setCountSentiment(Long countSentiment) {
		this.countSentiment = countSentiment;
	}
	
	
}
