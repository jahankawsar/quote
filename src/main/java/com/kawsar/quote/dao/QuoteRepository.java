package com.kawsar.quote.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.kawsar.quote.model.Quote;


@Repository
public interface QuoteRepository extends JpaRepository<Quote, Integer> {

}
