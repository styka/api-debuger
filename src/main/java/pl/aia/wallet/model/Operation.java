package pl.aia.wallet.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Operation {

	@Id
	@GeneratedValue
	public Long id;
	public Date date;
	public BigDecimal amount;
	public String title;
	
	public Operation() {
		super();
	}
	@Override
	public String toString() {
		return "Operation [id=" + id + ", date=" + date + ", amount=" + amount + ", title=" + title + "]";
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public BigDecimal getAmount() {
		return amount; 
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
