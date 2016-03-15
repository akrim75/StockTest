/**
 * 
 */
package com.market.gbce.test;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.MINUTES;
import static org.junit.Assert.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.market.gbce.Market;
import com.market.gbce.SingleStock;
import com.market.gbce.Trade;
import com.market.gbce.exception.MarketException;

/**
 * @author Mirka Zanotti
 *
 */
public class GBCETest {
	
	private static Market gbceMarket;
	DecimalFormat df = new DecimalFormat("0.0000");

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpClass() throws Exception {
		//Insert stock in GBCE Market
		gbceMarket = new Market();
		populateStocks(gbceMarket);
		
		// launch trade generator
		GBCENasdaTester  nasda = new GBCENasdaTester(10,5,gbceMarket);// every 10 seconds for 20 minutes
		
	}

	/**
	 * Stock Symbol	Type	Last Dividend	Fixed Dividend	Par Value	
				TEA	Common		0							100	
				POP	Common		8							100	
				ALE	Common		23							60	
				GIN	Preferred	8				2%			100	
				JOE	Common		13							250	

	 * @param gbceMarket
	 */
	private static void populateStocks(Market gbceMarket) { 
		SingleStock[] stocks = new SingleStock[5];
		stocks[0] = new SingleStock("TEA","Common",100,0,null);
		stocks[1] = new SingleStock("POP","Common",100,8,null);
		stocks[2] = new SingleStock("ALE","Common",60,23,null);
		stocks[3] = new SingleStock("GIN","Preferred",8,100,0.02);
		stocks[4] = new SingleStock("JOE","Common",13,250,null);
		
		gbceMarket.setStocks(new ArrayList<SingleStock> (Arrays.asList(stocks)));
		
	}
	
	
	/**
	 * Test calculate market stock price 
	**/
	@Test
	public void calculateMarketStockPrice() {
		
		try {
			
			int count=1;
			while(count<2){
				Thread.sleep(MILLISECONDS.convert(3, MINUTES));				
				System.out.println("***********  "+(count++)+"° MARKET PRICE  **************");
				for(SingleStock stock: gbceMarket.getStocks()){
					System.out.println("Stock: "+stock.getStockSymbol()+" Price : "+ df.format(gbceMarket.calculateStockPrice(stock))+" £");
				}
				 // calculate price every 5 minutes
				
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch(MarketException me) {
			fail(me.getMessage());
		}
		
		
	}
	
	@Test
	public void calculateAllShareIndex() {
		try {
			/**
			 * after 5 minutes of opened of GBCE Market  ( or stop of the previous test)
			 * determine AllShareIndex of GBCE
			 */
			Thread.sleep(MILLISECONDS.convert(5, MINUTES));
			double asi = gbceMarket.calculateAllShareIndex();
			System.out.println("GBCE All Share Index "+df.format(asi));
			assertTrue(asi>0);//simple test positive value of index
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail(e.getMessage());
		} catch(MarketException me) {
			fail(me.getMessage());
		}
	}
	
	@Test
	public void determineActualDividendYeld(){
		/**
		 * Determine the dividend yield of the 
		 */
		Set<SingleStock> entryStocks = gbceMarket.getLastTickerTrade().keySet();
		try {
		for(SingleStock s:entryStocks) {
			if(gbceMarket.getLastTickerTrade().containsKey(s) && !gbceMarket.getLastTickerTrade().get(s).isEmpty()) {
				Trade tickerTrade = gbceMarket.getLastTickerTrade().get(s).get(0);
				   
					double dividend = s.calculateDividendYeld(tickerTrade);
					System.out.println(s.getStockSymbol()+" "+dividend);
			}
		}
		} catch(MarketException me){
			fail(me.getMessage());
		}
		
		
		
	}


}
