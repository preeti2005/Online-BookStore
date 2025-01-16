
package com.example.bookstore.entity;
import jakarta.persistence.*;

@Entity
@Table(name="book_tbl")
public class Book {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(unique= true , nullable =false )
	private String name;
	
	@Column(unique= true , nullable =false )
	private String author;
	
	private double price;
	
	@Column(unique=true)
	private String imageId;

	public int getId() {
		return id;
	}
    
	public void setId(int id)
	{
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	
	
	@Override 
	public String toString()
	{
		return "Name : "+this.name;
	}

	public Book orElseThrow(Object object) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
