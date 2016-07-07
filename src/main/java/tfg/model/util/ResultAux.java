package tfg.model.util;

import java.util.Date;

public class ResultAux {
	
	private Long count;
	private Date date;
	
	public ResultAux() {
		super();
	}
	
	public ResultAux(Long count, Date date) {
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
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
