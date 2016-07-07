package tfg.web.pages.search;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.tapestry5.ComponentResources;
import org.apache.tapestry5.Link;
import org.apache.tapestry5.StreamResponse;
import org.apache.tapestry5.annotations.Persist;
import org.apache.tapestry5.annotations.Property;
import org.apache.tapestry5.ioc.annotations.Inject;
import org.apache.tapestry5.ioc.services.TypeCoercer;
import org.apache.tapestry5.services.Response;
import org.apache.tapestry5.services.javascript.JavaScriptSupport;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.DefaultKeyedValues;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.RectangleInsets;

import tfg.model.routeservice.RouteService;
import tfg.model.tweet.Tweet;
import tfg.model.tweetservice.TweetService;
import tfg.model.util.Miopia;
import tfg.model.util.Result;
import tfg.model.util.ResultAux;
import tfg.model.util.SentimentsCount;

public class RouteStats {

	@Persist
	@Property
	private Date dateStart;
	
	private String locationsP="";
	
	private String locationsN="";
	
	private boolean exists;
	
	@Persist
	@Property
	private Date dateEnd;
	
	@Inject
	private ComponentResources _resources;

	@Inject
	private TypeCoercer _coercer;

	@Inject
	private TweetService tweetService;

	private List<Tweet> tweets;

	private Long routeId;

	@Inject
	private JavaScriptSupport javaScriptSupport;

	void afterRender() {
		javaScriptSupport.require("bootstrap/tab");
	}

	Object[] onPassivate() {
		return new Object[] { routeId };
	}

	void onActivate(Long routeId) {
		this.routeId = routeId;
		if (dateStart!=null && dateEnd!=null){
			if (dateEnd.before(dateStart))
				exists=false;
			else
				exists=true;
		}else{
			List<SentimentsCount> count = tweetService.getSentimentCount(routeId,dateStart,dateEnd);
			if (count==null || count.isEmpty())
				exists=false;
			else
				exists=true;
		}
		List<Tweet> tweets = tweetService.getTweetsByDateAndSentiment(routeId, "P", dateStart, dateEnd);
		locationsP="[";
		for (Tweet tweet:tweets){
			locationsP=locationsP.concat("["+tweet.getTweetLatitude()+","+tweet.getTweetLongitude()+"],");
		}
		locationsP=locationsP.substring(0, locationsP.length()-1);
		locationsP=locationsP.concat("]");
		tweets = tweetService.getTweetsByDateAndSentiment(routeId, "N", dateStart, dateEnd);
		locationsN="[";
		for (Tweet tweet:tweets){
			locationsN=locationsN.concat("["+tweet.getTweetLatitude()+","+tweet.getTweetLongitude()+"],");
		}
		locationsN=locationsN.substring(0, locationsN.length()-1);
		locationsN=locationsN.concat("]");
	}

	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	public Link getChart3() {
		Long countN=0l;
		Long countP=0l;
		if (dateEnd!=null){
			Calendar cal = Calendar.getInstance();
	    	cal.setTime(dateEnd);
	    	cal.add(Calendar.HOUR_OF_DAY, 24);
	    	dateEnd=cal.getTime();
		}
		List<SentimentsCount> count = tweetService.getSentimentCount(routeId,dateStart,dateEnd);
		if (dateEnd!=null){
			Calendar cal = Calendar.getInstance();
	    	cal.setTime(dateEnd);
	    	cal.add(Calendar.HOUR_OF_DAY, -24);
	    	dateEnd=cal.getTime();
		}
		for (SentimentsCount sentimentCount: count){
			if (sentimentCount.getSentiment().equals("N"))
				countN=sentimentCount.getCountSentiment();
			if (sentimentCount.getSentiment().equals("P"))
				countP=sentimentCount.getCountSentiment();
		}
			return _resources.createActionLink("chart", false, new Object[] {
				"1140", "370", "P",
				countP.toString(),
				"N",
				countN.toString() });
	}

	public StreamResponse onChart(final int width, final int height,
			Object... rest) {
		DefaultKeyedValues values = new DefaultKeyedValues();
		for (int i = 3; i < rest.length; i += 2) {
			values.addValue(rest[i - 1].toString(),
					_coercer.coerce(rest[i], Number.class));
		}
		PieDataset pieDataset = new DefaultPieDataset(values);

		PiePlot3D plot = new PiePlot3D(pieDataset);
		plot.setBackgroundPaint(Color.WHITE);
        plot.setBackgroundImageAlpha(0.0f);
		plot.setForegroundAlpha(0.5f);
		plot.setDepthFactor(0.1);
		plot.setCircular(true);

		final JFreeChart chart = new JFreeChart(plot);
		chart.setBackgroundPaint(Color.WHITE);

		return new StreamResponse() {
			public String getContentType() {
				return "image/jpeg";
			}

			public InputStream getStream() throws IOException {
				BufferedImage image = chart.createBufferedImage(width, height);
				ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
				ChartUtilities.writeBufferedImageAsJPEG(byteArray, image);
				return new ByteArrayInputStream(byteArray.toByteArray());
			}

			public void prepareResponse(Response response) {
			}
		};
	}

	public Link getChart4() {

		return _resources.createActionLink("chart4", false, new Object[] {
				"1140", "370" });
	}

	public StreamResponse onChart4(final int width, final int height,
			Object... rest) throws ParseException {
		List<Result> values = new ArrayList<Result>();
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		String date;
		List<Result> dates=new ArrayList<Result>();
		boolean contains;
		int i=0;
		DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
		DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd"); 
		if (dateEnd!=null){
			Calendar cal = Calendar.getInstance();
	    	cal.setTime(dateEnd);
	    	cal.add(Calendar.HOUR_OF_DAY, 24);
	    	dateEnd=cal.getTime();
		}
		values = tweetService.getSentimentsByDate(routeId, "P",dateStart,dateEnd);
		if (dateEnd!=null){
			Calendar cal = Calendar.getInstance();
	    	cal.setTime(dateEnd);
	    	cal.add(Calendar.HOUR_OF_DAY, -24);
	    	dateEnd=cal.getTime();
		}
		TimeSeries s1 = new TimeSeries("P");
		for (Result value : values) {
			contains=false;
			date=value.getDate().substring(0, 10);
			i=0;
			for (Result result:dates){
				if (result.getDate().equals(date.toString())){
					contains=true;
					Result newDate=result;
					newDate.setCount(newDate.getCount()+1);
					dates.set(i, newDate);
				}
				i++;
			}
			if (!contains)
				dates.add(new Result(1l,date));
			
		}
		for (Result dateBD:dates){
				Date data=formatter1.parse(dateBD.getDate());
				s1.add(new Day(data), dateBD.getCount());

		}
		dataset.addSeries(s1);
		dates.clear();
		if (dateEnd!=null){
			Calendar cal = Calendar.getInstance();
	    	cal.setTime(dateEnd);
	    	cal.add(Calendar.HOUR_OF_DAY, 24);
	    	dateEnd=cal.getTime();
		}
		values = tweetService.getSentimentsByDate(routeId, "N", dateStart, dateEnd);
		if (dateEnd!=null){
			Calendar cal = Calendar.getInstance();
	    	cal.setTime(dateEnd);
	    	cal.add(Calendar.HOUR_OF_DAY, -24);
	    	dateEnd=cal.getTime();
		}
		s1 = new TimeSeries("N");
		for (Result value : values) {
			contains=false;
			contains=false;
			date=value.getDate().substring(0, 10);
			i=0;
			for (Result result:dates){
				if (result.getDate().equals(date.toString())){
					contains=true;
					Result newDate=dates.get(i);
					newDate.setCount(newDate.getCount()+1);
					dates.set(i, newDate);
				}
				i++;
			}
			if (!contains)
				dates.add(new Result(1l,date.toString()));
			
			
		}
		for (Result dateBD:dates){
			Date data=formatter1.parse(dateBD.getDate());
			s1.add(new Day(data), dateBD.getCount());

		}
		dataset.addSeries(s1);

		final JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"", // title
				"Date", // x-axis label
				"Number of errors", // y-axis label
				dataset, // data
				true, // create legend?
				true, // generate tooltips?
				false // generate URLs?
				);

		chart.setBackgroundPaint(Color.white);

		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
			renderer.setDrawSeriesLineAsPath(true);
		}

		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("dd-MM-yyyy"));

		return new StreamResponse() {
			public String getContentType() {
				return "image/jpeg";
			}

			public InputStream getStream() throws IOException {
				BufferedImage image = chart.createBufferedImage(width, height);
				ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
				ChartUtilities.writeBufferedImageAsJPEG(byteArray, image);
				return new ByteArrayInputStream(byteArray.toByteArray());
			}

			public void prepareResponse(Response response) {
			}
		};
	}

	public Link getChart5() {

		return _resources.createActionLink("chart5", false, new Object[] {
				"1140", "370" });
	}

	public StreamResponse onChart5(final int width, final int height,
			Object... rest) {
		Long countN=0l;
		Long countP=0l;
		if (dateEnd!=null){
			Calendar cal = Calendar.getInstance();
	    	cal.setTime(dateEnd);
	    	cal.add(Calendar.HOUR_OF_DAY, 24);
	    	dateEnd=cal.getTime();
		}
		List<SentimentsCount> count = tweetService.getSentimentCount(routeId, dateStart, dateEnd);
		if (dateEnd!=null){
			Calendar cal = Calendar.getInstance();
	    	cal.setTime(dateEnd);
	    	cal.add(Calendar.HOUR_OF_DAY, -24);
	    	dateEnd=cal.getTime();
		}
		for (SentimentsCount sentimentCount: count){
			if (sentimentCount.getSentiment().equals("N"))
				countN=sentimentCount.getCountSentiment();
			if (sentimentCount.getSentiment().equals("P"))
				countP=sentimentCount.getCountSentiment();
		}
		final DefaultCategoryDataset dataset = new DefaultCategoryDataset();

		dataset.addValue(countP,"P", "");
		dataset.addValue(countN, "N", "");
		final JFreeChart chart = ChartFactory.createBarChart("", "", "", dataset,
				PlotOrientation.VERTICAL, true, true, false);
		chart.setBackgroundPaint(Color.WHITE);

		return new StreamResponse() {
			public String getContentType() {
				return "image/jpeg";
			}

			public InputStream getStream() throws IOException {
				BufferedImage image = chart.createBufferedImage(width, height);
				ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
				ChartUtilities.writeBufferedImageAsJPEG(byteArray, image);
				return new ByteArrayInputStream(byteArray.toByteArray());
			}

			public void prepareResponse(Response response) {
			}
		};
	}
	
	public Link getChart7() {

		return _resources.createActionLink("chart7", false, new Object[] {
				"1140", "370" });
	}

	public StreamResponse onChart7(final int width, final int height,
			Object... rest) throws ParseException {
		List<Result> valuesP = new ArrayList<Result>();
		List<Result> valuesN = new ArrayList<Result>();
		List<Result> valuesB = new ArrayList<Result>();
		TimeSeriesCollection dataset = new TimeSeriesCollection();
		String date;
		List<Result> dates=new ArrayList<Result>();
		boolean contains;
		int i=0;
		DateFormat formatter = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy");
		DateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd"); 
		if (dateEnd!=null){
			Calendar cal = Calendar.getInstance();
	    	cal.setTime(dateEnd);
	    	cal.add(Calendar.HOUR_OF_DAY, 24);
	    	dateEnd=cal.getTime();
		}
		valuesP = tweetService.getSentimentsByDay(routeId, "P",dateStart,dateEnd);
		valuesN = tweetService.getSentimentsByDay(routeId, "N",dateStart,dateEnd);
		List<ResultAux> finalValues=new ArrayList<ResultAux>();
		boolean exists=false;
		for (Result result:valuesP){
			Date fecha=formatter1.parse(result.getDate());
			exists=false;
			for(Result resultN:valuesN){
				Date fecha1=formatter1.parse(resultN.getDate());
				if (fecha1.equals(fecha)){
					finalValues.add(new ResultAux(result.getCount()-resultN.getCount(),fecha));
					exists=true;
				}
			}
			if (exists==false){
				finalValues.add(new ResultAux(result.getCount(),fecha));
			}
		}
		for (Result result:valuesN){
			Date fecha=formatter1.parse(result.getDate());
			exists=false;
			for(ResultAux resultN:finalValues){
				if (resultN.getDate().equals(fecha)){
					exists=true;
				}
			}
			if (exists==false){
				finalValues.add(new ResultAux(0-result.getCount(),fecha));
			}
		}
		TimeSeries s1 = new TimeSeries("B");
		for (ResultAux dateBD:finalValues){
				s1.add(new Day(dateBD.getDate()), dateBD.getCount());

		}
		dataset.addSeries(s1);

		final JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"", // title
				"Date", // x-axis label
				"Number of errors", // y-axis label
				dataset, // data
				true, // create legend?
				true, // generate tooltips?
				false // generate URLs?
				);

		chart.setBackgroundPaint(Color.white);

		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.white);
		plot.setDomainGridlinePaint(Color.white);
		plot.setRangeGridlinePaint(Color.white);
		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);

		XYItemRenderer r = plot.getRenderer();
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
			renderer.setDrawSeriesLineAsPath(true);
		}

		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setDateFormatOverride(new SimpleDateFormat("dd-MM-yyyy"));

		return new StreamResponse() {
			public String getContentType() {
				return "image/jpeg";
			}

			public InputStream getStream() throws IOException {
				BufferedImage image = chart.createBufferedImage(width, height);
				ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
				ChartUtilities.writeBufferedImageAsJPEG(byteArray, image);
				return new ByteArrayInputStream(byteArray.toByteArray());
			}

			public void prepareResponse(Response response) {
			}
		};
	}

	public boolean isExists() {
		return exists;
	}

	public void setExists(boolean exists) {
		this.exists = exists;
	}

	public String getLocationsP() {
		return locationsP;
	}

	public void setLocationsP(String locationsP) {
		this.locationsP = locationsP;
	}

	public String getLocationsN() {
		return locationsN;
	}

	public void setLocationsN(String locationsN) {
		this.locationsN = locationsN;
	}
}
