package com.blog.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Vote implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	@Id	//主键
	@GeneratedValue(strategy=GenerationType.IDENTITY)	//自增长
	private Long id;
	
	@OneToOne(cascade=CascadeType.DETACH,fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User user;
	
	@Column(nullable=false)
	@org.hibernate.annotations.CreationTimestamp // 由数据库自动创建时间
	private Timestamp timestamp;
	

	protected Vote() {
		
	}


	public Vote(User user) {
		this.user = user;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Timestamp getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}
	

}
