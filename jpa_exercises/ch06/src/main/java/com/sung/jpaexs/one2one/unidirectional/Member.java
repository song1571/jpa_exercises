package com.sung.jpaexs.one2one.unidirectional;

import javax.persistence.*;

@Entity
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MEMBER_ID")
	private Long id;
	
	private String username;
	
	@OneToOne
	@JoinColumn(name = "LOCKER_ID", unique = true)
	private Locker locker;
	
	public Member() {
	}

	public Member(String username) {
		this.username = username;
	}

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

	public Locker getLocker() {
		return locker;
	}

	public void setLocker(Locker locker) {
		this.locker = locker;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
