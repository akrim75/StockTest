package com.market.gbce.exception;

public enum MarketExcepionType {

	NO_STOCKS_IN_MARKET(1,"No stocks register"),
	STOCK_INVALID(2,"The stock symbol is not registered in GBCE ");
	
	private int code;
	private String message;
	
	
	private MarketExcepionType(int code, String message) {
		this.code = code;
		this.message = message;
	}


	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}


	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}


	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}


	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}



	
	
	
}
