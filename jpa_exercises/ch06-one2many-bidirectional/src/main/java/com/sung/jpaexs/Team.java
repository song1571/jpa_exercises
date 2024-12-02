package com.sung.jpaexs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Team {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "TEAM_ID")
	private Long id;
	
	private String name;
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name = "TEAM_ID")
	List<Member> members = new ArrayList<>();
	
	public Team() {}
	public Team(String name) {this.name = name;}
	
	// 편의 메서드
	public void addMember(Member member) {
		member.setTeam(this);
		members.add(member);
	}
	
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
	
	public List<Member> getMembers() {
		return members;
	}

	public void setMembers(List<Member> members) {
		this.members = members;
	}

	@Override
	public String toString() {
		return "Team [id=" + id +
				", name=" + name +
				", members numbers=" + members.size() +
				"]";
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
