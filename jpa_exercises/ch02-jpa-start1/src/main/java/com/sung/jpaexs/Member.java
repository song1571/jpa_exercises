package com.sung.jpaexs;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
//@Table(name = "MEMBER")
//@Table(name = "Member",
//	uniqueConstraints = @UniqueConstraint(
//			name = "NAME_AGE_UNIQUE",
//			columnNames = {"email", "phoneNumber"}))
//@TableGenerator(
//		name = "MEMBER_SEQ_GENERATOR",
//		table = "MY_SEQUENCES",
//		pkColumnValue = "MEMBER_SEQ", allocationSize = 50)
@SequenceGenerator(
	    name = "MEMBER_SEQ_GENERATOR",
	    sequenceName = "MEMBER_SEQ",
	    initialValue = 1, allocationSize = 50)
public class Member {
	// Default Constructor가 있어야 함!!!
	
	@Id
//	@GeneratedValue(strategy = GenerationType.TABLE,
//		generator="MEMBER_SEQ_GENERATOR")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, 
		generator = "MEMBER_SEQ_GENERATOR")
	private Long id; // Integer(X), String(X) : 자동증가...
	
//	@Column(name="name")
	private String username;
	
	private Integer age;
	
//	@Column(unique=true)
//	private String email;
	
//	@Column(unique=true)
//	private String phoneNumber;
	
	@Enumerated(EnumType.STRING)
	private RoleType roleType;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;              
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date lastModifiedDate;        
	
//	private String addr;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	public RoleType getRoleType() {
		return roleType;
	}
	
	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}
	
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	@PrePersist
	public void prePersist() {
		createdDate = new Date();
	}
	
	@PreUpdate
	public void preUpdate() {
		lastModifiedDate = new Date();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
