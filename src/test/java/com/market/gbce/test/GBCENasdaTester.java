package com.market.gbce.test;


import static java.util.concurrent.TimeUnit.*;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import com.market.gbce.Market;
import com.market.gbce.Trade;
import com.market.gbce.exception.MarketException;

public class GBCENasdaTester {

	/**
	 * Simple demo that uses java.util.Timer to schedule a task 
	 * to execute once 5 seconds have passed.
	 */
	    Timer timer;
	    private static Double MIN_PRICE=0.00;
	    private static Double MAX_PRICE=200.00;
	    
	    private static long MIN_VOLUME=0;
	    private static long MAX_VOLUME=100;
	    
	    private Market myMarket;
	    private long endTime; 
	    DecimalFormat df = new DecimalFormat("#.0000"); 
	    
	    
	    public GBCENasdaTester(int seconds, int periodInMinutes, Market gbceMarket) {
	    	myMarket = gbceMarket;
	        timer = new Timer();
	        long startTime = System.currentTimeMillis();
	        endTime =startTime+ MILLISECONDS.convert(periodInMinutes, MINUTES);
	        timer.scheduleAtFixedRate(new GenerateTradePriceTask(),0, MILLISECONDS.convert(seconds, SECONDS));
		}

	    /**
	     * It simulates a market trend with continuous changes in selling prices of the various stocks 
	     * @author Betacom
	     *
	     */
	    class GenerateTradePriceTask extends TimerTask {
	        public void run() {
	        	if(endTime>System.currentTimeMillis()){
		        	Random generator = new Random();
		        	// generate randomic trade values
		        	int indexStock= generator.nextInt(myMarket.getStocks().size());
		        	String stockSymbol= myMarket.getStocks().get(indexStock).getStockSymbol();
		        	long shareVolume=MIN_VOLUME+(long)((MAX_VOLUME-MIN_VOLUME)*generator.nextDouble());
		        	String indicator = generator.nextBoolean()?"SELL":"BUY";
		        	Double price = MIN_PRICE+((MAX_PRICE-MIN_PRICE)*generator.nextDouble());
		        	
		        	System.out.println("New Trade : "+ stockSymbol+ " "+shareVolume+" "+indicator+ " "+df.format(price)+ " £");
		        	try {
		        		 myMarket.recordTickerTrade(stockSymbol, shareVolume, indicator, price);
		        		
					} catch (MarketException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	        	} else {
	        		timer.cancel(); //Terminate the timer thread
	        	}	
	        }
	    }

	   
	}