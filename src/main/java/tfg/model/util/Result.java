package tfg.model.util;

public class Result {
	
	private Long count;
	private String date;
	
	public Result() {
		super();
	}
	
	public Result(Long count, String date) {
		super();
		this.count = count;
		this.date = date;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
	
}
