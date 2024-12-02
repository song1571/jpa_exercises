package com.sung.jpaexs.one2one.bidirectional;

import javax.persistence.*;

@Entity
public class PassPort {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PASSPORT_ID")
	private Long id;
	
	private String name;
	
	@OneToOne(mappedBy = "passPort")
	private Traveler traveler;
	
	public PassPort() {}
	
	public PassPort(String name) {this.name = name;}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Traveler getTraveler() {
		return traveler;
	}

	public void setTraveler(Traveler traveler) {
		this.traveler = traveler;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
