package com.market.gbce;

import com.market.gbce.Trade;
import com.market.gbce.exception.MarketExcepionType;
import com.market.gbce.exception.MarketException;





public class SingleStock {

	private String stockSymbol;
	private StockType type;
	private Integer lastDividend;
	private Double fixedDividend;
	private int parValue;
	


	/**
	 * Create a complete Stock record with all information
	 * @param stockSymbol
	 * @param typeIn
	 * @param parValue
	 */
	public SingleStock(String stockSymbol,String typeIn,int parValue) {
		super();
		setStockSymbol(stockSymbol);
		setType(StockType.getType(typeIn));
		setParValue(parValue);
	}
	
	
	
	
	public SingleStock(String stockSymbol, String typeIn, int parValue, int lastDividend,
			Double fixedDividend) {
		super();
		setStockSymbol(stockSymbol);
		setType(StockType.getType(typeIn));
		setLastDividend(lastDividend);
		setParValue(parValue);
		if(fixedDividend !=null){
			setFixedDividend(fixedDividend);
		}
		
	}
	
	




	/**
	 * Create an incomplete stocks record
	 * @param stockSymbol
	 */
	public SingleStock(String stockSymbol){
		super();
		setStockSymbol(stockSymbol);
	}

	/*
	 *  calculate the dividend yield
	 */
	public double calculateDividendYeld(Trade tickerTrade) throws MarketException{
		double dividend = 0.00;
		if(this.getType() != null){
			if (tickerTrade.getTickerPrice() != 0) {
				switch (getType()) {
				case PREFERRED: {
					dividend = (getFixedDividend() * getParValue())
							/ tickerTrade.getTickerPrice();
					break;
					}
				case COMMON:
				default: {
					dividend = ((double)getLastDividend()) / tickerTrade.getTickerPrice();
	
					}
	
				}
			}
		} else {
			throw new MarketException(MarketExcepionType.STOCK_INVALID);
		}
		return dividend;
	}

	/*
	 *  calculate the P/E Ratio
	 */
	public double calculatePERatio(Trade tickerTrade) throws MarketException{
		double dividend = calculateDividendYeld(tickerTrade);
		double peRatio = 0.00;
		if(dividend != 0){
			peRatio = tickerTrade.getTickerPrice()/dividend;
		}
		return peRatio;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((stockSymbol == null) ? 0 : stockSymbol.hashCode());
		return result;
	}

	/* the equals method according only stockSymbol
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof SingleStock)) {
			return false;
		}
		SingleStock other = (SingleStock) obj;
		if (stockSymbol == null) {
			if (other.stockSymbol != null) {
				return false;
			}
		} else if (!stockSymbol.equals(other.stockSymbol)) {
			return false;
		}
		return true;
	}
	public String getStockSymbol() {
		return stockSymbol;
	}

	public void setStockSymbol(String stockSymbol) {
		this.stockSymbol = stockSymbol;
	}

	public StockType getType() {
		return type;
	}

	public void setType(StockType type) {
		this.type = type;
	}

	public Integer getLastDividend() {
		return lastDividend;
	}

	public void setLastDividend(Integer lastDividend) {
		this.lastDividend = lastDividend;
	}

	public Double getFixedDividend() {
		return fixedDividend;
	}

	public void setFixedDividend(Double fixedDividend) {
		this.fixedDividend = fixedDividend;
	}

	public Integer getParValue() {
		return parValue;
	}

	public void setParValue(Integer parValue) {
		this.parValue = parValue;
	}
	
	
	enum StockType {
		PREFERRED, COMMON;
		
		static StockType getType(String type) {
    		// TODO Auto-generated method stub
    	    if(PREFFERED.toString().equalsIgnoreCase(type)){
    	    	return PREFERRED;
    	    } else if(COMMON.toString().equalsIgnoreCase(type)){
    	    	return COMMON;
    	    } else {
    	    	return null;
    	    }
    	}
	}
	
	
	

}
