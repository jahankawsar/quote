package com.kawsar.quote.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.kawsar.quote.model.Quote;
import com.kawsar.quote.service.QuoteService;

/**
 * 
 * @author Kawsar Jahan
 * Controller for adding, retrieving, updating, deleting, and getting more information about the quotes
 * 
 */

@RestController
public class QuoteController {
	
	private static final Logger logger = LogManager.getLogger(QuoteController.class);
	
	@Autowired
	QuoteService quoteService;

	/**
	 * Retrieve all the quotes from the database
	 * @return a list of quotes
	 * @throws Exception
	 */
	@GetMapping("/quotes")
	public ResponseEntity<List<Quote>> getQuotes() throws Exception {
		logger.info("Retrieving all the quotes");
		
		try { 
			List<Quote> quotes = quoteService.getAllQuote();
	
			return new ResponseEntity<>(quotes, HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error occured while retrieving all quotes from the database : "
					+" - " + e.getMessage());
			throw e;
		}		
	}
	
	/**
	 * Retrieve a quote by id without third and fifth sentence
	 * @return a quote without third and fifth sentences
	 * @throws Exception 
	 */
	@GetMapping("/quoteWithoutThirdFifth/{id}")
	public ResponseEntity<String> getWithoutThirdFifthsentences (@PathVariable("id") int id) throws Exception {
		logger.info("Retrieving quote without third and fifth sentence");
		
		try {
			String quote = quoteService.getWithoutThirdFifthSentence(id);
			return new ResponseEntity<>(quote, HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error occured while retrieving quote by id to display quote without third and fifth sentences : " + " - " + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * Get specific information about the quote
	 * @return how many nouns in the quote 
	 * @throws Exception 
	 */
	@GetMapping("/quote_noun_count/{id}")
	public ResponseEntity<Double> getNounCount (@PathVariable("id") int id) throws Exception {
		logger.info("Retrieving noun count from a quote");
		
		try {
			Double count = quoteService.getNounCount(id);
			return new ResponseEntity<>(count, HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error occured while retrieving count of noun in the quote : " + " - " + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * Get specific information about the quote
	 * @return how many verbs in the quote 
	 * @throws Exception 
	 */
	@GetMapping("/quote_verb_count/{id}")
	public ResponseEntity<Double> getVerbCount (@PathVariable("id") int id) throws Exception {
		logger.info("Retrieving verb count from a quote");
		
		try {
			Double count = quoteService.getVerbCount(id);
			return new ResponseEntity<>(count, HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error occured while retrieving count of verb in the quote : " + " - " + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * Get specific information about the quote
	 * @return how many periods in the quote 
	 * @throws Exception 
	 */
	@GetMapping("/quote_period_count/{id}")
	public ResponseEntity<Double> getPeriodCount (@PathVariable("id") int id) throws Exception {
		logger.info("Retrieving period count from a quote");
		
		try {
			Double count = quoteService.getPeriodCount(id);
			return new ResponseEntity<>(count, HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error occured while retrieving count of period in the quote : " + " - " + e.getMessage());
			throw e;
		}
	}
	
	/**
	 * Get specific information about the quote
	 * @return a count of a keyword in the quote 
	 * @throws Exception 
	 */
	@GetMapping("/quote_keyword_count/{id}/{key}")
	public ResponseEntity<Double> getKeywordCount (@PathVariable("id") int id, @PathVariable("key") String key) throws Exception {
		logger.info("Retrieving count of keyword in the quote");
		
		try {
			Double count = quoteService.getProbOfToken(id, key);
			return new ResponseEntity<>(count, HttpStatus.OK);
			
		} catch (Exception e) {
			logger.error("Error occured while retrieving count of keyword in the quote : " + " - " + e.getMessage());
			throw e;
		}
	}
}
