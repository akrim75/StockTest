package com.market.gbce.exception;

public class MarketException extends Exception {
	
	private static final long serialVersionUID = 2723001877112795619L;
	
	private int codeError;
	
	/**
	 * @return the codeError
	 */
	public int getCodeError() {
		return codeError;
	}

	/**
	 * @param codeError the codeError to set
	 */
	public void setCodeError(int codeError) {
		this.codeError = codeError;
	}

	public MarketException(MarketExcepionType exceptiontype){
	  super(exceptiontype.getMessage());
	}
	

	
	

}
