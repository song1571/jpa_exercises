package com.sung.entity;

import java.util.Objects;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Address {
	
	private String street;
	private String city;
	private String zipCode;
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if ((o == null) || (getClass() !=o.getClass())) return false;
		
		Address address = (Address) o;
		return Objects.equals(street, address.street) &&
				Objects.equals(city, address.city) &&
				Objects.equals(zipCode, address.zipCode);
	}
	
	@Override
	public int hashCode ()  { return Objects.hash(street, city, zipCode); }
}
