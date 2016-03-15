package com.market.gbce;

import java.util.Date;

public class Trade {
	
	private String stockSymbol;
	
	private double tickerPrice;
	
	private Date  timestamp;
	
    private long shareVolume;
    
    /*
     * If the Buy or Sell Indicator:if flag is:
     * 		BUY => Buy signal (green) 
     *      SELL => Sell Signal (red)
     */
    private IndicatorType indicatorBuySell;



	public Trade(String stockSymbol, long shareVolume, String indicator,
			Double price,Date timestamp) { 
	      this.setStockSymbol(stockSymbol);
	      this.setShareVolume(shareVolume);
	      this.setTimestamp(timestamp);	 
	      this.setTickerPrice(price);
	      this.setIndicatorBuySell(IndicatorType.getIndicator(indicator));
	}



	public String getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	public double getTickerPrice() {
		return tickerPrice;
	}

	public void setTickerPrice(Double tickerPrice) {
		this.tickerPrice = tickerPrice;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public long getShareVolume() {
		return shareVolume;
	}

	public void setShareVolume(Long shareVolume) {
		this.shareVolume = shareVolume;
	}

	public IndicatorType getIndicatorBuySell() {
		return indicatorBuySell;
	}

	public void setIndicatorBuySell(IndicatorType indicatorBuySell) {
		this.indicatorBuySell = indicatorBuySell;
	} 
    
    
	/**
	 * Enum class to register the BUY/SELL indicator flag
	 * @author m.zanotti
	 *
	 */
    enum IndicatorType {
    	BUY, SELL;
    	
    	static IndicatorType getIndicator(String indicator) {
    		// TODO Auto-generated method stub
    	    if(SELL.toString().equalsIgnoreCase(indicator)){
    	    	return SELL;
    	    } else if(BUY.toString().equalsIgnoreCase(indicator)){
    	    	return BUY;
    	    } else {
    	    	return null;
    	    }
    	}
    }


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Trade [stockSymbol=" + stockSymbol + ", tickerPrice="
				+ tickerPrice + ", timestamp=" + timestamp + ", shareVolume="
				+ shareVolume + ", indicatorBuySell=" + indicatorBuySell + "]";
	}
	
    
    

}
