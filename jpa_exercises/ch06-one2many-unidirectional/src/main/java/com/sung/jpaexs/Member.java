package com.sung.jpaexs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Member {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MEMBER_ID")
	private Long Id;
	
	private String name;
	
	@ManyToOne/*(fetch = FetchType.LAZY)*/ // default는 FetchType.EAGER
	private Team team;

	public Member() {}
	
	public Member (String name) {this.name = name;}
	
//	@ElementCollection
//	List<String> strs = new ArrayList<>();
	
	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}
	
	@Override
	public String toString() {
		return "Member [Id=" + Id +
					", name=" + name +
					", team=" + team + 
//					", strs=" + strs + 
					"]";
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
