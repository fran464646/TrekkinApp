package tfg.model.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.TimerTask;

import org.apache.tapestry5.ioc.annotations.Inject;
import org.springframework.beans.factory.annotation.Autowired;

import tfg.model.alertservice.AlertService;
import tfg.model.route.Route;
import tfg.model.routeservice.RouteService;
import tfg.model.stat.Stat;
import tfg.model.statservice.StatService;
import tfg.model.tweet.Tweet;
import tfg.model.tweet.TweetDao;
import tfg.model.tweetservice.TweetService;

public class Miopia extends TimerTask {

	private TweetService tweetService;
	
	public Miopia() {
		super();
	}

	private AlertService alertService;

	private Long routeId;

	private Route route;
	
	private RouteService routeService;
	
	private StatService statService;

	private List<Long> ids = new ArrayList<Long>();

	public Long getRouteId() {
		return routeId;
	}

	public void setRouteId(Long routeId) {
		this.routeId = routeId;
	}

	public Miopia(TweetService tweetService,RouteService routeService,StatService statService,AlertService alertService) {
		super();
		this.tweetService = tweetService;
		this.routeService = routeService;
		this.statService = statService;
		this.alertService = alertService;
	}

	@Override
	public void run() {
		Date date=new Date();
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DAY_OF_MONTH, -7);
	    Date dateStart=cal.getTime();
	    cal = Calendar.getInstance();
	    cal.setTime(date);
	    cal.add(Calendar.DAY_OF_MONTH, -1);
	    cal.add(Calendar.HOUR_OF_DAY, +23);
	    cal.add(Calendar.MINUTE, +59);
	    cal.add(Calendar.SECOND, +59);
	    Date dateEnd=cal.getTime();
		ids = routeService.getAllRouteIds();
		for (Long routeId : ids) {
			if (routeId!=0l){
			try {
				route = routeService.findByRouteId(routeId);
				tweetService.parseKml(System.getProperty("user.dir")
						+ "/src/kml/" + route.getKmlFile(), routeId);
			} catch (InstanceNotFoundException e) {
				e.printStackTrace();
			}
		
			Process p;
			File file = new File(System.getProperty("user.dir")
					+ "/src/script/tweets.txt");
			List<String> statsBD = new ArrayList<String>();
			try {
				List<Tweet> tweets = tweetService.findByRoute(routeId);
				List<Tweet> tweetsAux = new ArrayList<Tweet>();
				PrintWriter writer;
				file.createNewFile();
				FileOutputStream fos = new FileOutputStream(file);
				BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
						fos));
				for (Tweet tweet : tweets) {
					if (tweet.getTweetSentiment() == null) {
						tweetsAux.add(tweet);
						bw.write(Normalizer.normalize(tweet.getTweetText(),
								Normalizer.Form.NFD).replaceAll(
								"[^\\p{ASCII}]", ""));
						bw.newLine();
						bw.flush();
					}
				}
				bw.close();
				BufferedReader br = new BufferedReader(new FileReader(
						file.getAbsolutePath()));
				if (br.readLine() != null) {
					p = Runtime.getRuntime().exec(
							"python " + System.getProperty("user.dir")
									+ "/src/script/script.py "
									+ System.getProperty("user.dir")
									+ "/src/script/tweets.txt");

					OutputStream stdin = p.getOutputStream();
					InputStream stdout = p.getInputStream();

					String lineaConsola;

					Scanner scanner = new Scanner(stdout);
					while (scanner.hasNextLine()) {
						String result = scanner.nextLine();
						String[] results = result.substring(1,
								result.length() - 2).split(",");
						statsBD = Arrays.asList(results);
					}
					int i = 0;
					for (Tweet tweet : tweetsAux) {
						if (statsBD.get(i).contains("NONE")) {
							tweet.setTweetSentiment("NONE");
						} else {
							if (statsBD.get(i).contains("P"))
								tweet.setTweetSentiment("P");
							else
								tweet.setTweetSentiment("N");
						}
						tweetService.updateTweet(tweet);
						i++;
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			file.delete();
			List<Stat> stats=statService.findStatByRouteAndDate(routeId, 0, 1,date, null);
			if (stats==null || stats.size()==0){
				List<Result> resultsP = tweetService.getSentimentsByDate(routeId, "P", date, null);
				List<Result> resultsN = tweetService.getSentimentsByDate(routeId, "N", date, null);
				List<Result> resultsDateP = tweetService.getSentimentsByDate(routeId, "P", dateStart, dateEnd);
				List<Result> resultsDateN = tweetService.getSentimentsByDate(routeId, "N", dateStart, dateEnd);
				List<Long> balances = new ArrayList<Long>();
				int i=0;
				int count=0;
				List<Result> resultsDatePFinal=new ArrayList<Result>();
				List<Result> resultsDateNFinal=new ArrayList<Result>();
				for (Result result:resultsDateP){
					String dateResult=result.getDate().substring(0, 10);
					Boolean contains=false;
					count=0;
					for (Result balance: resultsDatePFinal){
						if (balance.getDate().equals(dateResult)){
							resultsDatePFinal.set(count, new Result(balance.getCount()+result.getCount(),balance.getDate()));
							contains=true;
						}
						count++;
					}
					if (!contains)
						resultsDatePFinal.add(new Result(result.getCount(),dateResult));
				}
				for (Result result:resultsDateN){
					String dateResult=result.getDate().substring(0, 10);
					Boolean contains=false;
					count=0;
					for (Result balance: resultsDateNFinal){
						if (balance.getDate().equals(dateResult)){
							resultsDateNFinal.set(count, new Result(balance.getCount()+result.getCount(),balance.getDate()));
							contains=true;
						}
						count++;
					}
					if (!contains)
						resultsDateNFinal.add(new Result(result.getCount(),dateResult));
				}
				Date dateStartAux=dateStart;
				Date dateEndAux=dateEnd;
				Calendar calendar = Calendar.getInstance();
			    calendar.setTime(dateEndAux);
			    calendar.add(Calendar.DAY_OF_MONTH, -1);
			    dateEndAux=cal.getTime();
				while (dateStartAux.before(dateEndAux)){
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
					String dateFinal = format1.format(dateStartAux);
					Long pValue=0l;
					Long nValue=0l;
					for (Result result:resultsDatePFinal){
						if (result.getDate().equals(dateFinal))
							pValue=result.getCount();
					}
					for (Result result:resultsDateNFinal){
						if (result.getDate().equals(dateFinal))
							nValue=result.getCount();
					}
					balances.add(pValue-nValue);
					calendar = Calendar.getInstance();
				    calendar.setTime(dateStartAux);
				    calendar.add(Calendar.DAY_OF_MONTH, 1);
				    dateStartAux=calendar.getTime();
					i++;
				}
				Stat stat = new Stat();
				stat.setStatDate(date);
				stat.setStatNNumber(Long.decode(String.valueOf(resultsN.size())));
				stat.setStatPNumber(Long.decode(String.valueOf(resultsP.size())));
				stat.setStatOpinionBalance(Long.decode(String.valueOf(resultsP.size()))-Long.decode(String.valueOf(resultsN.size())));
				stat.setStatTipicalDeviation(statService.getTypicalDeviation(balances));
				stat.setStatOldMiddle(statService.getOldMiddle(balances));
				calendar = Calendar.getInstance();
			    calendar.setTime(dateStartAux);
			    calendar.add(Calendar.DAY_OF_MONTH, -1);
			    dateStartAux=calendar.getTime();
				resultsP = tweetService.getSentimentsByDate(routeId, "P", dateStartAux, dateEndAux);
				resultsN = tweetService.getSentimentsByDate(routeId, "N", dateStartAux, dateEndAux);
				if ((resultsP.size()-resultsN.size())>=0 && stat.getStatOpinionBalance()<0l)
					stat.setStatNewNegative(true);
				else
					stat.setStatNewNegative(false);
				stat.setStatRouteId(routeId);
				statService.saveStat(stat);
				
			}else{
				List<Result> resultsP = tweetService.getSentimentsByDate(routeId, "P", date, null);
				List<Result> resultsN = tweetService.getSentimentsByDate(routeId, "N", date, null);
				List<Result> resultsDateP = tweetService.getSentimentsByDate(routeId, "P", dateStart, dateEnd);
				List<Result> resultsDateN = tweetService.getSentimentsByDate(routeId, "N", dateStart, dateEnd);
				List<Long> balances = new ArrayList<Long>();
				int i=0;
				int count=0;
				List<Result> resultsDatePFinal=new ArrayList<Result>();
				List<Result> resultsDateNFinal=new ArrayList<Result>();
				for (Result result:resultsDateP){
					String dateResult=result.getDate().substring(0, 10);
					Boolean contains=false;
					count=0;
					for (Result balance: resultsDatePFinal){
						if (balance.getDate().equals(dateResult)){
							resultsDatePFinal.set(count, new Result(balance.getCount()+result.getCount(),balance.getDate()));
							contains=true;
						}
						count++;
					}
					if (!contains)
						resultsDatePFinal.add(new Result(result.getCount(),dateResult));
				}
				for (Result result:resultsDateN){
					String dateResult=result.getDate().substring(0, 10);
					Boolean contains=false;
					count=0;
					for (Result balance: resultsDateNFinal){
						if (balance.getDate().equals(dateResult)){
							resultsDateNFinal.set(count, new Result(balance.getCount()+result.getCount(),balance.getDate()));
							contains=true;
						}
						count++;
					}
					if (!contains)
						resultsDateNFinal.add(new Result(result.getCount(),dateResult));
				}
				Date dateStartAux=dateStart;
				Date dateEndAux=dateEnd;
				Calendar calendar = Calendar.getInstance();
			    calendar.setTime(dateEndAux);
			    calendar.add(Calendar.DAY_OF_MONTH, -1);
			    dateEndAux=cal.getTime();
				while (dateStartAux.before(dateEndAux)){
					SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
					String dateFinal = format1.format(dateStartAux);
					Long pValue=0l;
					Long nValue=0l;
					for (Result result:resultsDatePFinal){
						if (result.getDate().equals(dateFinal))
							pValue=result.getCount();
					}
					for (Result result:resultsDateNFinal){
						if (result.getDate().equals(dateFinal))
							nValue=result.getCount();
					}
					balances.add(pValue-nValue);
					calendar = Calendar.getInstance();
				    calendar.setTime(dateStartAux);
				    calendar.add(Calendar.DAY_OF_MONTH, 1);
				    dateStartAux=calendar.getTime();
					i++;
				}
				Stat stat=stats.get(0);
				stat.setStatNNumber(Long.decode(String.valueOf(resultsN.size())));
				stat.setStatPNumber(Long.decode(String.valueOf(resultsP.size())));
				stat.setStatOpinionBalance(Long.decode(String.valueOf(resultsP.size()))-Long.decode(String.valueOf(resultsN.size())));
				stat.setStatTipicalDeviation(statService.getTypicalDeviation(balances));
				stat.setStatOldMiddle(statService.getOldMiddle(balances));
				calendar = Calendar.getInstance();
			    calendar.setTime(dateStartAux);
			    calendar.add(Calendar.DAY_OF_MONTH, -1);
			    dateStartAux=calendar.getTime();
				resultsP = tweetService.getSentimentsByDate(routeId, "P", dateStartAux, dateEndAux);
				resultsN = tweetService.getSentimentsByDate(routeId, "N", dateStartAux, dateEndAux);
				if ((resultsP.size()-resultsN.size())>=0 && stat.getStatOpinionBalance()<0l)
					stat.setStatNewNegative(true);
				else
					stat.setStatNewNegative(false);
				stat.setStatRouteId(routeId);
				statService.saveStat(stat);
			}
		}
		}
	}
}
