package com.example.bookstore.entity;

import java.sql.Time;
import java.time.Instant;
import java.time.LocalTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;

@Entity
@Table(name = "order_tbl")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
	@JoinColumn(name = "book_id", nullable = false)
	private Book book;

	@Column(nullable = false)
	private int quantity;

	private double amount;

	@CreationTimestamp
	private Instant createdAt;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Enumerated(value = EnumType.STRING)
	private StatusType orderStatus;

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User customer) {
		this.user = customer;
	}

	public StatusType getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(StatusType orderStatus) {
		this.orderStatus = orderStatus;
	}

	public int getId() {
		return id;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	@Override
	public String toString() {
		return "Order{" + "id=" + id + ", book=" + (book != null ? book.getId() : "null") + // Assuming Book has an id
																							// field
				", quantity=" + quantity + ", amount=" + amount + ", createdAt=" + createdAt + ", user="
				+ (user != null ? user.getId() : "null") + // Assuming User has an id field
				", orderStatus=" + orderStatus + '}';
	}

}
