package com.sung.jpaexs.one2one.bidirectional;

import javax.persistence.*;

@Entity
public class Traveler {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TRAVELER_ID")
	private Long id;
	
	private String username;
	
	@OneToOne
	@JoinColumn(name = "PASSPORT_ID", unique = true)
	private PassPort passPort;
	
	public Traveler() {
	}

	public Traveler(String username) {
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

	public PassPort getPassPort() {
		return passPort;
	}

	public void setPassPort(PassPort passPort) {
		this.passPort = passPort;
		passPort.setTraveler(this);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
