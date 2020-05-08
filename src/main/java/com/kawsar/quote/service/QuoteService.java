package com.kawsar.quote.service;

import java.util.List;
import java.util.Map;

import com.kawsar.quote.model.Quote;

public interface QuoteService {
	
	public List<Quote> getAllQuote() throws Exception;
	public List<Quote> getQuoteByIds(List<Integer> ids); 
	public void addQuote(Quote quote);
	public Quote getQuoteById(int id);
	public void deleteQuote(int id);
	public String getWithoutThirdFifthSentence(int id) throws Exception;
	public Double getNounCount(int id) throws Exception;
	public Double getVerbCount(int id) throws Exception;
	public Double getPeriodCount(int id) throws Exception;
	public Double getProbOfToken(int id, String token) throws Exception;
}
