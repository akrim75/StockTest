package com.market.gbce;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.market.gbce.exception.MarketExcepionType;
import com.market.gbce.exception.MarketException;

import static java.util.concurrent.TimeUnit.*;

public class Market {

	/*
	 *  List of STOCKs of GBCE Market
	 */

	private  List<SingleStock> stocks;
		
	/*
	 *  Map of TicketTrade <stockSymbol,Trade> 
	 *  contains all stock price of the last 15 minutes...
	 *  the list of trade are update when income a new Trade price 
	 */

	private  Map<SingleStock, List<Trade>> lastTickerTrade;

	private static long MAX_DURATION = MILLISECONDS.convert(5, MINUTES);

	public Market() {
		stocks = new ArrayList<SingleStock>();
		lastTickerTrade = new HashMap<SingleStock, List<Trade>>();
	}

	/**
	 * Calculate Stock Price based on trades recorded in past 15 minutes
	 * @param stock
	 * @return
	 * @throws MarketException
	 */
	public  synchronized double calculateStockPrice(SingleStock stock) throws MarketException {		
		List<Trade> stockTrades = lastTickerTrade.get(stock);
		long quantityTot = 0;
		double tradeTot = 0.00;
		double stockPrice = 0.00;
		if (!stockTrades.isEmpty()) {
			for (Trade t : stockTrades) {
				quantityTot+= t.getShareVolume();
				tradeTot+= t.getTickerPrice()*t.getShareVolume();
			}
			stockPrice = tradeTot/quantityTot;
			return stockPrice;
		} else {
			throw new MarketException(MarketExcepionType.NO_STOCKS_IN_MARKET);
		}
	}

	/**
	*  Record a trade, with timestamp, quantity of shares, buy or sell indicator
	*  and price
    **/
	public synchronized  Trade recordTickerTrade(String stockSymbol, Long shareVolume,
			String indicator, Double price) throws MarketException {
		SingleStock newStock = new SingleStock(stockSymbol);		
		Date now = new Date();
		Trade t = new Trade(stockSymbol, shareVolume, indicator, price, now);
			if (lastTickerTrade.containsKey(newStock)) {	
				// remove the trade with timestamp <= (now -15 min)
				List<Trade> trades = lastTickerTrade.get(newStock);
				List<Trade> tradeToRemove = new ArrayList<Trade>();
				for (Trade oldTrade: trades) {
					long interval = now.getTime()
							- oldTrade.getTimestamp().getTime();
					if (interval > MAX_DURATION) {
						tradeToRemove.add(oldTrade);
					}
				}
				if(!tradeToRemove.isEmpty()) {
					trades.removeAll(tradeToRemove);
				}
				// add last trade
				List<Trade> list= lastTickerTrade.get(newStock);
				list.add(t);
				int index = stocks.indexOf(newStock);
				if (index != -1) {
					lastTickerTrade.put(stocks.get(index), list);
				} else {
					throw new MarketException(MarketExcepionType.STOCK_INVALID);
				}	
			} else {
				int index = stocks.indexOf(newStock);
				if (index != -1) {
					lastTickerTrade.put(stocks.get(index),new ArrayList<Trade>( Arrays.asList(t)));
				} else {
					throw new MarketException(MarketExcepionType.STOCK_INVALID);
				}
			}
			return t;

	}

	/**
	 *  Calculate the GBCE All Share Index using the geometric mean of prices for all stocks
	 * @return
	 * @throws MarketException
	 */

	public synchronized double calculateAllShareIndex() throws MarketException{
		double priceStock = 1.00;
		double singlePrice;
		if (stocks != null && !stocks.isEmpty()) {
			for (SingleStock s : stocks) {
				singlePrice = calculateStockPrice(s);
				System.out.println(s.getStockSymbol()+ " price "+singlePrice); // da eliminare
				//priceStock = priceStock * calculateStockPrice(s);
				priceStock = priceStock*singlePrice;
			}
			double allShareIndex = Math.pow(priceStock, (1/(double)stocks.size()));
			return allShareIndex;
		} else {
			throw  new MarketException(MarketExcepionType.NO_STOCKS_IN_MARKET);
		}
	}



	/**
	 * @param stocks the stocks to set
	 */
	public  void addStock(SingleStock stock) {
		stocks.add(stock);
	}

	/**
	 * @return the stocks
	 */
	public  List<SingleStock> getStocks() {
		return stocks;
	}

	/**
	 * @param stocks the stocks to set
	 */
	public void setStocks(List<SingleStock> stocks) {
		this.stocks = stocks;
	}

	/**
	 * @return the lastTickerTrade
	 */
	public Map<SingleStock, List<Trade>> getLastTickerTrade() {
		return lastTickerTrade;
	}
	
	
	
	
	
	
	
	

}
