
package com.example.bookstore.entity;

import jakarta.persistence.*;

@Entity
@Table(name="user_tbl")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(unique = true)
	private String username;
	private String password;
	@Column(unique = true)
	private String email;
	@Enumerated(value = EnumType.STRING)
	private Role role;
	
	@Column(unique = true,nullable=true)
	private String cookieId;
	
	@Column(unique=true)
	private String imageId;
	//setters and getter for fields
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getCookieId() {
		return cookieId;
	}
	public void setCookieId(String cookieId) {
		this.cookieId = cookieId;
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
		
	return 	"Id :"+this.id +"/n"+
	    "Username :"+this.username +"\n"+
	    "Password :"+this.password+"\n"+
	    "Email :"+this.email+"\n"+
	    "role :"+this.role+"\n"+
	    "cookieId :"+this.cookieId+"\n"+
	    "imageId :"+this.imageId;
	
	}


}
