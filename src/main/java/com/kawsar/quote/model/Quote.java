package com.kawsar.quote.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "quote", schema = "database1")
public class Quote {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int quote_id;
	
	private String quote;
	private String author_firstname;
	private String author_lastname;
	private String book_name;
	private String created_by;
	private Date dt_created;
	private String modified_by;
	private Date dt_modified;
	
	public Quote () {};
	
	public Quote(int quote_id, String quote, String author_firstname, String author_lastname, String book_name,
			String created_by, Date dt_created, String modified_by, Date dt_modified) {
		super();
		this.quote_id = quote_id;
		this.quote = quote;
		this.author_firstname = author_firstname;
		this.author_lastname = author_lastname;
		this.book_name = book_name;
		this.created_by = created_by;
		this.dt_created = dt_created;
		this.modified_by = modified_by;
		this.dt_modified = dt_modified;
	}
	
	@Column(name = "quote_id", unique = true, nullable = false)
	public int getQuote_id() {
		return quote_id;
	}

	public void setQuote_id(int quote_id) {
		this.quote_id = quote_id;
	}

	@Column(name = "quote")
	public String getQuote() {
		return quote;
	}

	public void setQuote(String quote) {
		this.quote = quote;
	}
	
	@Column(name = "author_firstname")
	public String getAuthor_firstname() {
		return author_firstname;
	}

	public void setAuthor_firstname(String author_firstname) {
		this.author_firstname = author_firstname;
	}

	@Column(name = "author_lastname")
	public String getAuthor_lastname() {
		return author_lastname;
	}

	public void setAuthor_lastname(String author_lastname) {
		this.author_lastname = author_lastname;
	}

	@Column(name = "book_name")
	public String getBook_name() {
		return book_name;
	}

	public void setBook_name(String book_name) {
		this.book_name = book_name;
	}

	@Column(name = "created_by")
	public String getCreated_by() {
		return created_by;
	}

	public void setCreated_by(String created_by) {
		this.created_by = created_by;
	}

	@Column(name = "dt_created")
	public Date getDt_created() {
		return dt_created;
	}

	public void setDt_created(Date dt_created) {
		this.dt_created = dt_created;
	}

	@Column(name = "modified_by")
	public String getModified_by() {
		return modified_by;
	}

	public void setModified_by(String modified_by) {
		this.modified_by = modified_by;
	}

	@Column(name = "dt_modified")
	public Date getDt_modified() {
		return dt_modified;
	}

	public void setDt_modified(Date dt_modified) {
		this.dt_modified = dt_modified;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author_firstname == null) ? 0 : author_firstname.hashCode());
		result = prime * result + ((author_lastname == null) ? 0 : author_lastname.hashCode());
		result = prime * result + ((book_name == null) ? 0 : book_name.hashCode());
		result = prime * result + ((created_by == null) ? 0 : created_by.hashCode());
		result = prime * result + ((dt_created == null) ? 0 : dt_created.hashCode());
		result = prime * result + ((dt_modified == null) ? 0 : dt_modified.hashCode());
		result = prime * result + ((modified_by == null) ? 0 : modified_by.hashCode());
		result = prime * result + ((quote == null) ? 0 : quote.hashCode());
		result = prime * result + quote_id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Quote other = (Quote) obj;
		if (author_firstname == null) {
			if (other.author_firstname != null)
				return false;
		} else if (!author_firstname.equals(other.author_firstname))
			return false;
		if (author_lastname == null) {
			if (other.author_lastname != null)
				return false;
		} else if (!author_lastname.equals(other.author_lastname))
			return false;
		if (book_name == null) {
			if (other.book_name != null)
				return false;
		} else if (!book_name.equals(other.book_name))
			return false;
		if (created_by == null) {
			if (other.created_by != null)
				return false;
		} else if (!created_by.equals(other.created_by))
			return false;
		if (dt_created == null) {
			if (other.dt_created != null)
				return false;
		} else if (!dt_created.equals(other.dt_created))
			return false;
		if (dt_modified == null) {
			if (other.dt_modified != null)
				return false;
		} else if (!dt_modified.equals(other.dt_modified))
			return false;
		if (modified_by == null) {
			if (other.modified_by != null)
				return false;
		} else if (!modified_by.equals(other.modified_by))
			return false;
		if (quote == null) {
			if (other.quote != null)
				return false;
		} else if (!quote.equals(other.quote))
			return false;
		if (quote_id != other.quote_id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Quote [quote_id=" + quote_id + ", quote=" + quote + ", author_firstname=" + author_firstname
				+ ", author_lastname=" + author_lastname + ", book_name=" + book_name + ", created_by=" + created_by
				+ ", dt_created=" + dt_created + ", modified_by=" + modified_by + ", dt_modified=" + dt_modified + "]";
	}
}
