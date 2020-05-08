package com.kawsar.quote.service.impl;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;


import com.kawsar.quote.dao.QuoteRepository;
import com.kawsar.quote.model.Quote;
import com.kawsar.quote.service.QuoteService;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;


@Service
public class QuoteServiceImpl implements QuoteService {
	
	private static final Logger logger = LogManager.getLogger(QuoteServiceImpl.class);
	
	@Value("classpath:en-sent.bin")
	Resource sentenceDetect;
	
	@Value("classpath:en-pos-maxent.bin")
	Resource pos;
	
	@Value("classpath:en-token.bin")
	Resource token;
	
	@Autowired
	QuoteRepository quoteRepo;

	@Override
	public List<Quote> getAllQuote() throws Exception {
		List<Quote> quotes = quoteRepo.findAll();
		return quotes;
	}

	@Override
	public List<Quote> getQuoteByIds(List<Integer> ids) {
		List<Quote> quotes = quoteRepo.findAllById(ids);
		return quotes;
	}

	@Override
	public void addQuote(Quote quote) {
		quoteRepo.save(quote);		
	}

	@Override
	public Quote getQuoteById(int id) {
		return quoteRepo.getOne(id);
	}

	@Override
	public void deleteQuote(int id) {
		quoteRepo.deleteById(id);		
	}

	@Override
	public String getWithoutThirdFifthSentence(int id) throws Exception {
		
		List<String> result = new ArrayList<String>();		
		
		Quote quote = quoteRepo.getOne(id);
		String paragraph = quote.getQuote();
		InputStream is = new FileInputStream(sentenceDetect.getFile());
        SentenceModel model = new SentenceModel(is); 
        
        SentenceDetectorME sdetector = new SentenceDetectorME(model); 
        String sentences[] = sdetector.sentDetect(paragraph); 
        
        // get first two sentences
        if (sentences.length >= 3) {
        	for (int i = 0; i < 2; i++) {
        		result.add(sentences[i]);
        	}
        }
        
        // get fourth sentence
        if (sentences.length >= 4 ) {
        	result.add(sentences[3]);
        }
        
        // get sixth and onward sentences
        if (sentences.length > 5) {
        	for (int i = 5; i < sentences.length; i++) {
        		result.add(sentences[i]);
        	}
        }
        
        return result.stream().collect(Collectors.joining(" "));		
	}

	@Override
	public Double getNounCount(int id) throws Exception {
		
		Double count = 0.0;

		Quote quote = quoteRepo.getOne(id);
		
		if (quote == null || quote.getQuote().isBlank() || quote.getQuote().isEmpty()) {
			return count;
		}
		
		String sentence = quote.getQuote();
		
		InputStream tokenModelIn = null;
        InputStream posModelIn = null; 
        
        try {
        	tokenModelIn = new FileInputStream(token.getFile());
        	TokenizerModel tokenModel = new TokenizerModel(tokenModelIn);
            Tokenizer tokenizer = new TokenizerME(tokenModel);
            
            String tokens[] = tokenizer.tokenize(sentence); 
            
            posModelIn = new FileInputStream(pos.getFile()); 
            POSModel posModel = new POSModel(posModelIn);
            POSTaggerME posTagger = new POSTaggerME(posModel); 
            
            String tags[] = posTagger.tag(tokens); 
            double probs[] = posTagger.probs(); 
            
            for (int i = 0; i<tags.length; i++) {
            	
            	if (tags[i].equalsIgnoreCase("NN")) { //Noun, singular or mass
            		count += probs[i];
            		
            	} else if (tags[i].equalsIgnoreCase("NNS")) { // Noun, plural
            		count += probs[i];
            		
            	} else if (tags[i].equalsIgnoreCase("NNP")) { // Proper noun, singular
            		count += probs[i];
            		
            	} else if (tags[i].equalsIgnoreCase("NNPS")) { // Proper noun, plural
            		count += probs[i];
            	} 
            }
            
        } catch (IOException e) {
        	
        	logger.error("Error occured in QuoteServiceImpl.getNounCount " + " - " + e.getMessage());
        	throw e;
        }
		return count;
	}

	@Override
	public Double getVerbCount(int id) throws Exception {
		
		Double count = 0.0;

		Quote quote = quoteRepo.getOne(id);
		
		if (quote == null || quote.getQuote().isBlank() || quote.getQuote().isEmpty()) {
			return count;
		}
		
		String sentence = quote.getQuote();
		
		InputStream tokenModelIn = null;
        InputStream posModelIn = null; 
        
        try {
        	tokenModelIn = new FileInputStream(token.getFile());
        	TokenizerModel tokenModel = new TokenizerModel(tokenModelIn);
            Tokenizer tokenizer = new TokenizerME(tokenModel);
            
            String tokens[] = tokenizer.tokenize(sentence); 
            
            posModelIn = new FileInputStream(pos.getFile()); 
            POSModel posModel = new POSModel(posModelIn);
            POSTaggerME posTagger = new POSTaggerME(posModel); 
            
            String tags[] = posTagger.tag(tokens); 
            double probs[] = posTagger.probs(); 
            
            for (int i = 0; i<tags.length; i++) {
            	
            	if (tags[i].equalsIgnoreCase("VB")) { // Verb, base form
            		count += probs[i];
            		
            	} else if (tags[i].equalsIgnoreCase("VBD")) { // Verb, past tense
            		count += probs[i];
            		
            	} else if (tags[i].equalsIgnoreCase("VBG")) { // Verb, gerund or present participle
            		count += probs[i];
            		
            	} else if (tags[i].equalsIgnoreCase("VBN")) { // Verb, past participle
            		count += probs[i];
            		
            	} else if (tags[i].equalsIgnoreCase("VBP")) { // Verb, non-3rd person singular present 
            		count += probs[i];
            		
            	} else if (tags[i].equalsIgnoreCase("VBZ")) { // Verb, 3rd person singular present 
            		count += probs[i];
            	} 
            }
            
        } catch (IOException e) {
        	
        	logger.error("Error occured in QuoteServiceImpl.getVerbCount " + " - " + e.getMessage());
        	throw e;
        }
        
		return count;
	}

	@Override
	public Double getPeriodCount(int id) throws Exception {
		Double count = 0.0;

		Quote quote = quoteRepo.getOne(id);
		
		if (quote == null || quote.getQuote().isBlank() || quote.getQuote().isEmpty()) {
			return count;
		}
		
		String sentence = quote.getQuote();
		
		InputStream tokenModelIn = null;
        InputStream posModelIn = null; 
        
        try {
        	tokenModelIn = new FileInputStream(token.getFile());
        	TokenizerModel tokenModel = new TokenizerModel(tokenModelIn);
            Tokenizer tokenizer = new TokenizerME(tokenModel);
            
            String tokens[] = tokenizer.tokenize(sentence); 
            
            posModelIn = new FileInputStream(pos.getFile()); 
            POSModel posModel = new POSModel(posModelIn);
            POSTaggerME posTagger = new POSTaggerME(posModel); 
            
            String tags[] = posTagger.tag(tokens); 
            double probs[] = posTagger.probs(); 
            
            for (int i = 0; i<tags.length; i++) {
            	
            	if (tags[i].equalsIgnoreCase(".")) { 
            		count += probs[i];       		
            	} 
            }
            
        } catch (IOException e) {
        	
        	logger.error("Error occured in QuoteServiceImpl.getPeriodCount " + " - " + e.getMessage());
        	throw e;
        }
        
		return count;
	}

	@Override
	public Double getProbOfToken(int id, String keyword) throws Exception {
		Double count = 0.0;

		Quote quote = quoteRepo.getOne(id);
		
		if (quote == null || quote.getQuote().isBlank() || quote.getQuote().isEmpty()) {
			return count;
		}
		
		String sentence = quote.getQuote();
		
		InputStream tokenModelIn = null;
        InputStream posModelIn = null; 
        
        try {
        	tokenModelIn = new FileInputStream(token.getFile());
        	TokenizerModel tokenModel = new TokenizerModel(tokenModelIn);
            Tokenizer tokenizer = new TokenizerME(tokenModel);
            
            String tokens[] = tokenizer.tokenize(sentence); 
            
            posModelIn = new FileInputStream(pos.getFile()); 
            POSModel posModel = new POSModel(posModelIn);
            POSTaggerME posTagger = new POSTaggerME(posModel); 
            
            String tags[] = posTagger.tag(tokens); 
            double probs[] = posTagger.probs(); 
            
            for (int i = 0; i<tokens.length; i++) {
            	
            	if (tokens[i].equalsIgnoreCase(keyword)) { 
            		count += probs[i];       		
            	} 
            }
            
        } catch (IOException e) {
        	
        	logger.error("Error occured in QuoteServiceImpl.getProbOfToken " + " - " + e.getMessage());
        	throw e;
        }
        
		return count;
	}
}
