package com.sung.entity;

import javax.persistence.*;

import javax.persistence.DiscriminatorValue;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@DynamicInsert
@DynamicUpdate
@Entity
@DiscriminatorValue("ALBUM")
public class Album extends Item {
	
	private String artist;
	
	public String getArtist() {
		return artist;
	}

	public void setArtist(String artist) {
		this.artist = artist;
	}

	public static void main(String[] args) {

	}

}
